package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class IceObstacle extends Obstacle {
    public IceObstacle(Texture texture, float x, float y, float width, float height, World world) {
        super(texture, x, y, width, height, world);
        setHealth(2);
    }

    @Override
    protected float getDensity() {
        return 0.5f; // Lighter than wood
    }

    @Override
    protected float getFriction() {
        return 0.2f; // Slippery
    }

    @Override
    protected float getRestitution() {
        return 0.3f; // More bouncy
    }
}
