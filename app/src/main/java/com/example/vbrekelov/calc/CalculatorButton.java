package com.example.vbrekelov.calc;

public class CalculatorButton {

    final static String ADD = "+";
    final static String SUBTRACT = "-";
    final static String MUTIPLY = "X";
    final static String DIVIDE = "÷";
    final static String DOT = ".";
    final static String EQUAL = "=";
    final static String CLEAR = "Clr";
    final static String OK = "OK";

    public static String BUTTONS[][] = {
            {CLEAR, OK},
            {"7", "8", "9", MUTIPLY},
            {"4", "5", "6", DIVIDE},
            {"1", "2", "3", ADD},
            {"0", DOT, EQUAL, SUBTRACT}
    };
}
