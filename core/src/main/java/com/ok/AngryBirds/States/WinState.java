package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WinState extends State{
    private final Texture win_screen;

    public WinState(GameStateManager gsm, State currentState){
        super(gsm);
        win_screen=new Texture("win_screen.png");
    }


    @Override
    protected void hande_input() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(win_screen,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
