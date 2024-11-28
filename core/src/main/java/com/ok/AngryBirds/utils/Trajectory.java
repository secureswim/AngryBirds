package com.ok.AngryBirds.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {
    private static final float GRAVITY = 4.905f;
    private static final float PIXELS_TO_METERS = 100f;

    public static List<float[]> calculate_trajectory(float initial_speed, float angle, float time_step, float scale) {
        List<float[]> points = new ArrayList<>();

        // Convert angle to radians
        float radianAngle = (float) Math.toRadians(angle);

        // Initial velocities
        float vX = (float) (initial_speed * Math.cos(radianAngle));
        float vY = (float) (initial_speed * Math.sin(radianAngle));

        float t = 0;
        while (true) {
            // Calculate position using projectile motion equations
            float x = vX * t;
            float y = vY * t - 0.5f * GRAVITY * t * t;

            // Scale to match screen coordinates
            x *= scale;
            y *= scale;

            // Stop when trajectory goes below ground
            if (y < 0) break;

            points.add(new float[]{x, -y});
            t += time_step;
        }

        return points;
    }

    // New method to calculate exact launch velocity
    public static Vector2 calculateLaunchVelocity(float speed, float angle) {
        float radianAngle = (float) Math.toRadians(angle);

        float velocityX = (float) (speed * Math.cos(radianAngle));
        float velocityY = (float) (speed * Math.sin(radianAngle));

        return new Vector2(velocityX, velocityY);
    }
}
