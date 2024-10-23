package com.ok.AngryBirds.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level1 extends State{
    private final Texture level1;
    private final Texture slingshot;
    private final Texture bird;


    protected Level1(GameStateManager gsm) {
        super(gsm);
        level1=new Texture("level1_background.jpg");
        slingshot=new Texture("slingshot_ab.png");
        bird= new Texture("red_ab.png");


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
        sb.draw(level1,0,0);
        sb.draw(slingshot,50,190,220,220);
        sb.draw(bird,100,350,120,80);
        sb.end();
    }

    @Override
    public void dispose() {
        level1.dispose();
        slingshot.dispose();
    }
}
