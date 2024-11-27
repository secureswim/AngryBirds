package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class SteelObstacle extends Obstacle {

    public SteelObstacle(Texture texture, float x, float y, float width, float height, World world) {
        super(texture, x, y, width, height, world);
        setHealth(3);
    }

    @Override
    protected float getDensity() {
        return 5.0f; // Heavier than wood
    }

    @Override
    protected float getFriction() {
        return 0.8f; // High friction
    }

    @Override
    protected float getRestitution() {
        return 0.05f; // Almost no bounce
    }
}
