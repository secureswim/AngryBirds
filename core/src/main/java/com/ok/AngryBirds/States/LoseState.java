package com.ok.AngryBirds.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;

public class LoseState extends State{

    private final ShapeRenderer shapeRenderer;
    private final State currentState;
    private final Texture lose_bar;
    private final Texture replay;
    private final Texture levels;
    private final Texture quit;

    protected LoseState(GameStateManager gsm, State currentState) {
        super(gsm);
        shapeRenderer=new ShapeRenderer();
        this.currentState = currentState;
        lose_bar = new Texture("lose_screen.png");
        replay = new Texture("replay_button.png");
        levels = new Texture("levels_button.png");
        quit=new Texture("quit_button.png");

    }

    @Override
    protected void handle_input() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 470 && x <= 534 && y >= 200 && y <= 264) {
                gsm.pop();
                gsm.pop();
                gsm.push(new Level_1(gsm));
            }
            if (x >= 570 && x <= 634 && y >= 200 && y <= 264) {
                gsm.pop();
                gsm.push(new MenuState(gsm));
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
        sb.draw(lose_bar, 350, 150,500,500);
        sb.draw(replay, 470, 200, 64, 64);
        sb.draw(quit, 570, 200, 64, 64);
        sb.draw(levels, 670, 200, 64, 64);
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
