package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public abstract class Bird {
    private Texture texture;
    private Body body;
    private World world;

    private float speed;
    private float angle;
    private boolean is_launched;
    private boolean is_in_air;

    private static final float PIXELS_TO_METERS = 100f;
    private static final float GRAVITY = 9.8f;
    private Vector2 initialPosition;

    public Bird(Texture texture, float x, float y, World world) {
        this.texture = texture;
        this.world = world;
        this.initialPosition = new Vector2(x / PIXELS_TO_METERS, y / PIXELS_TO_METERS);

        // Create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(initialPosition);
        bodyDef.fixedRotation = true; // Prevent rotation

        // Create body in the world
        body = world.createBody(bodyDef);

        // Create a circular shape for the bird
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(25f / PIXELS_TO_METERS); // Assuming 50x50 pixel bird

        // Create fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.2f; // Bounciness

        // Create fixture
        body.createFixture(fixtureDef);

        // Clean up shape
        circleShape.dispose();

        // Initially set the body to not move
        body.setLinearVelocity(0, 0);
        body.setAwake(false);

        this.is_launched = false;
        this.is_in_air = false;
    }

    public void launch(float speed, float angle) {
        // Store initial launch parameters for potential trajectory recreation
        this.speed = speed;
        this.angle = angle;

        // Convert angle to radians
        float radianAngle = (float) Math.toRadians(angle);

        // Calculate initial velocity components
        float velocityX = speed * (float) Math.cos(radianAngle);
        float velocityY = speed * (float) Math.sin(radianAngle);

        // Enable body physics
        body.setAwake(true);

        // Apply impulse to the body with a more physics-accurate approach
        body.applyLinearImpulse(
            new Vector2(velocityX, velocityY),
            body.getWorldCenter(),
            true
        );

        this.is_launched = true;
        this.is_in_air = true;
    }

    public void update(float dt) {
        // Additional trajectory management
        if (is_launched && is_in_air) {
            Vector2 currentVelocity = body.getLinearVelocity();

            // Optional: Apply slight drag or air resistance
            currentVelocity.x *= 0.99f;

            body.setLinearVelocity(currentVelocity);
        }

        // If the bird falls below ground, reset
        if (body.getPosition().y * PIXELS_TO_METERS < 200) {
            reset();
        }
    }

    public void reset() {
        // Reset to initial position
        body.setTransform(initialPosition, 0);
        body.setLinearVelocity(0, 0);
        body.setAwake(false);

        is_launched = false;
        is_in_air = false;
    }

    // Existing getters and setters remain the same
    // ... (getPosX, getPosY, getTexture, etc.)

    // Getters and Setters (previous implementation)
    public Texture getTexture() {
        return texture;
    }

    public float getPosX() {
        return body.getPosition().x * PIXELS_TO_METERS;
    }

    public void setPosX(float posX) {
        body.setTransform(posX / PIXELS_TO_METERS, body.getPosition().y, body.getAngle());
    }

    public float getPosY() {
        return body.getPosition().y * PIXELS_TO_METERS;
    }

    public void setPosY(float posY) {
        body.setTransform(body.getPosition().x, posY / PIXELS_TO_METERS, body.getAngle());
    }

    public Body getBody() {
        return body;
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

