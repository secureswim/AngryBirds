package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ok.AngryBirds.Sprites.*;
import com.ok.AngryBirds.utils.Trajectory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level_3 extends State {
    private final Texture levelBackground;
    private final Texture slingshot;
    private final Texture pause;
    private final Texture win;
    private final Texture lose;

    private ArrayList<Bird> birds;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Pig> pigs;
    private Bird current_bird;

    private World world;
    private ShapeRenderer shape_renderer;

    private static final float PIXELS_TO_METERS = 100f;
    private final float slingshot_centreX = 155;
    private final float slingshot_centreY = 345;

    private List<float[]> trajectory;

    private float startX, startY;
    private float endX, endY;
    private boolean is_dragging;

    private CollisionHandler collisionHandler;

    public Level_3(GameStateManager gsm) {
        super(gsm);
        pause = new Texture("pause_button.png");
        win = new Texture("win_button.png");
        lose = new Texture("lose_button.png");

        levelBackground = new Texture("level1_background.jpg");
        slingshot = new Texture("slingshot_ab.png");

        birds = new ArrayList<>();
        obstacles = new ArrayList<>();
        pigs = new ArrayList<>();


        shape_renderer = new ShapeRenderer();

        world = new World(new Vector2(0, -4.905f), true);

        collisionHandler = new CollisionHandler();
        world.setContactListener(collisionHandler);

        birds.add(new RedBird(new Texture("red_ab.png"), 125, 331, world));
        birds.add(new YellowBird(new Texture("yellow_ab.png"), 55, 193, world));
        current_bird = birds.get(0);
        createAllBodies();
        setupUserData();
    }

    private void createAllBodies() {
        Ground ground = new Ground(world, 0, 0, 1200/100f, 190/100f);

        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 730, 191, 16, 100, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 820, 191, 16, 100, world));
        obstacles.add(new IceObstacle(new Texture("h_ice_short.png"), 730, 291, 100, 14, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 830, 191, 16, 200, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 950, 191, 16, 200, world));
        obstacles.add(new SteelObstacle(new Texture("horizontal_steel.png"), 830, 391, 138, 16, world));
        obstacles.add(new SteelObstacle(new Texture("vertical_steel.png"), 850, 407, 14, 80, world));
        obstacles.add(new SteelObstacle(new Texture("vertical_steel.png"), 930, 407, 14, 80, world));
        obstacles.add(new SteelObstacle(new Texture("horizontal_steel.png"), 850, 487, 90, 14, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 970, 191, 16, 150, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 1060, 191, 16, 150, world));
        obstacles.add(new IceObstacle(new Texture("h_ice_short.png"), 975, 341, 100, 14, world));


        pigs.add(new ArmoredPig(new Texture("armoured_pig.png"), 870, 403, 27, world));
        pigs.add(new RegularPig(new Texture("regular_pig.png"), 750, 292, 25, world));
        pigs.add(new RegularPig(new Texture("regular_pig.png"), 1000, 198, 25, world));
        pigs.add(new BossPig(new Texture("boss_pig.png"), 860, 198, 35, world));


    }

    private void setupUserData() {
        // Add user data to birds
        for (Bird bird : birds) {
            bird.getBody().setUserData(bird);
        }

        // Add user data to obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.getBody().setUserData(obstacle);
        }

        // Add user data to pigs
        for (Pig pig : pigs) {
            pig.getBody().setUserData(pig);
        }
    }

    @Override
    protected void handle_input() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 30 && x <= 115 && y >= 650 && y <= 735) {
                gsm.push(new PauseState_3(gsm, this));
                return;
            }

            if (!is_dragging) {
                startX = x;
                startY = y;
                is_dragging = true;
            }

        } else if (Gdx.input.isTouched() && is_dragging) {
            endX = Gdx.input.getX();
            endY = Gdx.graphics.getHeight() - Gdx.input.getY();

            float dx = slingshot_centreX - endX;
            float dy = slingshot_centreY - endY;
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));

            // Adjust speed calculation to prevent extreme velocities
            //float speed = Math.min((float) Math.sqrt(dx * dx + dy * dy) / 100, 50);

            float new_speed = Math.min((float) Math.sqrt(dx * dx + dy * dy) / 4, 50);

            trajectory = Trajectory.calculate_trajectory(new_speed, angle, 0.1f, 10.0f);


            // Update bird's body position while dragging
            current_bird.getBody().setTransform(
                endX / PIXELS_TO_METERS,
                endY / PIXELS_TO_METERS,
                0
            );
            current_bird.getBody().setAwake(false);

        } else if (!Gdx.input.isTouched() && is_dragging) {
            float dx = slingshot_centreX - endX;
            float dy = slingshot_centreY - endY;

            // Adjust speed calculation to prevent extreme velocities
            float speed = Math.min((float) Math.sqrt(dx * dx + dy * dy) / 100, 50);
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));

            current_bird.getBody().setAwake(true);
            current_bird.launch(speed, angle);

            is_dragging = false;
            trajectory = null;
        }
    }
    @Override
    public void update(float dt) {
        // Step the physics world
        world.step(dt, 6, 2);

        handle_input();

        // Update each bird
        for (Bird bird : birds) {
            bird.update(dt);
        }

        // Safely remove obstacles
        Iterator<Obstacle> obstacleIterator = obstacles.iterator();
        while (obstacleIterator.hasNext()) {
            Obstacle obstacle = obstacleIterator.next();
            if (obstacle.getBody().getType() == BodyDef.BodyType.DynamicBody) {
                Vector2 velocity = obstacle.getBody().getLinearVelocity();
                if (velocity.len() < 0.05f) {
                    obstacle.getBody().setLinearVelocity(0, 0);
                    obstacle.getBody().setAngularVelocity(0);
                }
            }
            if (collisionHandler.getBodiesToDestroy().contains(obstacle.getBody())) {
                obstacleIterator.remove();
            }
        }

        // Safely remove pigs
        Iterator<Pig> pigIterator = pigs.iterator();
        while (pigIterator.hasNext()) {
            Pig pig = pigIterator.next();
            if (pig.getBody().getType() == BodyDef.BodyType.DynamicBody) {
                Vector2 velocity = pig.getBody().getLinearVelocity();
                if (velocity.len() < 0.05f) {
                    pig.getBody().setLinearVelocity(0, 0);
                    pig.getBody().setAngularVelocity(0);
                }
            }
            if (collisionHandler.getBodiesToDestroy().contains(pig.getBody())) {
                pigIterator.remove();
            }
        }

        // Destroy bodies after iterations are complete
        List<Body> bodiesToDestroy = collisionHandler.getBodiesToDestroy();
        for (Body body : bodiesToDestroy) {
            if (body != null) {
                world.destroyBody(body);
            }
        }
        bodiesToDestroy.clear();

        if (birds.isEmpty() && !pigs.isEmpty()) {
            gsm.push(new LoseState_3(gsm, this));
        } else if (pigs.isEmpty()) {
            gsm.push(new WinState_3(gsm, this));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(levelBackground, 0, 0);
        sb.draw(slingshot, 50, 190, 190, 190);
        sb.draw(pause, 30, 650, 85, 85);

        for (Bird bird : birds) {
            sb.draw(bird.getTexture(),
                bird.getPosX()-25,
                bird.getPosY()-25,
                50, 50
            );
        }

        for (Obstacle obstacle : obstacles) {
            Texture txt = obstacle.getTexture();
            float bodyX = obstacle.getBody().getPosition().x * PIXELS_TO_METERS;
            float bodyY = obstacle.getBody().getPosition().y * PIXELS_TO_METERS;

            // Use exact height and width offsets
            sb.draw(
                txt,
                bodyX - obstacle.getWidth() / 2,
                bodyY - obstacle.getHeight() / 2,
                obstacle.getWidth() / 2,
                obstacle.getHeight() / 2,
                obstacle.getWidth(),
                obstacle.getHeight(),
                1f,
                1f,
                obstacle.getBody().getAngle() * MathUtils.radiansToDegrees,
                0, 0,
                txt.getWidth(),
                txt.getHeight(),
                false,
                false
            );
        }


        for (Pig pig : pigs) {
            Texture txt = pig.getTexture();
            float diameter = pig.getRadius() * 2;
            sb.draw(
                txt,
                pig.getBody().getPosition().x * PIXELS_TO_METERS - pig.getRadius(),
                pig.getBody().getPosition().y * PIXELS_TO_METERS - pig.getRadius(),
                diameter,
                diameter
            );
        }

        if (trajectory != null && is_dragging) {
            shape_renderer.begin(ShapeRenderer.ShapeType.Line);
            shape_renderer.setColor(0, 0, 0, 1);
            for (float[] point : trajectory) {
                // Debug print to verify points

                shape_renderer.circle(
                    slingshot_centreX + point[0],
                    slingshot_centreY - point[1],  // Invert Y to match screen coordinates
                    5
                );
            }
            shape_renderer.end();

            shape_renderer.begin(ShapeRenderer.ShapeType.Filled);
            shape_renderer.setColor(1, 1, 1, 1);

            for (float[] point : trajectory) {
                shape_renderer.circle(
                    slingshot_centreX + point[0],
                    slingshot_centreY - point[1],  // Same Y-axis inversion
                    4
                );
            }
            shape_renderer.end();
        }

        sb.end();
    }

    @Override
    public void dispose() {
        levelBackground.dispose();
        slingshot.dispose();
        shape_renderer.dispose();
        world.dispose();
        collisionHandler = null;

        for (Bird bird : birds) bird.getTexture().dispose();
        for (Obstacle obstacle : obstacles) obstacle.getTexture().dispose();
        for (Pig pig : pigs) pig.getTexture().dispose();
    }
}
