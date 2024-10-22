package com.ok.AngryBirds.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State {
    private Texture bird;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird=new Texture("angrybirds_red.png");
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
        sb.draw(bird,50,50);
        sb.end();

    }

    @Override
    public void dispose() {
        bird.dispose();
    }
}
