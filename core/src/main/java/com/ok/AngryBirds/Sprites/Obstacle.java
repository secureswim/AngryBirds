package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Obstacle {
    private Texture texture;
    private Body body;
    private float posX;
    private float posY;
    private float width;
    private float height;
    private int health;

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Obstacle(Texture texture, float x, float y, float width, float height, World world) {
        this.texture = texture;
        this.posX=x;
        this.posY=y;
        this.width=width;
        this.height=height;

        float PIXELS_TO_METERS = 100f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((x + width / 2) / PIXELS_TO_METERS, (y + height / 2) / PIXELS_TO_METERS);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2) / PIXELS_TO_METERS, (height / 2) / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = getDensity();
        fixtureDef.friction = getFriction();
        fixtureDef.restitution = getRestitution();

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void reduceHealth(int damage) {
        health -= damage;
    }

    protected abstract float getDensity();
    protected abstract float getFriction();
    protected abstract float getRestitution();

    public Texture getTexture() {
        return texture;
    }

    public Body getBody() {
        return body;
    }

    public boolean isDestroyed() {
        if(health<=0){
            return true;
        }
        else{
            return false;
        }
    }
}
