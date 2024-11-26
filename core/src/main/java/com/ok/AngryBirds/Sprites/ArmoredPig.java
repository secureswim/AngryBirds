package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class ArmoredPig extends Pig {
    public ArmoredPig(Texture texture, float x, float y, float radius, World world) {
        super(texture, x, y, radius, world);
    }

    @Override
    protected int getInitialHealth() {
        return 200; // Higher health due to armor
    }

    @Override
    protected float getDensity() {
        return 1.0f; // Heavier due to armor
    }

    @Override
    protected float getFriction() {
        return 0.5f;
    }

    @Override
    protected float getRestitution() {
        return 0.2f; // Less bounciness
    }
}
