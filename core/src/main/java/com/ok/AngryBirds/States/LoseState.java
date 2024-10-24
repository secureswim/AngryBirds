package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoseState extends State{
    private final Texture lose_screen;

    public LoseState(GameStateManager gsm){
        super(gsm);
        lose_screen=new Texture("lose_screen.png");
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
        sb.draw(lose_screen,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
