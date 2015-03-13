package com.example.vbrekelov.calc;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class CalculatorPopup extends Dialog {

    private CalculatorContentView calculator;

    public CalculatorPopup(Context context) {
        super(context);
        this.calculator = new CalculatorContentView(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(calculator);
    }

    public CalculatorContentView getCalculator() {
        return calculator;
    }
}
