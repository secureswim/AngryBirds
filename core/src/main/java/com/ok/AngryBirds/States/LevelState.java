package com.ok.AngryBirds.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelState extends State {
    private Texture level_map;

    protected LevelState(GameStateManager gsm) {
        super(gsm);
        level_map=new Texture("level_map.png");
    }

    @Override
    protected void hande_input() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }
}
