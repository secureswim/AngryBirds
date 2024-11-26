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

    private ArrayList<Bird> birds;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Pig> pigs;
    private Bird current_bird;

    private World world;
    private ShapeRenderer shape_renderer;

    private final float slingshot_centreX = 155;
    private final float slingshot_centreY = 345;

    private List<float[]> trajectory;

    private float startX, startY;
    private float endX, endY;
    private boolean is_dragging;

    public Level_1(GameStateManager gsm) {
        super(gsm);

        levelBackground = new Texture("level1_background.jpg");
        slingshot = new Texture("slingshot_ab.png");

        birds = new ArrayList<>();
        obstacles = new ArrayList<>();
        pigs = new ArrayList<>();

        shape_renderer = new ShapeRenderer();
        world = new World(new Vector2(0, -9.81f), true);

        birds.add(new RedBird(new Texture("red_ab.png"), 125, 331, world));
        birds.add(new YellowBird(new Texture("yellow_ab.png"), 55, 193, world));
        current_bird = birds.get(0);
        createAllBodies();
    }



    private void createAllBodies() {
        new Ground(world, 0, 0, 1200 / 100f, 200 / 100f);

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
            float speed = (float) Math.sqrt(dx * dx + dy * dy) / 4;

            trajectory = Trajectory.calculate_trajectory(angle, speed, 0.1f, 10.0f);
        } else if (!Gdx.input.isTouched() && is_dragging) {
            float dx = slingshot_centreX - endX;
            float dy = slingshot_centreY - endY;
            float speed = (float) Math.sqrt(dx * dx + dy * dy) / 4;
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));

            current_bird.launch(speed, angle);

            is_dragging = false;
            trajectory = null;
        }

    }
    @Override
    public void update(float dt) {
        world.step(1 / 60f, 6, 2); // Step Box2D world

        handle_input();

        if (current_bird.isIs_launched()) {
            Vector2 position = current_bird.getPosition();
            if (position.y < 200) {
                current_bird.reset(world, 125, 331);
            }
        }
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(levelBackground, 0, 0);
        sb.draw(slingshot, 50, 190, 190, 190);

        for (Bird bird : birds) {
            sb.draw(bird.getTexture(), bird.getPosX() - 25, bird.getPosY() - 25, 50, 50);
        }

        for (Obstacle obstacle : obstacles) {
            Texture txt = obstacle.getTexture();
            sb.draw(
                txt,
                obstacle.getBody().getPosition().x * 100 - obstacle.getWidth() / 2,
                obstacle.getBody().getPosition().y * 100 - obstacle.getHeight() / 2,
                obstacle.getWidth(),
                obstacle.getHeight()
            );
        }

        for (Pig pig : pigs) {
            Texture txt = pig.getTexture();
            float diameter = pig.getRadius() * 2;
            sb.draw(
                txt,
                pig.getBody().getPosition().x * 100 - pig.getRadius(),
                pig.getBody().getPosition().y * 100 - pig.getRadius(),
                diameter,
                diameter
            );
        }

        if (trajectory != null && is_dragging) {
            shape_renderer.begin(ShapeRenderer.ShapeType.Line);
            shape_renderer.setColor(0, 0, 0, 1);
            for (float[] point : trajectory) {
                shape_renderer.circle(slingshot_centreX + point[0], slingshot_centreY + point[1], 5);
            }
            shape_renderer.end();

            shape_renderer.begin(ShapeRenderer.ShapeType.Filled);
            shape_renderer.setColor(1, 1, 1, 1);

            for (float[] point : trajectory) {
                shape_renderer.circle(slingshot_centreX + point[0], slingshot_centreY + point[1], 5);
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
