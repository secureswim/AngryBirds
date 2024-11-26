package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.physics.box2d.*;

public class Ground {
    public Ground(World world){
        createGround(world);
    }
    private void createGround(World world) {
        // Define the ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(800 / 100f, 50 / 100f); // Centered horizontally, near bottom of the screen
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        Body groundBody = world.createBody(groundBodyDef);

        // Define the ground shape
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(800 / 100f, 10 / 100f); // Width = screen width, Height = 10 pixels

        // Define the fixture
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f; // Slight friction for bird-land interactions

        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose(); // Dispose of shape after use
    }

}
