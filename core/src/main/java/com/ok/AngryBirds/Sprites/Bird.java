package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bird {
    private Texture texture;
    private float posX;
    private float posY;
    private Body body;
    private boolean is_launched;

    public Bird(Texture texture, float x, float y, World world) {
        this.texture = texture;
        this.posX = x;
        this.posY = y;
        this.is_launched = false;

        // Create Box2D body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 100f, y / 100f);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.25f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void update() {

    }

    public void launch(float speed, float angle) {
        float radianAngle = (float) Math.toRadians(angle);

        // Apply impulse to launch
        body.applyLinearImpulse(
            new Vector2((float) (speed * Math.cos(radianAngle)),
                (float) (speed * Math.sin(radianAngle))),
            body.getWorldCenter(),
            true
        );
        is_launched = true;
    }

    public Vector2 getPosition() {
        return body.getPosition().scl(100f); // Convert back to screen units
    }


    public void reset(World world, float x, float y) {
        body.setTransform(x / 100f, y / 100f, 0);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        is_launched = false;
    }

    public Texture getTexture() {
        return texture;
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
}
