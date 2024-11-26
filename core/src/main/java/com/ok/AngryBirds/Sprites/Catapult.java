package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.physics.box2d.*;

public class Catapult {
    private static final float PPM = 100f;
    private Body body;
    private World world;

    public Catapult(World world, float x, float y) {
        this.world = world;

        // Create catapult body at the center (125, 331) in world coordinates
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;  // Static because it doesn't move
        bodyDef.position.set(x / PPM, y / PPM); // Position in world coordinates

        body = world.createBody(bodyDef);

        // Define the catapult's shape (e.g., a simple rectangular platform)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 0.5f);  // Set the size of the catapult (1m width, 0.5m height)

        // Create the fixture for the catapult body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;  // Static body, no density
        fixtureDef.friction = 0.5f;

        // Attach the shape to the body
        body.createFixture(fixtureDef);
        shape.dispose(); // Dispose shape after use
    }

    public Body getBody() {
        return body;
    }
}
