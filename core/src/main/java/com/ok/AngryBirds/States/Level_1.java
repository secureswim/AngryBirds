package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.w3c.dom.Text;

public class Level_1 extends State{
    private final Texture level1;
    private final Texture slingshot;
    private final Texture bird;
    private final Texture wood1;
    private final Texture wood2;
    private final Texture wood3;
    private final Texture pig1;
    private final Texture ice1;
    private final Texture block;
    private final Texture triangle;
    private final Texture pause;

    public Level_1(GameStateManager gsm) {
        super(gsm);
        level1=new Texture("level1_background.jpg");
        slingshot=new Texture("slingshot_ab.png");
        bird= new Texture("red_ab.png");
        wood1=new Texture("vertical_wood.png");
        wood2=new Texture("vertical_wood.png");
        wood3=new Texture("horizontal_wood.png");
        pig1=new Texture("pig1.png");
        ice1=new Texture("v_ice_short.png");
        block=new Texture("ice_block.png");
        triangle=new Texture("triangle_wood.png");
        pause=new Texture("pause_button.png");
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
        sb.draw(level1,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(slingshot,50,97,190,190);
        sb.draw(bird,125,240,50,50);
        sb.draw(wood1,860,98,16,150);
        sb.draw(wood2,992,98,16,150);
        sb.draw(wood3,855,240,155,16);
        sb.draw(pig1,905,320,60,60);
        sb.draw(ice1,900,100,16,100);
        sb.draw(ice1,952,100,16,100);
        sb.draw(wood3,898,195,69,16);
        sb.draw(block,905,265,70,70);
        sb.draw(triangle,803,100,60,60);
        sb.draw(triangle,1000,100,60,60);
        sb.draw(pause,30,650,90,90);


        //sb.draw(ice1,990,285,20,150);
        sb.end();
    }

    @Override
    public void dispose() {
        level1.dispose();
        slingshot.dispose();
    }
}
