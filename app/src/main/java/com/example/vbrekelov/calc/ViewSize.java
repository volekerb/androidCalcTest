package com.example.vbrekelov.calc;

import android.content.Context;

// estimate the width and height of an element based on screen size or given value
public class ViewSize {

    private final static float LOW_LEVEL = 0.75f;
    private final static float MEDIUM_LEVEL = 1.0f; // default size
    private final static float HIGH_LEVEL = 1.5f;
    private final static float X_HIGH_LEVEL = 2.0f;
    private final static float XX_HIGH_LEVEL = 3.0f;
    private final static float XXX_HIGH_LEVEL = 4.0f;

    public int computeWidth(float widthInMediumDensity, Context context) {
        return compute(widthInMediumDensity, context);
    }

    public int computeHeight(float heightInMediumDensity, Context context) {
        return compute(heightInMediumDensity, context);
    }

    private int compute(float value, Context context) {
        float level = context.getApplicationContext().getResources().getDisplayMetrics().density;
        boolean shouldMultiply = false;
        if (level == LOW_LEVEL || level == HIGH_LEVEL || level == X_HIGH_LEVEL ||
                level == XX_HIGH_LEVEL || level == XXX_HIGH_LEVEL) {
            shouldMultiply = true;
        }
        return (int) (shouldMultiply ? value * level : value);
    }
}