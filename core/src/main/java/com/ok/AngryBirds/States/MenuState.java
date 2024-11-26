package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ok.AngryBirds.Main;

public class MenuState extends State{
    private Texture background;
    private Texture playbutton;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        background=new Texture("new_bg.png");
        playbutton=new Texture("main_play_button.png");
    }

    @Override
    protected void handle_input() {
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float buttonX = 494;
            float buttonY = 24;
            float buttonWidth = 180;
            float buttonHeight = 90;

            if (touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                gsm.push(new LevelState(gsm));
                dispose();
            }
        }
    }

    @Override
    public void update(float dt) {
        handle_input();
    }

    @Override
    public void dispose() {
        background.dispose();
        playbutton.dispose();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0, Main.width,Main.height);
        sb.draw(playbutton,495,24,180,90);
        sb.end();
    }
}
