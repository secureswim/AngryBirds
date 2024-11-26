package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ok.AngryBirds.Main;

public class PauseMenu extends State {
    private final Texture pause_bg;
    private final Texture pause_bar;
    private final Texture pause_level;
    private final Texture resume;
    private final Texture replay;
    private final Texture next;
    private final Texture levels;

    public PauseMenu(GameStateManager gsm) {
        super(gsm);
        pause_bg = new Texture("pause_menu_bg.png");
        pause_bar= new Texture("pause_bar.png");
        pause_level=new Texture("logo.png");
        resume = new Texture("play_button.png");
        replay = new Texture("replay_button.png");
        next = new Texture("next_button.png");
        levels = new Texture("levels_button.png");
    }

    @Override
    protected void handle_input() {

    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.enableBlending();

        sb.draw(pause_bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Dark overlay
        sb.draw(pause_bar,32,465,80,264);
        sb.draw(pause_level, ((float) (Main.width) /2)-250,600,500,135);
        sb.draw(resume, 30, 650, 85, 85); // Position and size can be adjusted
        sb.draw(replay, 39, 582, 64, 64);
        sb.draw(next, 39, 516, 64, 64);
        sb.draw(levels, 39, 450, 64, 64);

        sb.end();
    }


    @Override
    public void dispose() {

    }
}
