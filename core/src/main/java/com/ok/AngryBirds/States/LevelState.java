package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ok.AngryBirds.Main;

public class LevelState extends State {
    private final Texture levels;

    protected LevelState(GameStateManager gsm) {
        super(gsm);
        levels=new Texture("levels_ab.png");
    }

    @Override
    protected void hande_input() {
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float buttonX = 40;
            float buttonY = 40;
            float buttonWidthX = 240;
            float buttonHeightY = 240;

            if (touchX >= buttonX && touchX <= buttonWidthX &&
                touchY >= buttonY && touchY <=buttonHeightY) {
                gsm.set(new MenuState(gsm));
                dispose();
            }
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(levels,0,0);
        sb.end();
    }

    @Override
    public void dispose() {
        levels.dispose();
    }
}
