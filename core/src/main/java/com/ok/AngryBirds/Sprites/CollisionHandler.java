package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class CollisionHandler implements ContactListener {
    // Velocity threshold for causing damage (in m/s)
    private static final float DAMAGE_VELOCITY_THRESHOLD = 5.0f;

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
        float collisionVelocity = Math.abs(velocityA.sub(velocityB).len());

        // Handle different collision scenarios
        if (isBodyABird(bodyA) || isBodyABird(bodyB)) {
            handleBirdCollision(bodyA, bodyB, collisionVelocity);
        }

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

        Bird bird = (Bird) birdBody.getUserData();

        // Check if the bird collided with an obstacle
        if (isBodyAnObstacle(otherBody)) {
            Obstacle obstacle = (Obstacle) otherBody.getUserData();
            if (obstacle != null && bird != null) {
                obstacle.reduceHealth(bird.getDamage());
                if (obstacle.isDestroyed()) {
                    destroyObstacle(obstacle, otherBody);
                }
            }
        }
    }

    private void handleObstacleCollision(Body bodyA, Body bodyB, float collisionVelocity) {
        // Retrieve the obstacle bodies
        Body obstacleBodyA = isBodyAnObstacle(bodyA) ? bodyA : null;
        Body obstacleBodyB = isBodyAnObstacle(bodyB) ? bodyB : null;

        if (obstacleBodyA != null && obstacleBodyB != null) {
            Obstacle obstacleA = (Obstacle) obstacleBodyA.getUserData();
            Obstacle obstacleB = (Obstacle) obstacleBodyB.getUserData();

            // Both obstacles lose health upon collision
            if (obstacleA != null) {
                obstacleA.reduceHealth(1);
                if (obstacleA.isDestroyed()) {
                    destroyObstacle(obstacleA, obstacleBodyA);
                }
            }

            if (obstacleB != null) {
                obstacleB.reduceHealth(1);
                if (obstacleB.isDestroyed()) {
                    destroyObstacle(obstacleB, obstacleBodyB);
                }
            }
        }
    }

    private void destroyObstacle(Obstacle obstacle, Body obstacleBody) {
        // Dispose the obstacle's resources and remove it from the physics world
        obstacleBody.getWorld().destroyBody(obstacleBody);
        System.out.println("Obstacle destroyed: " + obstacle);
    }

    // Utility methods to identify body types
    private boolean isBodyABird(Body body) {
        return body.getUserData() instanceof Bird;
    }

    private boolean isBodyAnObstacle(Body body) {
        return body.getUserData() instanceof Obstacle;
    }
}
