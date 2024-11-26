package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;

public abstract class Bird {
    private Texture texture;
    private float posX;
    private float posY;
    private float speed;
    private float angle;
    protected boolean isLaunched; // Changed access modifier to protected

    public Bird(Texture texture, float x, float y) {
        this.texture = texture;
        this.posX = x;
        this.posY = y;
        this.speed = 0;
        this.angle = 0;
        this.isLaunched = false;
    }

    public abstract void launch(float speed, float angle);

    public void update(float dt) {
        if (isLaunched) {
            float velocityX = (float) (speed * Math.cos(Math.toRadians(angle)));
            float velocityY = (float) (speed * Math.sin(Math.toRadians(angle)) - 9.81f * dt);
            posX += velocityX * dt;
            posY += velocityY * dt;
        }
    }

    public void reset() {
        isLaunched = false;
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
        return isLaunched;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    protected void setLaunched(boolean launched) {
        this.isLaunched = launched;
    }
}
