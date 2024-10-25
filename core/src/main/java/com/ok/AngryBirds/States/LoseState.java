package com.ok.AngryBirds.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;

import com.ok.AngryBirds.Main;

public class LoseState extends State{

    private final ShapeRenderer shapeRenderer;
    private final State currentState;
    private final Texture lose_bar;
    private final Texture replay;
    private final Texture levels;

    protected LoseState(GameStateManager gsm, State currentState) {
        super(gsm);
        shapeRenderer=new ShapeRenderer();
        this.currentState = currentState;
        lose_bar = new Texture("lose_screen.png");
        replay = new Texture("replay_button.png");
        levels = new Texture("levels_button.png");

    }

    @Override
    protected void hande_input() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 500 && x <= 564 && y >= 200 && y <= 264) {
                gsm.pop();
                gsm.pop();
                gsm.push(new Level_1(gsm));
            }
            if (x >= 600 && x <= 664 && y >= 200 && y <= 264) {
                gsm.pop();
                gsm.push(new LevelState(gsm));
            }
        }
    }


    @Override
    public void update(float dt) {
        hande_input();
    }

    @Override
    public void render(SpriteBatch sb) {
        currentState.render(sb);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.7f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        sb.begin();
        sb.draw(lose_bar, 350, 150,500,500);
        sb.draw(replay, 500, 200, 64, 64);
        sb.draw(levels, 600, 200, 64, 64);
        sb.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        lose_bar.dispose();
        replay.dispose();
        levels.dispose();
    }
}
