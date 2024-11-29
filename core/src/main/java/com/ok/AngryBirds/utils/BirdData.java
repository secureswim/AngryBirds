package com.ok.AngryBirds.utils;

public class BirdData {
    public float posX;
    public float posY;
    public float velocityX;
    public float velocityY;
    public float angle;
    public String texturePath; // Updated to use file path
    public String type;

    public BirdData(String texturePath, float posX, float posY, float velocityX, float velocityY, float angle, String type){
        this.posX = posX;
        this.posY = posY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.angle = angle;
        this.texturePath = texturePath;
        this.type = type;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
