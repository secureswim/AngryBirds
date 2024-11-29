package com.ok.AngryBirds.utils;

public class ObstacleData {
    public float posX;
    public float posY;
    public float velocityX;
    public float velocityY;
    public float angle;
    public int health;
    public float width;
    public float height;
    public String type;
    public String texturePath;

    public ObstacleData(String texturePath, float posX, float posY, float velocityX, float velocityY,
                        float angle, int health, String type, float width, float height) {
        this.posX = posX;
        this.posY = posY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.angle = angle;
        this.health = health;
        this.type = type;
        this.width = width;
        this.height = height;
        this.texturePath = texturePath;
    }
}
