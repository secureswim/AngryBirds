package com.ok.AngryBirds.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Catapult {
    private Bird current_bird;
    private Vector2 position;
    private Texture image;

    public Catapult(String path){
        this.image=new Texture(path);
    }
    public void setAngle(float angle){

    }

    public void setForce(float force){

    }

    public void hit(int damage){

    }
}
