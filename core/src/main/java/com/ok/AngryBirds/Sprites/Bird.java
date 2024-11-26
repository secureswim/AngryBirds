package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;

public abstract class Bird {
    private Texture texture;
    private float posX;
    private float posY;
    private float speed;
    private float angle;
    private float velocityX, velocityY;
    protected boolean is_launched;
    private boolean is_in_air;
    private static final float gravity = -9.8f;



    public Bird(Texture texture, float x, float y) {
        this.texture = texture;
        this.posX = x;
        this.posY = y;
        this.speed = 0;
        this.angle = 0;

        this.is_launched = false;
    }

    public void launch(float speed, float angle){
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
        velocityX = 0;
        velocityY = 0;
        is_launched = false;
        is_in_air = false;
        speed = 0;
        angle = 0;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public boolean isIsLaunched() {
        return is_launched;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    protected void setIs_launched(boolean is_launched) {
        this.is_launched = is_launched;
    }
}
