package com.example.vbrekelov.calc;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalculatorContentView extends LinearLayout {

    protected ButtonsClickListener clickListener;
    protected TextView display;
    protected View viewWhereClickHappen;
    protected ViewSize viewSize = new ViewSize();
    protected CalculatorPopup calculatorPopup;

    public CalculatorContentView(CalculatorPopup calculatorPopup) {
        super(calculatorPopup.getContext());

        this.setOrientation(LinearLayout.VERTICAL);
        this.calculatorPopup = calculatorPopup;
        clickListener = new ButtonsClickListener(this);
        this.addComponent();
    }

    private void addComponent() {
        // initialize the display
        this.display = this.display();
        this.addView(this.border());
        // add the display to view
        this.addView(this.display);
        this.addView(this.border());
        // construct the calculator buttons
        //TODO: move this to calcButtonsConstructor
        for (int x = 0; x < CalculatorButton.BUTTONS.length; x++) {
            LinearLayout linearLayout = new LinearLayout(this.getContext());
            LinearLayout.LayoutParams linearParams =
                    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                            viewSize.computeHeight(50, this.getContext()));
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

    // add border to the pop up
    private View border() {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);

        View border = new View(this.getContext());
        border.setLayoutParams(params);
        border.setBackgroundColor(Color.BLACK);
        return border;
    }
//TODO: remove magic numbers
    /**
     * @return display = the white bar on the calculator where you show the numbers, etc.
     */
    private TextView display() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                viewSize.computeHeight(50, this.getContext()));
        params.setMargins(1, 0, 1, 0);
        TextView display = new TextView(this.getContext());

        display.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        display.setText("0");
        display.setTypeface(null, Typeface.BOLD);
        display.setTextSize(18);
        display.setLayoutParams(params);
        display.setPadding(2, 5, 2, 5);
        display.setBackgroundColor(Color.parseColor("#c7c9c5"));
        return display;
    }

    public void setViewWhereClickHappen(View viewWhereClickHappen) {
        this.viewWhereClickHappen = viewWhereClickHappen;
        // SET displayData VALUE
        clickListener.displayData = ((TextView) viewWhereClickHappen).getText().toString();
        this.display.setText(clickListener.displayData);
    }
}