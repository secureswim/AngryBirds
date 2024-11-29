package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class BlueBird extends Bird{
    public BlueBird(Texture texture, float x, float y, World world) {
        super(texture, x, y, world);
        setDamage(5);
    }
}
