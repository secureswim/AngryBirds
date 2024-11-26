package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;

public class RedBird extends Bird {
    public RedBird(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void launch(float speed, float angle) {
        setSpeed(speed);
        setAngle(angle);
        setLaunched(true); // Use the setter or protected variable
    }
}
