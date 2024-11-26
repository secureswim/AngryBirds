package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;

public class YellowBird extends Bird {
    public YellowBird(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void launch(float speed, float angle) {
        setSpeed(speed * 1.5f); // Increased speed boost for yellow bird
        setAngle(angle);
        setIs_launched(true); // Use the setter or protected variable
    }
}
