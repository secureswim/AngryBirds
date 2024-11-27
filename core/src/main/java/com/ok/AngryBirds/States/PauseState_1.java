package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;

import com.ok.AngryBirds.Main;

public class PauseState_1 extends State{

    private final ShapeRenderer shapeRenderer;
    private final State currentState;
    private final Texture pause_bar;
    private final Texture pause_level;
    private final Texture resume;
    private final Texture replay;
    private final Texture next;
    private final Texture levels;

    protected PauseState_1(GameStateManager gsm, State currentState) {
        super(gsm);
        shapeRenderer=new ShapeRenderer();
        this.currentState = currentState;
        pause_bar = new Texture("pause_bar.png");
        pause_level = new Texture("logo.png");
        resume = new Texture("play_button.png");
        replay = new Texture("replay_button.png");
        next = new Texture("next_button.png");
        levels = new Texture("levels_button.png");

    }

    @Override
    protected void handle_input() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 30 && x <= 115 && y >= 650 && y <= 735) {
                gsm.pop();
            }

            if(x >= 39 && x <= 103 && y >=582 && y<=646) {
                gsm.pop();
                gsm.pop();
                gsm.push(new Level_1(gsm));
            }
            if(x >= 39 && x <= 103 && y >=516 && y<=580) {
                gsm.pop();
                gsm.push(new Level_2(gsm));
            }

            if(x >= 39 && x <= 103 && y >=450 && y<=514){
                gsm.pop();
                gsm.push(new LevelState(gsm));
            }
        }
    }


    @Override
    public void update(float dt) {
        handle_input();
    }

    @Override
    public void render(SpriteBatch sb) {
        currentState.render(sb);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.7f); // 50% transparency
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        sb.begin();
        sb.draw(pause_bar, 32, 465, 80, 264);
        sb.draw(pause_level, ((float) Main.width / 2) - 250, 600, 500, 135);
        sb.draw(resume, 30, 650, 85, 85);
        sb.draw(replay, 39, 582, 64, 64);
        sb.draw(next, 39, 516, 64, 64);
        sb.draw(levels, 39, 450, 64, 64);
        sb.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        pause_bar.dispose();
        pause_level.dispose();
        resume.dispose();
        replay.dispose();
        next.dispose();
        levels.dispose();
    }
}
