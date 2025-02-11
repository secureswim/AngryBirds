package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ok.AngryBirds.Sprites.*;
import com.ok.AngryBirds.utils.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Level_3 extends State {
    private final Texture levelBackground;
    private final Texture slingshot;
    private final Texture pause;

    private ArrayList<Bird> birds;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Pig> pigs;
    private Bird current_bird;

    private World world;
    private ShapeRenderer shape_renderer;
    private Box2DDebugRenderer debugRenderer;

    private static final float PIXELS_TO_METERS = 100f;
    private final float slingshot_centreX = 155;
    private final float slingshot_centreY = 345;

    private List<float[]> trajectory;

    private float startX, startY;
    private float endX, endY;
    private boolean is_dragging;

    private CollisionHandler collisionHandler;
    private Ground ground;
    private boolean isBirdLaunched;
    private final Texture save_game;


    public Level_3(GameStateManager gsm) {
        super(gsm);
        pause = new Texture("pause_button.png");

        levelBackground = new Texture("level1_background.jpg");
        slingshot = new Texture("slingshot_ab.png");
        save_game=new Texture("save_game.png");


        debugRenderer = new Box2DDebugRenderer();

        birds = new ArrayList<>();
        obstacles = new ArrayList<>();
        pigs = new ArrayList<>();

        shape_renderer = new ShapeRenderer();

        world = new World(new Vector2(0, -4.905f), true);
        collisionHandler = new CollisionHandler();
        world.setContactListener(collisionHandler);
        ground = new Ground(world, 0, 0, 1200/100f, 190/100f);

        birds.add(new RedBird(new Texture("red_ab.png"), 125, 331, world));
        birds.add(new BlackBird(new Texture("black_ab.png"), 55, 193, world));
        birds.add(new BlackBird(new Texture("black_ab.png"),20,193,world));

        current_bird = birds.get(0);

        isBirdLaunched=false;

        createAllBodies();
        setupUserData();
    }

    private void createAllBodies() {
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 730, 191, 16, 100, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 820, 191, 16, 100, world));
        obstacles.add(new IceObstacle(new Texture("h_ice_short.png"), 730, 291, 100, 14, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 830, 191, 16, 200, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 950, 191, 16, 200, world));
        obstacles.add(new SteelObstacle(new Texture("horizontal_steel.png"), 830, 391, 138, 16, world));
        obstacles.add(new SteelObstacle(new Texture("vertical_steel.png"), 850, 407, 14, 80, world));
        obstacles.add(new SteelObstacle(new Texture("vertical_steel.png"), 930, 407, 14, 80, world));
        obstacles.add(new SteelObstacle(new Texture("horizontal_steel.png"), 850, 487, 90, 14, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 970, 191, 16, 150, world));
        obstacles.add(new WoodObstacle(new Texture("vertical_wood.png"), 1060, 191, 16, 150, world));
        obstacles.add(new IceObstacle(new Texture("h_ice_short.png"), 975, 341, 100, 14, world));


        pigs.add(new ArmoredPig(new Texture("armoured_pig.png"), 870, 403, 27, world));
        pigs.add(new RegularPig(new Texture("regular_pig.png"), 750, 292, 25, world));
        pigs.add(new RegularPig(new Texture("regular_pig.png"), 1000, 198, 25, world));
        pigs.add(new BossPig(new Texture("boss_pig.png"), 860, 198, 35, world));


    }

    private void setupUserData(){
        for (Bird bird : birds) {
            bird.getBody().setUserData(bird);
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.getBody().setUserData(obstacle);
        }

        for (Pig pig : pigs) {
            pig.getBody().setUserData(pig);
        }
        ground.getBody().setUserData(ground);
    }

    @Override
    protected void handle_input() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 30 && x <= 115 && y >= 650 && y <= 735) {
                gsm.push(new PauseState_1(gsm, this));
                return;
            }

            if (x >= 900 && x <= 1150 && y >= 650 && y <= 717) {
                this.saveGameState("game_save.ser");
                gsm.push(new LevelState(gsm));
                return;
            }


            if (!is_dragging) {
                startX = x;
                startY = y;
                is_dragging = true;
            }

        } else if (Gdx.input.isTouched() && is_dragging) {
            endX = Gdx.input.getX();
            endY = Gdx.graphics.getHeight() - Gdx.input.getY();

            float dx = slingshot_centreX - endX;
            float dy = slingshot_centreY - endY;
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));

            float new_speed = Math.min((float) Math.sqrt(dx * dx + dy * dy) / 4, 50);

            trajectory = Trajectory.calculate_trajectory(new_speed, angle, 0.1f, 10.0f);
            current_bird.getBody().setTransform(
                endX / PIXELS_TO_METERS,
                endY / PIXELS_TO_METERS,
                0
            );
            current_bird.getBody().setAwake(false);

        } else if (!Gdx.input.isTouched() && is_dragging) {
            float dx = slingshot_centreX - endX;
            float dy = slingshot_centreY - endY;

            float speed = Math.min((float) Math.sqrt(dx * dx + dy * dy) / 100, 50);
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));

            current_bird.getBody().setAwake(true);
            current_bird.launch(speed, angle);

            isBirdLaunched=true;

            is_dragging = false;
            trajectory = null;
        }
    }

    public void saveGameState(String filePath) {
        try {
            GameStateData gameState = new GameStateData();
            gameState.birds = new ArrayList<>();
            gameState.obstacles = new ArrayList<>();
            gameState.pigs = new ArrayList<>();

            for (Bird bird : birds) {
                Body body = bird.getBody();
                String type;
                if (bird instanceof RedBird) {
                    type = "RedBird";
                } else if (bird instanceof BlackBird) {
                    type = "BlueBird";
                } else {
                    type = "YellowBird";
                }
                String texturePath = bird.getTexture().toString();
                gameState.birds.add(new BirdData(texturePath, body.getPosition().x, body.getPosition().y,
                    body.getLinearVelocity().x, body.getLinearVelocity().y,
                    body.getAngle(), type));
            }
            for (Pig pig : pigs) {
                Body body = pig.getBody();
                String type;
                if (pig instanceof RegularPig) {
                    type = "RegularPig";
                } else if (pig instanceof ArmoredPig) {
                    type = "ArmoredPig";
                } else {
                    type = "BossPig";
                }
                String texturePath = pig.getTexture().toString();
                gameState.pigs.add(new PigData(
                    texturePath,
                    pig.getX(),
                    pig.getY(),
                    body.getLinearVelocity().x,
                    body.getLinearVelocity().y,
                    body.getAngle(),
                    pig.getHealth(),
                    type,
                    pig.getRadius(),
                    pig.isHas_collided()
                ));
            }

            for (Obstacle obstacle : obstacles) {
                Body body = obstacle.getBody();
                String type;
                if (obstacle instanceof WoodObstacle) {
                    type = "WoodObstacle";
                } else if (obstacle instanceof IceObstacle) {
                    type = "IceObstacle";
                } else {
                    type = "SteelObstacle";
                }
                String texturePath = obstacle.getTexture().toString();
                gameState.obstacles.add(new ObstacleData(
                    texturePath,
                    obstacle.getPosX(),
                    obstacle.getPosY(),
                    body.getLinearVelocity().x,
                    body.getLinearVelocity().y,
                    body.getAngle(),
                    obstacle.getHealth(),
                    type,
                    obstacle.getWidth(),
                    obstacle.getHeight()
                ));
            }

            String type;
            if (current_bird instanceof RedBird) {
                type = "RedBird";
            } else if (current_bird instanceof BlackBird) {
                type = "BlueBird";
            } else {
                type = "YellowBird";
            }
            gameState.currentBird = new BirdData(
                current_bird.getTexture().toString(),
                current_bird.getBody().getPosition().x,
                current_bird.getBody().getPosition().y,
                current_bird.getBody().getLinearVelocity().x,
                current_bird.getBody().getLinearVelocity().y,
                current_bird.getBody().getAngle(),
                type
            );

            gameState.isBirdLaunched = isBirdLaunched;
            gameState.slingshotCentreX = slingshot_centreX;
            gameState.slingshotCentreY = slingshot_centreY;
            gameState.setCurrentLevel("Level_1");


            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameState);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGameState(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            GameStateData gameState = (GameStateData) ois.readObject();
            ois.close();
            fis.close();

            for (Bird bird : birds) world.destroyBody(bird.getBody());
            for (Pig pig : pigs) world.destroyBody(pig.getBody());
            for (Obstacle obstacle : obstacles) world.destroyBody(obstacle.getBody());

            birds.clear();
            pigs.clear();
            obstacles.clear();

            for (BirdData birdData : gameState.birds) {
                Texture texture = new Texture(birdData.getTexturePath());
                Bird bird = null;
                float posX = birdData.posX * PIXELS_TO_METERS;
                float posY = birdData.posY * PIXELS_TO_METERS;

                if ("RedBird".equals(birdData.getType())) {
                    bird = new RedBird(texture, posX, posY, world);
                } else if ("BlueBird".equals(birdData.getType())) {
                    bird = new BlackBird(texture, posX, posY, world);
                } else if ("YellowBird".equals(birdData.getType())) {
                    bird = new YellowBird(texture, posX, posY, world);
                }

                if (bird != null) {
                    bird.getBody().setLinearVelocity(birdData.velocityX, birdData.velocityY);
                    bird.setIs_launched(gameState.isBirdLaunched);
                    birds.add(bird);
                }
            }

            for (PigData pigData : gameState.pigs) {
                Texture texture = new Texture(pigData.texturePath);
                Pig pig = null;
                float posX = pigData.posX;
                float posY = pigData.posY;

                if ("RegularPig".equals(pigData.type)) {
                    pig = new RegularPig(texture, posX, posY, pigData.radius, world);
                } else if ("ArmoredPig".equals(pigData.type)) {
                    pig = new ArmoredPig(texture, posX, posY, pigData.radius, world);
                } else if ("BossPig".equals(pigData.type)) {
                    pig = new BossPig(texture, posX, posY, pigData.radius, world);
                }

                if (pig != null) {
                    pig.setHealth(pigData.health);
                    pig.setHas_collided(pigData.isHasCollided());
                    pigs.add(pig);
                }
            }

            for (ObstacleData obstacleData : gameState.obstacles) {
                Texture texture = new Texture(obstacleData.texturePath);
                Obstacle obstacle = null;
                float posX = obstacleData.posX;
                float posY = obstacleData.posY;

                if ("WoodObstacle".equals(obstacleData.type)) {
                    obstacle = new WoodObstacle(texture, posX, posY, obstacleData.width, obstacleData.height, world);
                } else if ("IceObstacle".equals(obstacleData.type)) {
                    obstacle = new IceObstacle(texture, posX, posY, obstacleData.width, obstacleData.height, world);
                } else if ("SteelObstacle".equals(obstacleData.type)) {
                    obstacle = new SteelObstacle(texture, posX, posY, obstacleData.width, obstacleData.height, world);
                }

                if (obstacle != null) {
                    obstacle.setHealth(obstacleData.health);
                    obstacles.add(obstacle);
                }
            }

            if (gameState.currentBird != null && !birds.isEmpty()) {
                current_bird = birds.get(0);
            }

            isBirdLaunched = gameState.isBirdLaunched;
            setupUserData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(float dt) {

        world.step(dt, 6, 2);
        handle_input();
        for (Bird bird : birds) {
            bird.update(dt);
        }
        for (Iterator<Bird> iterator = birds.iterator(); iterator.hasNext(); ) {
            Bird bird = iterator.next();
            bird.update(dt);
            if (bird == current_bird && isBirdLaunched) {
                Vector2 velocity = bird.getBody().getLinearVelocity();
                if (velocity.len() < 0.05f) {
                    if (bird.getDestructionTimer() == 0) {
                        bird.startDestructionTimer();
                    }
                    if (bird.getDestructionTimer() >= 4.0f) {
                        bird.getTexture().dispose();
                        world.destroyBody(bird.getBody());
                        iterator.remove();
                        isBirdLaunched = false;
                        if (!birds.isEmpty()) {
                            current_bird = birds.get(0);
                            current_bird.getBody().setTransform(
                                slingshot_centreX / PIXELS_TO_METERS,
                                slingshot_centreY / PIXELS_TO_METERS,
                                0
                            );
                            current_bird.getBody().setAwake(false);
                        } else {
                            current_bird = null;
                        }
                    }
                }
            }
        }


        Iterator<Obstacle> obstacleIterator = obstacles.iterator();
        while (obstacleIterator.hasNext()) {
            Obstacle obstacle = obstacleIterator.next();
            if (obstacle.getBody().getType() == BodyDef.BodyType.DynamicBody) {
                Vector2 velocity = obstacle.getBody().getLinearVelocity();
                if (velocity.len() < 0.05f) {
                    obstacle.getBody().setLinearVelocity(0, 0);
                    obstacle.getBody().setAngularVelocity(0);
                }
            }
            if (collisionHandler.getBodiesToDestroy().contains(obstacle.getBody())) {
                obstacleIterator.remove();
            }
        }

        Iterator<Pig> pigIterator = pigs.iterator();
        while (pigIterator.hasNext()) {
            Pig pig = pigIterator.next();
            if (pig.getBody().getType() == BodyDef.BodyType.DynamicBody) {
                Vector2 velocity = pig.getBody().getLinearVelocity();
                if (velocity.len() < 0.05f) {
                    pig.getBody().setLinearVelocity(0, 0);
                    pig.getBody().setAngularVelocity(0);
                }
            }
            if (collisionHandler.getBodiesToDestroy().contains(pig.getBody())) {
                pigIterator.remove();
            }
        }

        List<Body> bodiesToDestroy = collisionHandler.getBodiesToDestroy();
        for (Body body : bodiesToDestroy) {
            if (body != null) {
                world.destroyBody(body);
            }
        }
        bodiesToDestroy.clear();

        if (birds.isEmpty() && !pigs.isEmpty()) {
            gsm.push(new LoseState_1(gsm, this));
        } else if (pigs.isEmpty()) {
            gsm.push(new WinState_1(gsm, this));
        }
    }



    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(levelBackground, 0, 0);
        sb.draw(slingshot, 50, 190, 190, 190);
        sb.draw(pause, 30, 650, 85, 85);
        sb.draw(save_game,900,650,250,67);


        for (Bird bird : birds) {
            sb.draw(bird.getTexture(),
                bird.getPosX()-25,
                bird.getPosY()-25,
                50, 50
            );
        }

        if (current_bird != null) {
            sb.draw(
                current_bird.getTexture(),
                current_bird.getPosX() - 25,
                current_bird.getPosY() - 25,
                50, 50
            );
        }

        for (Obstacle obstacle : obstacles) {
            Texture txt = obstacle.getTexture();
            float bodyX = obstacle.getBody().getPosition().x * PIXELS_TO_METERS;
            float bodyY = obstacle.getBody().getPosition().y * PIXELS_TO_METERS;
            sb.draw(
                txt,
                bodyX - obstacle.getWidth() / 2,
                bodyY - obstacle.getHeight() / 2,
                obstacle.getWidth() / 2,
                obstacle.getHeight() / 2,
                obstacle.getWidth(),
                obstacle.getHeight(),
                1f,
                1f,
                obstacle.getBody().getAngle() * MathUtils.radiansToDegrees,
                0, 0,
                txt.getWidth(),
                txt.getHeight(),
                false,
                false
            );
        }


        for (Pig pig : pigs) {
            Texture txt = pig.getTexture();
            float diameter = pig.getRadius() * 2;
            sb.draw(
                txt,
                pig.getBody().getPosition().x * PIXELS_TO_METERS - pig.getRadius(),
                pig.getBody().getPosition().y * PIXELS_TO_METERS - pig.getRadius(),
                diameter,
                diameter
            );
        }

        if (trajectory != null && is_dragging) {
            shape_renderer.begin(ShapeRenderer.ShapeType.Line);
            shape_renderer.setColor(0, 0, 0, 1);
            for (float[] point : trajectory) {
                shape_renderer.circle(slingshot_centreX + point[0],slingshot_centreY - point[1],5);
            }
            shape_renderer.end();

            shape_renderer.begin(ShapeRenderer.ShapeType.Filled);
            shape_renderer.setColor(1, 1, 1, 1);

            for (float[] point : trajectory) {
                shape_renderer.circle(
                    slingshot_centreX + point[0],
                    slingshot_centreY - point[1],  // Same Y-axis inversion
                    4
                );
            }
            shape_renderer.end();
        }
        sb.end();
//        debugRenderer.render(world, sb.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 1));
    }

    @Override
    public void dispose() {
        levelBackground.dispose();
        slingshot.dispose();
        shape_renderer.dispose();
        world.dispose();
        collisionHandler = null;
        save_game.dispose();


        for (Bird bird : birds) bird.getTexture().dispose();
        for (Obstacle obstacle : obstacles) obstacle.getTexture().dispose();
        for (Pig pig : pigs) pig.getTexture().dispose();
    }
}
