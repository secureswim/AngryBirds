package com.ok.AngryBirds.utils;

import java.util.ArrayList;
import java.util.List;

public class Trajectory{
    private static final float gravity = 9.8f;

    public static List<float[]> calculate_trajectory(float speed, float angle, float time_step, float scale) {
        List<float[]> points=new ArrayList<>();

        float angle_rad=(float) Math.toRadians(angle);

        float vX=speed * (float) Math.cos(angle_rad);
        float vY=speed * (float) Math.sin(angle_rad);

        float t=0;
        while (true) {
            float x=vX * t;
            float y=vY * t-0.5f * gravity * t * t;

            x *= scale;
            y *= scale;

            if (y<0) break;

            points.add(new float[]{x, y});
            t+=time_step;
        }

        return points;
    }
}
