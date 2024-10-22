package com.ok.AngryBirds.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ok.AngryBirds.Main;

public class MenuState extends State{
    private Texture background;
    private Texture playbutton;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        background=new Texture("ab_bg.png");
        playbutton=new Texture("play_ab.png");
    }

    @Override
    protected void hande_input() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        background.dispose();
        playbutton.dispose();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0, Main.width,Main.height);
        sb.draw(playbutton,((float) Main.width /2)-((float) playbutton.getWidth() /2), (float) Main.height /2);
        sb.end();
    }
}
