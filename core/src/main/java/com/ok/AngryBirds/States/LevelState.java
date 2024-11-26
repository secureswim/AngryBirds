package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ok.AngryBirds.Main;

public class LevelState extends State {
    private final Texture levels;
    private final Texture play;
    private final Texture back;


    protected LevelState(GameStateManager gsm) {
        super(gsm);
        levels=new Texture("levels_ab.png");
        play=new Texture("main_play_button.png");
        back=new Texture("back_button.png");

    }

    @Override
    protected void handle_input() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float backX = 50;
            float backY = 30;
            float backWidthX = 90;
            float backHeightY = 90;

            if (touchX >= backX && touchX <= backX+ backWidthX &&
                touchY >= backY && touchY <=backY+ backHeightY) {
                gsm.push(new MenuState(gsm));
                dispose();
            }
        }
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float playX = 505;
            float playY = 30;
            float playWidthX = 200;
            float playWidthY = 100;

            if (touchX >= playX && touchX <= playX + playWidthX &&
                touchY >= playY && touchY <= playY + playWidthY) {
                gsm.push(new Level_1(gsm));
                dispose();
            }
        }
    }

    @Override
    public void update(float dt) {
        handle_input();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(levels,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        sb.draw(play,505,30,200,100);
        sb.draw(back,50,30,90,90);
        sb.end();
    }

    @Override
    public void dispose() {
        levels.dispose();
        play.dispose();
        back.dispose();
    }
}
