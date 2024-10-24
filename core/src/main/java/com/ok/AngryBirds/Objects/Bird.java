package com.ok.AngryBirds.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    private Vector2 speed;
    private int impact;
    private Vector2 position;
    private Catapult catapult;
    Texture image;

    public Bird() {
        speed = new Vector2();
        int impact;
        position=new Vector2();
        catapult= new Catapult("slingshot.png");
    }

    public void launch(float angle, float power){

    }

    public void hit(Obstacle obstacle){

    }

    public void hit(Pig pig){

    }
}
