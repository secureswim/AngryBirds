package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ok.AngryBirds.Main;

import java.awt.*;

public class LevelState extends State {
    private final Texture levels;
    private final Texture square;

    protected LevelState(GameStateManager gsm) {
        super(gsm);
        levels=new Texture("levels_ab.png");
        square=new Texture("square.png");
    }

    @Override
    protected void hande_input() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float buttonX = 40;
            float buttonY = 40;
            float buttonWidthX = 100;
            float buttonHeightY = 100;

            if (touchX >= buttonX && touchX <= buttonX+buttonWidthX &&
                touchY >= buttonY && touchY <=buttonY+buttonHeightY) {
                gsm.pop();
                dispose();
            }
        }
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float buttonX = 35;
            float buttonY = 240;
            float buttonWidthX = 90;
            float buttonHeightY = 90;

            if (touchX >= buttonX && touchX <= buttonX+buttonWidthX &&
                touchY >= buttonY && touchY <=buttonY+buttonHeightY) {
                gsm.push(new Level_1(gsm));
                dispose();
            }
        }
    }

    @Override
    public void update(float dt) {
        hande_input();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(levels,0,0);
        //sb.draw(square,35,240,90,90);
        sb.end();
    }

    @Override
    public void dispose() {
        levels.dispose();
        square.dispose();
    }
}
