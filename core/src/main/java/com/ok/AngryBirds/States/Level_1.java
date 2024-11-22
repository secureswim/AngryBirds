package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ok.AngryBirds.utils.Trajectory;
import com.ok.AngryBirds.Sprites.Bird;

import java.util.List;

public class Level_1 extends State {
    private final Texture level1;
    private final Texture slingshot;
    private final Texture bird;
    private final Texture bird2;
    private final Texture wood1;
    private final Texture wood2;
    private final Texture wood3;
    private final Texture pig1;
    private final Texture ice1;
    private final Texture block;
    private final Texture triangle1;
    private final Texture triangle2;
    private final Texture pause;
    private final Texture win;
    private final Texture lose;

    private final float slingshot_centreX = 155;
    private final float slingshot_centreY = 345;


    private Bird current_bird;
    private ShapeRenderer shape_renderer;
    private List<float[]> trajectory;

    private float startX, startY;
    private float endX, endY;
    private boolean is_dragging;


    public Level_1(GameStateManager gsm) {
        super(gsm);
        level1 = new Texture("level1_background.jpg");
        slingshot = new Texture("slingshot_ab.png");
        bird = new Texture("red_ab.png");
        bird2=new Texture("yellow_ab.png");
        wood1 = new Texture("vertical_wood.png");
        wood2 = new Texture("vertical_wood.png");
        wood3 = new Texture("horizontal_wood.png");
        pig1 = new Texture("pig1.png");
        ice1 = new Texture("v_ice_short.png");
        block = new Texture("ice_block.png");
        triangle1 = new Texture("ice_tri_left.png");
        triangle2 = new Texture("ice_tri_right.png");
        pause = new Texture("pause_button.png");
        win = new Texture("win_button.png");
        lose = new Texture("lose_button.png");

        shape_renderer = new ShapeRenderer();
        current_bird = new Bird(bird);
    }

    @Override
    protected void hande_input() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (x >= 30 && x <= 115 && y >= 650 && y <= 735) {
                    gsm.push(new PauseState(gsm,this));
                    return;
                }
                if (x >= 1110 && x <= 1180 && y >= 665 && y <= 735) {
                    gsm.push(new WinState(gsm,this));

                } else if (x >= 1110 && x <= 1180 && y >= 595 && y <= 665) {
                    gsm.push(new LoseState(gsm,this));
                }

                if (!is_dragging) {
                    startX = x;
                    startY = y;
                    is_dragging = true;
                }

            } else if (Gdx.input.isTouched() && is_dragging) {
                endX=Gdx.input.getX();
                endY=Gdx.graphics.getHeight()-Gdx.input.getY();

                float dx= slingshot_centreX -endX;
                float dy= slingshot_centreY -endY;
                float angle=(float) Math.toDegrees(Math.atan2(dy, dx));
                float speed = (float) Math.sqrt(dx * dx + dy * dy) / 3;
                if (speed < 1) {
                    speed = 1;
                }

            trajectory=Trajectory.calculate_trajectory(speed , angle, 0.1f, 10.0f);

                current_bird.setPosX(endX-25);
                current_bird.setPosY(endY-25);
            }


            else if (!Gdx.input.isTouched() && is_dragging) {
                float dx = slingshot_centreX - endX;
                float dy = slingshot_centreY - endY;
                float speed = (float) Math.sqrt(dx * dx + dy * dy) / 10;
                float angle = (float) Math.toDegrees(Math.atan2(dy, dx));

                current_bird.setSpeed(speed);
                current_bird.setAngle(angle);
                current_bird.launch(speed,angle);

                is_dragging = false;
                trajectory = null;

                current_bird.setPosX(slingshot_centreX -25);
                current_bird.setPosY(slingshot_centreY -25);
        }

    }

    @Override
    public void update(float dt) {
        hande_input();

        // Update bird position if it is launched
        if (current_bird.isIs_launched()) {
            current_bird.update(dt);

            // Check if the bird has fallen out of bounds or hit the ground
            if (current_bird.getPosY() < 0) {
                current_bird.reset(); // Reset bird position
            }
        }
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(level1, 0, 0);
        sb.draw(slingshot, 50, 190, 190, 190);
        sb.draw(bird, current_bird.getPosX(), current_bird.getPosY(), 50, 50);
        sb.draw(bird2,55,193,50,50);
        sb.draw(wood1, 860, 191, 16, 150);
        sb.draw(wood2, 992, 191, 16, 150);
        sb.draw(wood3, 855, 333, 155, 16);
        sb.draw(pig1, 903, 403, 57, 57);
        sb.draw(ice1, 900, 193, 16, 100);
        sb.draw(ice1, 952, 193, 16, 100);
        sb.draw(wood3, 898, 288, 69, 16);
        sb.draw(block, 902, 348, 60, 60);
        sb.draw(triangle1, 860, 348, 42, 42);
        sb.draw(triangle2, 962, 348, 42, 42);
        sb.draw(pause, 30, 650, 85, 85);
        sb.draw(win, 1110, 665, 70, 70);
        sb.draw(lose, 1110, 595, 70, 70);

        sb.end();

        if (trajectory != null && is_dragging) {
            shape_renderer.begin(ShapeRenderer.ShapeType.Line);
            shape_renderer.setColor(0, 0, 0, 1);
            for (float[] point : trajectory) {
                shape_renderer.circle(slingshot_centreX + point[0], slingshot_centreY + point[1], 5);
            }
            shape_renderer.end();

            shape_renderer.begin(ShapeRenderer.ShapeType.Filled);
            shape_renderer.setColor(1, 1, 1, 1);


            for (float[] point : trajectory) {
                shape_renderer.circle(slingshot_centreX + point[0], slingshot_centreY + point[1], 4);
            }
            shape_renderer.end();
        }

    }

    @Override
    public void dispose() {
        level1.dispose();
        slingshot.dispose();
        bird.dispose();
        bird2.dispose();
        wood1.dispose();
        wood2.dispose();
        wood3.dispose();
        pig1.dispose();
        ice1.dispose();
        block.dispose();
        triangle1.dispose();
        triangle2.dispose();
        pause.dispose();
        win.dispose();
        lose.dispose();
    }

}
