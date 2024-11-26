package com.ok.AngryBirds.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ok.AngryBirds.Objects.RedBird;

public class PlayState extends State {
    private Texture bird;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        RedBird bird=new RedBird("angrybirds_red.png");
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
        sb.draw(bird,50,50);
        sb.end();

    }

    @Override
    public void dispose() {
    }
}
