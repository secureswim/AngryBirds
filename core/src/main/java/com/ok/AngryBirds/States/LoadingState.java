package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.ok.AngryBirds.Main;

public class LoadingState extends State{

    private final Texture loading_bg;
    private final Texture loading_bar;
    private final Texture loading_bar_background;
    private final float loadingTime;
    private float elapsedTime;

    private Music backgroundMusic;


    public LoadingState(GameStateManager gsm) {
        super(gsm);
        loading_bg=new Texture("loading_background.png");
        loading_bar=new Texture("loading_bar.png");
        loading_bar_background=new Texture("loading_bar_background.png");
        loadingTime=3.5f;
        elapsedTime=0f;

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("theme_song.mp3"));
        backgroundMusic.setLooping(true);  // Loop the music
        backgroundMusic.setVolume(10.5f);   // Set the volume (optional)
        backgroundMusic.play();


    }

    @Override
    protected void handle_input() {
    }

    @Override
    public void update(float dt) {
        elapsedTime+=dt;
        if (elapsedTime >=loadingTime) {
            gsm.pop();
            gsm.push(new MenuState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(loading_bg,0,0, Main.width,Main.height);
        sb.draw(loading_bar_background,430,30,300,30);
        float progress = Math.min(elapsedTime / loadingTime, 1f);
        sb.draw(loading_bar,430,40,300*progress,20);

        sb.end();
    }

    @Override
    public void dispose() {
        backgroundMusic.stop();
        backgroundMusic.dispose();

        loading_bg.dispose();
        loading_bar.dispose();
        loading_bar_background.dispose();
    }
}
