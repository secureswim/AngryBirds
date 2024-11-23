package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;

public class Bird {
    private Texture texture;
    private float posX, posY;
    private float speed;
    private float angle;
    private float velocityX, velocityY;
    private boolean is_launched;
    private boolean is_in_air;

    private static final float gravity = -9.8f;

    public Bird(Texture texture) {
        this.texture = texture;
        this.posX = 125;
        this.posY = 331;
        this.is_launched = false;
        this.is_in_air = false;
    }

    public void launch(float speed,float angle) {
        this.speed = speed;
        this.angle = angle;

        float radianAngle = (float) Math.toRadians(angle);

        this.velocityX = (float) (speed * Math.cos(radianAngle));
        this.velocityY = (float) (speed * Math.sin(radianAngle));

        this.is_launched = true;
        this.is_in_air = true;
    }

    public void update(float dt) {
        if (is_in_air) {
            velocityY += gravity * dt;
            posX += (float) (velocityX * dt * 10.0f);
            posY += (float) (velocityY * dt * 10.0f);

            if (posY < 200) {
                posY = 200;
                is_in_air = false;
                velocityX = 0;
                velocityY = 0;
            }
        }
    }

    public void reset() {
        posX = 125;
        posY = 331;
        velocityX = 0;
        velocityY = 0;
        is_launched = false;
        is_in_air = false;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public boolean isIs_launched() {
        return is_launched;
    }

    public boolean isIs_in_air() {
        return is_in_air;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }
}
