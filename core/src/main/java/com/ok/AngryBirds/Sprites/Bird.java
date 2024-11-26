package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ok.AngryBirds.utils.Trajectory;

public abstract class Bird {
    private Texture texture;
    private float posX;
    private float posY;
    private Body body;
    private boolean is_launched;
    private World world;

    public Bird(Texture texture, float x, float y, World world) {
        this.texture = texture;
        this.posX = x;
        this.posY = y;
        this.world = world;
        this.is_launched = false;

        // Create static body initially
        createStaticBody(x, y);
    }

    private void createStaticBody(float x, float y) {
        // Remove existing body if it exists
        if (body != null) {
            world.destroyBody(body);
        }

        // Create static body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
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

    private void createDynamicBody(float x, float y) {
        // Remove existing static body
        world.destroyBody(body);

        // Create dynamic body
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

    public void launch(float speed, float angle) {
        // First, convert to dynamic body
        createDynamicBody(posX, posY);

        Vector2 velocity = Trajectory.calculateLaunchVelocity(speed, angle);

        // Apply velocity to the bird
        body.setLinearVelocity(velocity.x*10, velocity.y*10);
        is_launched = true;
    }

    public void reset(World world, float x, float y) {
        // Recreate static body at initial position
        createStaticBody(x, y);
        posX = x;
        posY = y;
        is_launched = false;
    }

    // Existing getters remain the same
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

    public Vector2 getPosition() {
        Vector2 position = body.getPosition();
        return new Vector2(position.x * 10, position.y * 10);
    }

    public Body getBody() {
        return body;
    }
}
