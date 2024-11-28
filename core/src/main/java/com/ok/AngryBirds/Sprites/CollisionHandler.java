package com.ok.AngryBirds.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler implements ContactListener {
    private List<Body> bodiesToDestroy = new ArrayList<>();

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        Vector2 velocityA = bodyA.getLinearVelocity();
        Vector2 velocityB = bodyB.getLinearVelocity();
        float collisionVelocity = velocityA.sub(velocityB).len();


        if(bodyA.getUserData() instanceof Pig || bodyB.getUserData() instanceof Ground){
            System.out.println("Pig hit the ground");
            System.out.println(collisionVelocity);
        }

        if (bodyA.getUserData() instanceof Pig && bodyB.getUserData() instanceof Ground) {
            handlePigAndGroundCollision(bodyA, bodyB, collisionVelocity);
        } else if (bodyB.getUserData() instanceof Pig && bodyA.getUserData() instanceof Ground) {
            handlePigAndGroundCollision(bodyB, bodyA, collisionVelocity);
        }


        boolean is_colliding = false;

        if (collisionVelocity > 2.0f) {
            is_colliding = true;
        }
        if(is_colliding) {
            if (bodyA.getUserData() == null || bodyB.getUserData() == null) {
                return;
            }
            if (bodyA.getUserData() instanceof Bird || bodyB.getUserData() instanceof Bird) {
                handleBirdCollision(bodyA, bodyB);
            }
            if (bodyA.getUserData() instanceof Pig && bodyB.getUserData() instanceof Pig) {
                handlePigCollision(bodyA, bodyB);
            }
            if (bodyA.getUserData() instanceof Obstacle && bodyB.getUserData() instanceof Obstacle) {
                handleObstacleCollision(bodyA, bodyB);
            }
            if (bodyA.getUserData() instanceof Pig && bodyB.getUserData() instanceof Ground) {
                handlePigCollision(bodyA, bodyB);
            }
            if (bodyA.getUserData() instanceof Obstacle && bodyB.getUserData() instanceof Ground) {
                handleObstacleCollision(bodyA, bodyB);
            }
            if (bodyA.getUserData() instanceof Pig && bodyB.getUserData() instanceof Obstacle) {
                handlePigCollision(bodyA, bodyB);
            }
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
                pig.setHas_collided(true);
                if (pig.isDestroyed()) {
                    destroyPig(pig, otherBody);
                }
            }
        }
    }

    private void handlePigAndGroundCollision(Body pigBody, Body groundBody, float collisionVelocity) {
        Pig pig = (Pig) pigBody.getUserData();
        if (collisionVelocity > 2.0f) { // Only destroy if the impact velocity is significant
            pig.setHealth(-1); // Decrease health or destroy directly
            if (pig.isDestroyed()) {
                destroyPig(pig, pigBody);
            }
        }
    }

    public void handlePigCollision(Body bodyA, Body bodyB){
        Body pigBody = null;
        Body otherBody = null;

        System.out.println("im here");


        if(isBodyAPig(bodyA)){
            pigBody=bodyA;
            otherBody=bodyB;
        }
        else{
            pigBody=bodyB;
            otherBody=bodyA;
        }

        Pig pig = (Pig) pigBody.getUserData();
        pig.setHas_collided(true);

        if (isBodyAnObstacle(otherBody)) {
            Obstacle obstacle = (Obstacle) otherBody.getUserData();
            if (obstacle != null && pig != null) {
                obstacle.reduceHealth(5);
                pig.reduceHealth(10);
                if (obstacle.isDestroyed()) {
                    destroyObstacle(obstacle, otherBody);
                }
                if (pig.isDestroyed()) {
                    destroyPig(pig, pigBody);
                }
            }
        }

        if (isBodyAPig(otherBody)) {
            Pig pig2 = (Pig) otherBody.getUserData();
            if (pig2 != null && pig != null) {
                pig.setHealth(-1);
                pig2.setHealth(-1);
                if(pig.isDestroyed()){
                    destroyPig(pig,pigBody);
                }
                if(pig2.isDestroyed()){
                    destroyPig(pig2,otherBody);
                }
            }
        }

        if(isBodyGround(otherBody)){
            if (pig != null) {
                System.out.println("im here");
                pig.setHealth(-1);
                destroyPig(pig,pigBody);
            }
        }
    }

    public void handleObstacleCollision(Body bodyA, Body bodyB) {
        Body obstacleBody = null;
        Body otherBody = null;


        if (isBodyAnObstacle(bodyA)) {
            obstacleBody = bodyA;
            otherBody = bodyB;
        } else {
            obstacleBody = bodyB;
            otherBody = bodyA;
        }

        Obstacle obstacle = (Obstacle) obstacleBody.getUserData();
        if(isBodyAnObstacle(otherBody)){
            Obstacle obstacle2=(Obstacle) otherBody.getUserData();
            if(obstacle2!=null && obstacle!=null){
                obstacle.reduceHealth(5);
                obstacle2.reduceHealth(5);
                if(obstacle.isDestroyed()){
                    destroyObstacle(obstacle,obstacleBody);
                }
                if(obstacle2.isDestroyed()){
                    destroyObstacle(obstacle2,otherBody);
                }
            }
        }

        if(isBodyGround(otherBody)){
            if(obstacle!=null) {
                obstacle.setHealth(-1);
                if(obstacle.isDestroyed()){
                    destroyObstacle(obstacle,obstacleBody);
                }
            }
        }
    }

    private void destroyObstacle(Obstacle obstacle, Body obstacleBody) {
        bodiesToDestroy.add(obstacleBody);
        System.out.println("Obstacle destroyed: " + obstacle);
        obstacle.getTexture().dispose();
    }

    private void destroyPig(Pig pig, Body pigBody) {
        bodiesToDestroy.add(pigBody);
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

    private boolean isBodyGround(Body body){
        return body.getUserData() instanceof Ground;
    }


    public List<Body> getBodiesToDestroy() {
        return bodiesToDestroy;
    }

}
