package com.ok.AngryBirds.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {
    private static final float GRAVITY = 9.81f;

    public static List<float[]> calculate_trajectory(float speed, float angle, float time_step, float scale) {
        List<float[]> points = new ArrayList<>();

        float angle_rad = (float) Math.toRadians(angle);
        Vector2 initialVelocity = new Vector2(
            (float) (speed * Math.cos(angle_rad)),
            (float) (speed * Math.sin(angle_rad))
        );
        float t = 0;
        while (true) {
            float x = initialVelocity.x * t;
            float y = initialVelocity.y * t - 0.5f * GRAVITY * t * t;
            x *= scale;
            y *= scale;

            if (y < 0) break;

            points.add(new float[]{x, y});
            t += time_step;
        }

        return points;
    }
}
