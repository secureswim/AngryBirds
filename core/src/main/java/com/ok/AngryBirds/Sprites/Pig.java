package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Pig {
    private Texture texture;
    private Body body;
    private int health;

    public Pig(Texture texture, float x, float y, float radius, World world) {
        this.texture = texture;
        this.health = getInitialHealth();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Pigs can move
        bodyDef.position.set((x + radius) / 100f, (y + radius) / 100f);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / 100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = getDensity();
        fixtureDef.friction = getFriction();
        fixtureDef.restitution = getRestitution();

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    protected abstract int getInitialHealth();
    protected abstract float getDensity();
    protected abstract float getFriction();
    protected abstract float getRestitution();

    public Texture getTexture() {
        return texture;
    }

    public Body getBody() {
        return body;
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(int amount) {
        health = Math.max(0, health - amount);
    }

    public boolean isDestroyed() {
        return health <= 0;
    }
}

