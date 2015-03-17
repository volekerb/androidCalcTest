package com.example.vbrekelov.calc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    public static final String DEFAULT_TEXT = "0";
    public static final int WIDTH_IN_MEDIUM_DENSITY = 100;
    public static final int HEIGHT_IN_MEDIUM_DENSITY = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());
    }

    private LinearLayout contentView() {
        ViewSize viewSize = new ViewSize();
        // container of text view. TODO: change to container with button 'open calc'
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    viewSize.computeWidth(WIDTH_IN_MEDIUM_DENSITY, this),
                    viewSize.computeHeight(HEIGHT_IN_MEDIUM_DENSITY, this)
            );
            params.setMargins(5, 5, 0, 0);
            TextView textView = new TextView(this);
            textView.setText(DEFAULT_TEXT);
            textView.setPadding(2, 0, 2, 0);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
            textView.setLayoutParams(params);
            textView.setOnClickListener(this);
            contentView.addView(textView);
        return contentView;
    }

    @Override
    public void onClick(View view) {
        CalculatorPopup calculatorPopup = new CalculatorPopup(this);
        calculatorPopup.getCalculator().setViewWhereClickHappen(view);
        calculatorPopup.show();
    }
}