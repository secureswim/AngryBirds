package com.ok.AngryBirds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ok.AngryBirds.Main;
import com.ok.AngryBirds.utils.GameStateData;

import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class LevelState extends State {
    private final Texture levels;
    private final Texture play;
    private final Texture back;
    private final Texture load_game;


    protected LevelState(GameStateManager gsm) {
        super(gsm);
        levels=new Texture("levels_ab.png");
        play=new Texture("main_play_button.png");
        back=new Texture("back_button.png");
        load_game=new Texture("load_game.png");

    }

    @Override
    protected void handle_input() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float backX = 50;
            float backY = 30;
            float backWidthX = 90;
            float backHeightY = 90;

            if (touchX >= backX && touchX <= backX+ backWidthX &&
                touchY >= backY && touchY <=backY+ backHeightY) {
                gsm.push(new MenuState(gsm));
                dispose();
            }
        }
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float playX = 505;
            float playY = 30;
            float playWidthX = 200;
            float playWidthY = 100;

            if (touchX >= playX && touchX <= playX + playWidthX &&
                touchY >= playY && touchY <= playY + playWidthY) {
                gsm.push(new Level_1(gsm));
                dispose();
            }
        }
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float playX = 90;
            float playY = 290;
            float playWidthX = 60;
            float playWidthY = 60;

            if (touchX >= playX && touchX <= playX + playWidthX &&
                touchY >= playY && touchY <= playY + playWidthY) {
                gsm.push(new Level_1(gsm));
                dispose();
            }
        }
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Main.height - Gdx.input.getY();

            // Add load game button check
            float loadX = 900;
            float loadY = 650;
            float loadWidthX = 250;
            float loadHeightY = 67;

            if (touchX >= loadX && touchX <= loadX + loadWidthX &&
                touchY >= loadY && touchY <= loadY + loadHeightY) {
                // Load the saved game state
                loadSavedGame();
                return;
            }
        }

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float playX = 295;
            float playY = 292;
            float playWidthX = 60;
            float playWidthY = 60;

            if (touchX >= playX && touchX <= playX + playWidthX &&
                touchY >= playY && touchY <= playY + playWidthY) {
                gsm.push(new Level_2(gsm));
                dispose();
            }
        }

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Main.height - touchY;

            float playX = 468;
            float playY = 368;
            float playWidthX = 60;
            float playWidthY = 60;

            if (touchX >= playX && touchX <= playX + playWidthX &&
                touchY >= playY && touchY <= playY + playWidthY) {
                gsm.push(new Level_3(gsm));
                dispose();
            }
        }


    }

    @Override
    public void update(float dt) {
        handle_input();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(levels,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        sb.draw(play,505,30,200,100);
        sb.draw(back,50,30,90,90);
        sb.draw(load_game,900,650,250,67);


        sb.end();
    }
    private void loadSavedGame() {
        // Check for existing save file
        java.io.File saveFile = new java.io.File("game_save.ser");
        if (saveFile.exists()) {
            try {
                // Read the game state to determine the level
                FileInputStream fis = new FileInputStream(saveFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                GameStateData gameState = (GameStateData) ois.readObject();
                ois.close();
                fis.close();

                // Determine which level to load based on the saved level
                State loadedLevel = null;
                switch (gameState.currentLevel) {
                    case "Level_1":
                        Level_1 level1 = new Level_1(gsm);
                        level1.loadGameState("game_save.ser");
                        loadedLevel = level1;
                        break;
                    case "Level_2":
                        Level_2 level2 = new Level_2(gsm);
                        level2.loadGameState("game_save.ser");
                        loadedLevel = level2;
                        break;
                    case "Level_3":
                        Level_3 level3 = new Level_3(gsm);
                        level3.loadGameState("game_save.ser");
                        loadedLevel = level3;
                        break;
                    default:
                        System.out.println("Unknown level: " + gameState.currentLevel);
                        return;
                }

                // Push the loaded level to the game state manager
                if (loadedLevel != null) {
                    gsm.push(loadedLevel);
                    dispose();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading saved game: " + e.getMessage());
            }
        } else {
            // Optional: Show a message that no saved game exists
            System.out.println("No saved game found.");
        }
    }

    @Override
    public void dispose() {
        levels.dispose();
        play.dispose();
        back.dispose();
        load_game.dispose();
    }
}
