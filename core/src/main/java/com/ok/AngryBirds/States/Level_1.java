package com.ok.AngryBirds.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ok.AngryBirds.Sprites.*;

import java.util.ArrayList;

public class Level_1 extends State {
    private final Texture levelBackground;
    private final Texture slingshot;

    private ArrayList<Bird> birds;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Pig> pigs;
    private Bird currentBird;

    private World world;
    private ShapeRenderer shapeRenderer;

    public Level_1(GameStateManager gsm) {
        super(gsm);

        levelBackground = new Texture("level1_background.jpg");
        slingshot = new Texture("slingshot_ab.png");

        birds = new ArrayList<>();
        obstacles = new ArrayList<>();
        pigs = new ArrayList<>();

        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, -9.81f), true);

        // Initialize birds
        birds.add(new RedBird(new Texture("red_ab.png"), 125, 331));
        birds.add(new YellowBird(new Texture("yellow_ab.png"), 125, 331));
        currentBird = birds.get(0);

        // Initialize obstacles and pigs
        createAllBodies();
    }

    private void createAllBodies() {
        // Adding obstacles

        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"),860,191,16,150,world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"),992,191,16,150,world));
        obstacles.add(new WoodObstacle(new Texture("horizontal_wood.png"),855, 333, 155, 16,world));
        obstacles.add(new IceObstacle(new Texture("v_ice_short.png"),900, 193, 16, 100,world));
        obstacles.add(new IceObstacle(new Texture("v_ice_short.png"),952, 193, 16, 100,world));
        obstacles.add(new WoodObstacle(new Texture("horizontal_wood.png"),898, 288, 69, 16,world));
        obstacles.add(new IceObstacle(new Texture("ice_block.png"),902, 348, 60, 60,world));
        obstacles.add(new IceObstacle(new Texture("ice_tri_left.png"),860, 348, 42, 42,world));
        obstacles.add(new IceObstacle(new Texture("ice_tri_right.png"),962, 348, 42, 42,world));


//        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 860, 191, 16, 150, world));
//        obstacles.add(new IceObstacle(new Texture("v_ice_short.png"), 900, 193, 16, 100, world));

        // Adding pigs
        pigs.add(new RegularPig(new Texture("pig1.png"), 903, 403, 25, world));
    }

    @Override
    protected void hande_input() {
        // Input handling for slingshot and bird launching
    }

    @Override
    public void update(float dt) {
        hande_input();
        if (currentBird.isIsLaunched()) {
            currentBird.update(dt);
            if (currentBird.getPosY() < 200) {
                currentBird.reset();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(levelBackground, 0, 0);
        sb.draw(slingshot, 50, 190, 190, 190);

        for (Bird bird : birds) {
            sb.draw(bird.getTexture(), bird.getPosX(), bird.getPosY(), 50, 50);
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
        sb.end();

        // Render trajectory (if needed)
    }

    @Override
    public void dispose() {
        levelBackground.dispose();
        slingshot.dispose();
        shapeRenderer.dispose();
        world.dispose();

        for (Bird bird : birds) bird.getTexture().dispose();
        for (Obstacle obstacle : obstacles) obstacle.getTexture().dispose();
        for (Pig pig : pigs) pig.getTexture().dispose();
    }
}
