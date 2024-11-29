package com.ok.AngryBirds.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStateData implements Serializable {
    public ArrayList<BirdData> birds;
    public ArrayList<ObstacleData> obstacles;
    public ArrayList<PigData> pigs;
    public BirdData currentBird;
    public boolean isBirdLaunched;
    public float slingshotCentreX, slingshotCentreY;


}

