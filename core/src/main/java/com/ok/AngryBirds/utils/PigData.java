package com.ok.AngryBirds.utils;

import java.io.Serializable;

public class PigData implements Serializable {
    public float posX;
    public float posY;
    public float velocityX;
    public float velocityY;
    public float angle;
    public int health;
    public String texturePath;
    public String type;
    public float radius;
    public boolean hasCollided;

    public PigData(String texturePath, float posX, float posY, float velocityX, float velocityY,
                   float angle, int health, String type, float radius, boolean hasCollided) {
        this.posX = posX;
        this.posY = posY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.angle = angle;
        this.health = health;
        this.texturePath = texturePath;
        this.type = type;
        this.radius = radius;
        this.hasCollided = hasCollided;
    }

    public boolean isHasCollided() {
        return hasCollided;
    }

    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }
}
