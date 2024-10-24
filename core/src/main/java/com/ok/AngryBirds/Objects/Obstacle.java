package com.ok.AngryBirds.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    private int strength;
    private String material;
    private Vector2 position;
    Texture image;

    public Obstacle(){
    }

    public void hit(int damage){

    }

    public boolean isDestroyed(){
        return true;
    }

}
