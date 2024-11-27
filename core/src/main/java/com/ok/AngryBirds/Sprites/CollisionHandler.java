package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class CollisionHandler implements ContactListener {
    private static final float DAMAGE_VELOCITY_THRESHOLD = 5.0f;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

//        System.out.println("COLLISION DETECTED!");
//
//        System.out.println("Body A user data: " + bodyA.getUserData());
//        System.out.println("Body B user data: " + bodyB.getUserData());

        if (bodyA.getUserData() == null || bodyB.getUserData() == null) {
            //System.out.println("Collision: One or both fixtures are null!");
            return;
        }

        if (bodyA.getUserData() instanceof Bird || bodyB.getUserData() instanceof Bird) {
            handleBirdCollision(bodyA, bodyB);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Optional: Implement any end-of-contact logic if needed
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Optional: Pre-collision processing
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Optional: Post-collision processing
    }

    private void handleBirdCollision(Body bodyA, Body bodyB) {
        Body birdBody = null;
        Body otherBody = null;


        if(isBodyABird(bodyA)){
            birdBody=bodyA;
            otherBody=bodyB;
        }
        else{
            birdBody=bodyB;
            otherBody=bodyA;
        }

        Bird bird = (Bird) birdBody.getUserData();

        if (isBodyAnObstacle(otherBody)) {
            Obstacle obstacle = (Obstacle) otherBody.getUserData();
            if (obstacle != null && bird != null) {
                obstacle.reduceHealth(bird.getDamage());
                if (obstacle.isDestroyed()) {
                    destroyObstacle(obstacle, otherBody);
                }
            }
        }

        if (isBodyAPig(otherBody)) {
            Pig pig = (Pig) otherBody.getUserData();
            if (pig != null && bird != null) {
                pig.reduceHealth(bird.getDamage());
                if (pig.isDestroyed()) {
                    destroyPig(pig, otherBody);
                }
            }
        }
    }

    private void destroyObstacle(Obstacle obstacle, Body obstacleBody) {
        System.out.println("Obstacle destroyed: " + obstacle);
        obstacle.getTexture().dispose();
    }

    private void destroyPig(Pig pig, Body pigBody) {
//        pigBody.getWorld().destroyBody(pigBody);
        System.out.println("Obstacle destroyed: " + pig);
        pig.getTexture().dispose();
    }

    private boolean isBodyABird(Body body) {
        return body.getUserData() instanceof Bird;
    }

    private boolean isBodyAnObstacle(Body body) {
        return body.getUserData() instanceof Obstacle;
    }

    private boolean isBodyAPig(Body body){
        return body.getUserData() instanceof Pig;
    }
}
