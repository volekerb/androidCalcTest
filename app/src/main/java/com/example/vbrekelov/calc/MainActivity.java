package com.example.vbrekelov.calc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

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
                    viewSize.computeWidth(100, this),
                    viewSize.computeHeight(50, this)
            );
            params.setMargins(5, 5, 0, 0);
            TextView textView = new TextView(this);
            textView.setText("0");
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