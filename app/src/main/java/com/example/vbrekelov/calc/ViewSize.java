package com.example.vbrekelov.calc;

import android.content.Context;

// estimate the width and height of an element based on screen size or given value
public class ViewSize {

    final static float LOW_LEVEL = 0.75f;
    final static float MEDIUM_LEVEL = 1.0f; // default size
    final static float HIGH_LEVEL = 1.5f;
    final static float X_HIGH_LEVEL = 2.0f;
    final static float XX_HIGH_LEVEL = 3.0f;
    final static float XXX_HIGH_LEVEL = 4.0f;

    public float computeWidth(float widthInMediumDensity, Context context) {
        return compute(widthInMediumDensity, context);
    }

    public float computeHeight(float heightInMediumDensity, Context context) {
        return compute(heightInMediumDensity, context);
    }

    private float compute(float value, Context context) {
        float level = context.getApplicationContext().getResources().getDisplayMetrics().density;
        boolean shouldMultiply = false;
        if (level == LOW_LEVEL || level == HIGH_LEVEL || level == X_HIGH_LEVEL ||
                level == XX_HIGH_LEVEL || level == XXX_HIGH_LEVEL) {
            shouldMultiply = true;
        }
        return shouldMultiply ? value * level : value;
    }
}