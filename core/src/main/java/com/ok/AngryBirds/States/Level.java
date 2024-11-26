package com.ok.AngryBirds.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ok.AngryBirds.Sprites.Bird;
import com.ok.AngryBirds.Sprites.Obstacle;
import com.ok.AngryBirds.Sprites.Pig;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public abstract class Level extends State {
    protected ArrayList<Obstacle> obstacles;
    protected ArrayList<Bird> birds;
    protected ArrayList<Pig> pigs;
    protected Bird currentBird;
    protected World world;
    protected ShapeRenderer shapeRenderer;

    public Level(GameStateManager gsm) {
        super(gsm);
        obstacles = new ArrayList<>();
        birds = new ArrayList<>();
        pigs = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, -9.81f), true);
    }

    protected abstract void createAllBodies();

    @Override
    public abstract void render(SpriteBatch sb);

    @Override
    public void dispose() {
        world.dispose();
        shapeRenderer.dispose();
        for (Bird bird : birds) {
            bird.getTexture().dispose();
        }
        for (Obstacle obstacle : obstacles) {
            obstacle.getTexture().dispose();
        }
        for (Pig pig : pigs) {
            pig.getTexture().dispose();
        }
    }
}
