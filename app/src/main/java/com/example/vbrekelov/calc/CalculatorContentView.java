package com.example.vbrekelov.calc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalculatorContentView extends LinearLayout {

    public static final String BACKGROUND_COLOR = "#c7c9c5";
    public static final int TEXT_SIZE = 18;
    public static final String DEFAULT_TEXT = "0";
    public static final int HEIGHT_IN_MEDIUM_DENSITY = 50;
    protected ButtonsClickListener clickListener;
    protected TextView display;
    protected View viewWhereClickHappen;
    protected ViewSize viewSize = new ViewSize();
    protected CalculatorPopup calculatorPopup;

    public CalculatorContentView(Context context) {
        super(context);
    }

    public CalculatorContentView(Context context, CalculatorPopup calculatorPopup) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.calculatorPopup = calculatorPopup;
        this.clickListener = new ButtonsClickListener(this);
        initialize();
    }

    protected void initialize() {
        // initialize the display
        display = buildDisplay();
        addView(buildBorder());
        addView(display);
        addView(buildBorder());
        // initialize the calculator buttons
        //TODO: move this to calcButtonsConstructor
        for (int x = 0; x < CalculatorButton.BUTTONS.length; x++) {
            LinearLayout linearLayout = new LinearLayout(this.getContext());
            LinearLayout.LayoutParams linearParams =
                    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                            viewSize.computeHeight(HEIGHT_IN_MEDIUM_DENSITY, this.getContext()));
            linearParams.setMargins(0, 1, 0, 0);
            LinearLayout.LayoutParams buttonParams =
                    new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
            buttonParams.weight = 1;
            buttonParams.setMargins(1, 0, 1, 0);
            int innerBtnCount = CalculatorButton.BUTTONS[x].length;

            for (int y = 0; y < innerBtnCount; y++) {
                Button button = new Button(this.getContext());
                button.setOnClickListener(this.clickListener);
                button.setText(CalculatorButton.BUTTONS[x][y] + "");
                button.setTag(CalculatorButton.BUTTONS[x][y] + "");
                button.setBackgroundColor(Color.LTGRAY);
                // simple animation effect when clicking a button
                button.setBackground(new CustomStateListDrawable(button));
                linearLayout.addView(button, buttonParams);
            }
            this.addView(linearLayout, linearParams);
        }
    }

    private View buildBorder() {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);

        View border = new View(this.getContext());
        border.setLayoutParams(params);
        border.setBackgroundColor(Color.BLACK);
        return border;
    }

    //TODO: remove magic numbers
    private TextView buildDisplay() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                viewSize.computeHeight(HEIGHT_IN_MEDIUM_DENSITY, this.getContext()));
        params.setMargins(1, 0, 1, 0);
        TextView display = new TextView(this.getContext());

        display.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        display.setText(DEFAULT_TEXT);
        display.setTypeface(null, Typeface.BOLD);
        display.setTextSize(TEXT_SIZE);
        display.setLayoutParams(params);
        display.setPadding(2, 5, 2, 5);
        display.setBackgroundColor(Color.parseColor(BACKGROUND_COLOR));
        return display;
    }

    public void setViewWhereClickHappen(View viewWhereClickHappen) {
        this.viewWhereClickHappen = viewWhereClickHappen;
        clickListener.displayData = ((TextView) viewWhereClickHappen).getText().toString();
        display.setText(clickListener.displayData);
    }
}