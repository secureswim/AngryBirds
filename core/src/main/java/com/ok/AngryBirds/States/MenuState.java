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
        playbutton=new Texture("play_ab.png");
    }

    @Override
    protected void hande_input() {
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float buttonX = (float) Main.width / 2 - (float) playbutton.getWidth() / 2;
            float buttonY = (float) (Main.height / 2) - 100;
            float buttonWidth = playbutton.getWidth();
            float buttonHeight = playbutton.getHeight();

            if (touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                gsm.push(new LevelState(gsm));
                dispose();
            }
        }
    }

    @Override
    public void update(float dt) {
        hande_input();
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
        //sb.draw(playbutton,((float) Main.width /2)-((float) playbutton.getWidth() /2), (float) (Main.height /2)-100);
        sb.end();
    }
}
