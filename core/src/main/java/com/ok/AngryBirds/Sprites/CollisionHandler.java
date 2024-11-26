package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class CollisionHandler implements ContactListener {
    // Velocity threshold for causing damage (in m/s)
    private static final float DAMAGE_VELOCITY_THRESHOLD = 5.0f;

    // Damage amounts
    private static final int BIRD_COLLISION_DAMAGE = 1;
    private static final int FALL_DAMAGE = 2;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Determine the bodies involved in the collision
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        // Get the collision velocity
        Vector2 velocityA = bodyA.getLinearVelocity();
        Vector2 velocityB = bodyB.getLinearVelocity();
        float collisionVelocity = Math.max(
            Math.abs(velocityA.len()),
            Math.abs(velocityB.len())
        );

        // Check if collision involves a Bird
        if (isBodyABird(bodyA) || isBodyABird(bodyB)) {
            handleBirdCollision(bodyA, bodyB, collisionVelocity);
        }

        // Check if collision involves a Pig
        if (isBodyAPig(bodyA) || isBodyAPig(bodyB)) {
            handlePigCollision(bodyA, bodyB, collisionVelocity);
        }

        // Check if collision involves an Obstacle
        if (isBodyAnObstacle(bodyA) || isBodyAnObstacle(bodyB)) {
            handleObstacleCollision(bodyA, bodyB, collisionVelocity);
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

    private void handleBirdCollision(Body bodyA, Body bodyB, float collisionVelocity) {
        // Retrieve the bird body
        Body birdBody = isBodyABird(bodyA) ? bodyA : bodyB;
        Body otherBody = birdBody == bodyA ? bodyB : bodyA;

        // Check if bird has enough velocity to cause damage
        if (collisionVelocity >= DAMAGE_VELOCITY_THRESHOLD) {
            // If colliding with a Pig
            if (isBodyAPig(otherBody)) {
                Pig pig = (Pig) otherBody.getUserData();
                if (pig != null) {
                    pig.reduceHealth(BIRD_COLLISION_DAMAGE);
                }
            }
            // If colliding with an Obstacle
            else if (isBodyAnObstacle(otherBody)) {
                Obstacle obstacle = (Obstacle) otherBody.getUserData();
                if (obstacle != null) {
                    // Potentially implement obstacle damage logic
                }
            }
        }
    }

    private void handlePigCollision(Body bodyA, Body bodyB, float collisionVelocity) {
        // Retrieve the pig body
        Body pigBody = isBodyAPig(bodyA) ? bodyA : bodyB;
        Body otherBody = pigBody == bodyA ? bodyB : bodyA;

        // Check if pig falls to ground or collides with high velocity
        if (collisionVelocity >= DAMAGE_VELOCITY_THRESHOLD) {
            Pig pig = (Pig) pigBody.getUserData();
            if (pig != null) {
                // Apply fall damage or collision damage
                pig.reduceHealth(FALL_DAMAGE);
            }
        }
    }

    private void handleObstacleCollision(Body bodyA, Body bodyB, float collisionVelocity) {
        // Retrieve the obstacle body
        Body obstacleBody = isBodyAnObstacle(bodyA) ? bodyA : bodyB;
        Body otherBody = obstacleBody == bodyA ? bodyB : bodyA;

        // Check if obstacle receives significant impact
        if (collisionVelocity >= DAMAGE_VELOCITY_THRESHOLD) {
            Obstacle obstacle = (Obstacle) obstacleBody.getUserData();
            if (obstacle != null) {
                // Potential damage logic for obstacles
                // This could involve reducing structural integrity, breaking, etc.
            }
        }
    }

    // Utility methods to identify body types
    private boolean isBodyABird(Body body) {
        return body.getUserData() instanceof Bird;
    }

    private boolean isBodyAPig(Body body) {
        return body.getUserData() instanceof Pig;
    }

    private boolean isBodyAnObstacle(Body body) {
        return body.getUserData() instanceof Obstacle;
    }
}
