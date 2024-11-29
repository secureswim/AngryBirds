package com.ok.AngryBirds.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;

public class WinState_2 extends State{

    private final ShapeRenderer shapeRenderer;
    private final State currentState;
    private final Texture win_bar;
    private final Texture replay;
    private final Texture next;
    private final Texture levels;

    protected WinState_2(GameStateManager gsm, State currentState) {
        super(gsm);
        shapeRenderer=new ShapeRenderer();
        this.currentState = currentState;
        win_bar = new Texture("win_screen.png");
        replay = new Texture("replay_button.png");
        next = new Texture("next_button.png");
        levels = new Texture("levels_button.png");

    }

    @Override
    protected void handle_input() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 470 && x <= 534 && y >= 200 && y <= 264) {
                gsm.pop();
                gsm.pop();
                gsm.push(new Level_2(gsm));
            }
            if (x >= 570 && x <= 634 && y >= 200 && y <= 264) {
                gsm.set(new Level_3(gsm));
            }
            if (x >= 670 && x <= 734 && y >= 200 && y <= 264) {
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
        shapeRenderer.setColor(0, 0, 0, 0.7f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        sb.begin();
        sb.draw(win_bar, 350, 150,500,500);
        sb.draw(replay, 470, 200, 64, 64);
        sb.draw(next, 570, 200, 64, 64);
        sb.draw(levels, 670, 200, 64, 64);
        sb.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        win_bar.dispose();
        replay.dispose();
        next.dispose();
        levels.dispose();
    }
}
