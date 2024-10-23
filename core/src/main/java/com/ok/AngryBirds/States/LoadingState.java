//package com.ok.AngryBirds.States;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//
//
//public class LoadingState extends State{
//    private final Texture background;
//    private final Texture logo;
//    private Texture progress_bar;
//    private final AssetManager assetManager;
//    private float progress;
//    private float elapsed_time;
//
//
//    protected LoadingState(GameStateManager gsm) {
//        super(gsm);
//        assetManager = new AssetManager();
//        background=new Texture("loading_backgrounf.png");
//        logo= new Texture("sgdf.png");
//        assetManager.load("assets/other_asset.png", Texture.class);
//
//
//    }
//
//    @Override
//    public void show() {
//        progress = 0;
//        elapsed_time = 0;
//    }
//
//
//
//    @Override
//    protected void hande_input() {
//    }
//
//    @Override
//    public void update(float dt) {
//        hande_input();
//    }
//
//    @Override
//    public void render(SpriteBatch sb) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        // Update elapsed time
//        elapsed_time = delta;
//
//        // Calculate progress based on elapsed time (2 seconds duration)
//        progress = Math.min(elapsedTime / 2.0f, 1.0f);
//
//        // Draw the loading bar
//        batch.begin();
//
//        // Draw background of the loading bar
//        batch.draw(backgroundTexture, 100, 100, 600, 50);
//
//        // Draw the progress bar fill
//        batch.draw(progressBarTexture, 100, 100, 600 * progress, 50);
//
//        batch.end();
//
//        // Once progress reaches 1 (100%), check if asset loading is done
//        if (progress >= 1 && assetManager.update()) {
//            // All assets are loaded, proceed to the next screen or game logic
//            // For example: game.setScreen(new MainGameScreen());
//        }
//    }
//
//    @Override
//    public void dispose() {
//        background.dispose();
//        logo.dispose();
//    }
//}
