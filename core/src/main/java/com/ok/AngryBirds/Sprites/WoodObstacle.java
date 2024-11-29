package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class WoodObstacle extends Obstacle {

    public WoodObstacle(Texture texture, float x, float y, float width, float height, World world) {
        super(texture, x, y, width, height, world);
        setHealth(15);
    }

    @Override
    protected float getDensity() {
        return 1.0f;
    }

    @Override
    protected float getFriction() {
        return 0.5f;
    }

    @Override
    protected float getRestitution() {
        return 0.1f;
    }
}
