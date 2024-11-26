package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ok.AngryBirds.Sprites.*;
import com.ok.AngryBirds.utils.Trajectory;

import java.util.ArrayList;
import java.util.List;

public class Level_1 extends State {
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
    private CollisionHandler collisionHandler;

    private static final float PIXELS_TO_METERS = 100f;
    private final float slingshot_centreX = 155;
    private final float slingshot_centreY = 345;

    private List<float[]> trajectory;

    private float startX, startY;
    private float endX, endY;
    private boolean is_dragging;

    public Level_1(GameStateManager gsm) {
        super(gsm);
        pause = new Texture("pause_button.png");
        win = new Texture("win_button.png");
        lose = new Texture("lose_button.png");

        levelBackground = new Texture("level1_background.jpg");
        slingshot = new Texture("slingshot_ab.png");

        birds = new ArrayList<>();
        obstacles = new ArrayList<>();
        pigs = new ArrayList<>();
        collisionHandler = new CollisionHandler();

        shape_renderer = new ShapeRenderer();

        // Slightly reduced gravity to slow down fall
        world = new World(new Vector2(0, -4.905f), true);

        birds.add(new RedBird(new Texture("red_ab.png"), 125, 331, world));
        birds.add(new YellowBird(new Texture("yellow_ab.png"), 55, 193, world));
        current_bird = birds.get(0);
        createAllBodies();
    }

    private void createAllBodies() {
        Ground ground = new Ground(world, 0, 0, 1200/100f, 190/100f);

        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 860, 191, 16, 150, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 992, 191, 16, 150, world));
        obstacles.add(new WoodObstacle(new Texture("horizontal_wood.png"), 855, 333, 155, 16, world));
        obstacles.add(new IceObstacle(new Texture("v_ice_short.png"), 900, 193, 16, 100, world));
        obstacles.add(new IceObstacle(new Texture("v_ice_short.png"), 952, 193, 16, 100, world));
        obstacles.add(new WoodObstacle(new Texture("horizontal_wood.png"), 898, 288, 69, 16, world));
        obstacles.add(new IceObstacle(new Texture("ice_block.png"), 902, 348, 60, 60, world));
        obstacles.add(new IceObstacle(new Texture("ice_tri_left.png"), 860, 348, 42, 42, world));
        obstacles.add(new IceObstacle(new Texture("ice_tri_right.png"), 962, 348, 42, 42, world));

        pigs.add(new RegularPig(new Texture("pig1.png"), 903, 403, 25, world));
    }

    @Override
    protected void handle_input() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 30 && x <= 115 && y >= 650 && y <= 735) {
                gsm.push(new PauseState(gsm, this));
                return;
            }
            if (x >= 1110 && x <= 1180 && y >= 665 && y <= 735) {
                gsm.push(new WinState(gsm, this));
            } else if (x >= 1110 && x <= 1180 && y >= 595 && y <= 665) {
                gsm.push(new LoseState(gsm, this));
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
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(levelBackground, 0, 0);
        sb.draw(slingshot, 50, 190, 190, 190);
        sb.draw(pause, 30, 650, 85, 85);
        sb.draw(win, 1110, 665, 70, 70);
        sb.draw(lose, 1110, 595, 70, 70);

        for (Bird bird : birds) {
            sb.draw(bird.getTexture(),
                bird.getPosX(),
                bird.getPosY(),
                50, 50
            );
        }

        for (Obstacle obstacle : obstacles) {
            Texture txt = obstacle.getTexture();
            sb.draw(
                txt,
                obstacle.getBody().getPosition().x * PIXELS_TO_METERS - obstacle.getWidth() / 2,
                obstacle.getBody().getPosition().y * PIXELS_TO_METERS - obstacle.getHeight() / 2,
                obstacle.getWidth(),
                obstacle.getHeight()
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

        for (Bird bird : birds) bird.getTexture().dispose();
        for (Obstacle obstacle : obstacles) obstacle.getTexture().dispose();
        for (Pig pig : pigs) pig.getTexture().dispose();
    }
}
