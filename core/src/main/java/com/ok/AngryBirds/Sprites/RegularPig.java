package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class RegularPig extends Pig {
    public RegularPig(Texture texture, float x, float y, float radius, World world) {
        super(texture, x, y, radius, world);
    }

    @Override
    protected int getInitialHealth() {
        return 45;
    }

    @Override
    protected float getDensity() {
        return 0.6f; // Light
    }

    @Override
    protected float getFriction() {
        return 100.4f;
    }

    @Override
    protected float getRestitution() {
        return 0.3f; // Moderate bounciness
    }
}
