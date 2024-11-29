package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.physics.box2d.*;

public class Catapult {
    private static final float PPM = 100f;
    private Body body;
    private World world;

    public Catapult(World world, float x, float y) {
        this.world = world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / PPM, y / PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }
}
