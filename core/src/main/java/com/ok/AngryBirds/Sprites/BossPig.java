package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class BossPig extends Pig {
    public BossPig(Texture texture, float x, float y, float radius, World world) {
        super(texture, x, y, radius, world);
    }

    @Override
    protected int getInitialHealth() {
        return 500; // Very high health
    }

    @Override
    protected float getDensity() {
        return 1.5f; // Heaviest
    }

    @Override
    protected float getFriction() {
        return 0.6f;
    }

    @Override
    protected float getRestitution() {
        return 0.1f; // Minimal bounciness
    }
}
