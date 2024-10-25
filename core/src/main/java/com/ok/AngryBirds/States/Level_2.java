package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ok.AngryBirds.Main;

public class Level_2 extends State {
    private final Texture pause;

    public Level_2(GameStateManager gsm) {
        super(gsm);
        pause = new Texture("pause_button.png");

    }

    @Override
    protected void hande_input() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 30 && x <= 115 && y >= 650 && y <= 735) {
                gsm.push(new PauseState(gsm,this));
                return;
            }
            if (x >= 1110 && x <= 1180 && y >= 665 && y <= 735) {
                gsm.push(new WinState(gsm,this));
                dispose();
            } else if (x >= 1110 && x <= 1180 && y >= 595 && y <= 665) {
                gsm.push(new LoseState(gsm,this));
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
        sb.draw(pause, 30, 650, 85, 85);
        sb.end();
    }




    @Override
    public void dispose() {
        pause.dispose();
    }

}
