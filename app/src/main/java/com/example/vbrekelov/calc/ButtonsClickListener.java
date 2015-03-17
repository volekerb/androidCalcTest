package com.example.vbrekelov.calc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Double.parseDouble;

public class ButtonsClickListener implements OnClickListener {

    public static final String OPERATION_PATTERN = "[^0-9\\.]";
    String displayData = "0";
    CalculatorContentView calculatorView;

    public ButtonsClickListener(CalculatorContentView calculatorView) {
        this.calculatorView = calculatorView;
    }

    private void setDisplayData() {
        calculatorView.display.setText(displayData);
    }

    @Override
    public void onClick(View view) {
        String tag = view.getTag().toString();
        String tempData = this.displayData;
        if (doesTextContainMainOperation(tag)) {
            displayData = operationClick(view, tag, tempData);
            setDisplayData();
        } else {
            if (tag.equalsIgnoreCase(CalculatorButton.EQUAL)) {
                equalButtonClick(tempData);
            } else if (tag.equalsIgnoreCase(CalculatorButton.DOT)) {
                tempData += ((TextView) view).getText().toString();
                displayData = tempData;
                setDisplayData();
            } else {
                if (tag.equalsIgnoreCase(CalculatorButton.CLEAR)) {
                    tempData = this.removeLastChar(tempData);
                    displayData = tempData.length() <= 0 ? "0" : tempData;
                    setDisplayData();
                } else {
                    if (tag.equalsIgnoreCase(CalculatorButton.OK)) {
                        okButtonClick(tempData);
                    } else {
                        displayData = tempData.equals("0") ? tag : tempData + tag;
                        setDisplayData();
                    }
                }
            }
        }
    }

    private void equalButtonClick(String tempData) {
        try {
            String data = tempData.substring(1, tempData.length());
            int indexOfFirstOperation = indexOfFirstOperation(OPERATION_PATTERN, data) + 1;
            try {
                String firstOperation = tempData.charAt(indexOfFirstOperation) + "";
                double result = compute(firstOperation, tempData);
                displayData = removeUnwantedValueInDecimal(Double.toString(result));
            } catch (IndexOutOfBoundsException e) {
                setDisplayDataAndClose();
            }
            calculatorView.display.setText(displayData + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void okButtonClick(String tempData) {
        try {
            String data = tempData.length() > 2 ?
                    tempData.substring(1, tempData.length()) : tempData;
            int indexOfFirstOperation = indexOfFirstOperation(OPERATION_PATTERN, data) + 1;

            if (isFirstOperationTextBetweenNumber(indexOfFirstOperation, data)) {
                String firstOperation = tempData.charAt(indexOfFirstOperation) + "";
                double result = compute(firstOperation, tempData);
                displayData = removeUnwantedValueInDecimal(result + "");
                setDisplayDataAndClose();
            } else {
                tempData = (isLastTextOperation(tempData) || isLastTextDot(tempData))
                        && tempData.length() > 2 ? removeLastChar(tempData) : tempData;
                if (isValidNumber(tempData)) {
                    tempData = parseDouble(tempData) + "";
                    tempData = removeUnwantedValueInDecimal(tempData);
                    displayData = tempData;
                    setDisplayDataAndClose();
                } else {
                    Toast.makeText(calculatorView.getContext(), "Not a valid number " + tempData,
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", "error");
        }
    }

    private void setDisplayDataAndClose() {
        ((TextView) calculatorView.viewWhereClickHappen).setText(displayData + "");
        calculatorView.calculatorPopup.dismiss();
    }

    private String operationClick(View view, String operation, String tempData) {
        try {
            if (!allowToAppendOperationText(true, tempData, operation)) {
                Toast.makeText(view.getContext(), "Invalid", Toast.LENGTH_SHORT).show();
                return "0";
            }
            if (tempData.equalsIgnoreCase("0")
                    && operation.equalsIgnoreCase(CalculatorButton.SUBTRACT)) {
                return operation;
            }
            if (isFirstTextNegativeSign(tempData) && tempData.length() <= 1) {
                return tempData;
            }
            if (!doesDataContainOperationText(tempData)) {
                tempData += operation;
                return tempData;
            }
            tempData += operation;

            String data = tempData.substring(1, tempData.length());
            if (doComputeTotal("([^0-9\\.])", data)) {
                int indexOfFirstOperation = indexOfFirstOperation(OPERATION_PATTERN, data) + 1;
                String firstOperation = tempData.charAt(indexOfFirstOperation) + "";
                tempData = removeLastChar(tempData);
                double result = compute(firstOperation, tempData);
                tempData = removeUnwantedValueInDecimal(result + "") + operation;
            } else {
                tempData = removePreviousOperationText(tempData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tempData = "";
            calculatorView.display.setText("Error");
        }
        return tempData;
    }

    private boolean isFirstOperationTextBetweenNumber(int indexOfFirstOperation, String tempData) {
        int tempDataLength = tempData.length();
        return indexOfFirstOperation > 1 && indexOfFirstOperation < (tempDataLength);
    }

    private boolean isValidNumber(String data) {
        try {
            parseDouble(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String removeUnwantedValueInDecimal(String result) {
        String resultTmp = result.substring(result.lastIndexOf("."), result.length());
        if (this.containNumberNotZero("[1-9]", resultTmp)) {
            return result;
        } else {
            return result.substring(0, result.lastIndexOf("."));
        }
    }

    private double compute(String firstOperation, String tempData) throws Exception {
        double firstNumber = parseDouble(tempData.substring(0,
                tempData.indexOf(firstOperation)));
        double secondNumber = parseDouble(tempData.substring(
                tempData.indexOf(firstOperation) + 1, tempData.length()));
        double result = 0;
        if (firstOperation.equalsIgnoreCase(CalculatorButton.ADD)) {
            result = firstNumber + secondNumber;
        } else if (firstOperation.equalsIgnoreCase(CalculatorButton.SUBTRACT)) {
            result = firstNumber - secondNumber;
        } else if (firstOperation.equalsIgnoreCase(CalculatorButton.MUTIPLY)) {
            result = firstNumber * secondNumber;
        } else if (firstOperation.equalsIgnoreCase(CalculatorButton.DIVIDE)) {
            result = firstNumber / secondNumber;
        }
        return result;
    }

    private boolean doComputeTotal(String theRegex, String stringToCheck) {
        boolean computeTotal = false;
        Pattern pattern = Pattern.compile(theRegex);
        Matcher matcher = pattern.matcher(stringToCheck);

        int operationCount = 0;
        int firstOperationEndIndex = 0;
        int secondOperationStartIndex = 0;

        while (matcher.find()) {
            if (operationCount == 0) {
                firstOperationEndIndex = matcher.end();
            } else if (operationCount == 1) {
                secondOperationStartIndex = matcher.start();
            }
            operationCount++;
        }
        if (operationCount > 1 && firstOperationEndIndex < secondOperationStartIndex) {
            computeTotal = true;
        } else if (operationCount > 1 && firstOperationEndIndex >= secondOperationStartIndex) {
            computeTotal = false;
        }
        return computeTotal;
    }

    private int indexOfFirstOperation(String theRegex, String stringToCheck) {
        Pattern pattern = Pattern.compile(theRegex);
        Matcher matcher = pattern.matcher(stringToCheck);
        while (matcher.find()) {
            return matcher.start();
        }
        return 0;
    }

    private boolean containNumberNotZero(String theRegex, String stringToCheck) {
        Pattern pattern = Pattern.compile(theRegex);
        Matcher matcher = pattern.matcher(stringToCheck);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    private boolean allowToAppendOperationText(boolean isOperationButtonHasClicked, String tempData,
                                               String operation) {
        return !((tempData.length() <= 0) && isOperationButtonHasClicked
                && !operation.equalsIgnoreCase(CalculatorButton.SUBTRACT));
    }

    private boolean isFirstTextNegativeSign(String data) {
        return data.length() > 0
                && (data.charAt(0) + "").equalsIgnoreCase(CalculatorButton.SUBTRACT);
    }

    private boolean isLastTextOperation(String data) {
        if (data.length() > 0) {
            String lastText = getLastText(data);
            if (lastText.equalsIgnoreCase(CalculatorButton.DOT)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLastTextDot(String data) {
        if (data.length() > 0) {
            String lastText = getLastText(data);
            return doesTextContainMainOperation(lastText);
        }
        return false;
    }

    private boolean doesTextContainMainOperation(String text) {
        return text.equalsIgnoreCase(CalculatorButton.ADD)
                || text.equalsIgnoreCase(CalculatorButton.SUBTRACT)
                || text.equalsIgnoreCase(CalculatorButton.MUTIPLY)
                || text.equalsIgnoreCase(CalculatorButton.DIVIDE);
    }

    private String getLastText(String data) {
        return data.substring(data.length() - 1, data.length());
    }

    private String removePreviousOperationText(String data) {
        return data.substring(0, data.length() - 2) + getLastText(data);
    }

    private String removeLastChar(String data) {
        return data.length() > 0 ? data.substring(0, data.length() - 1) : data;
    }

    private boolean doesDataContainOperationText(String data) {
        if (data.length() > 1) {
            String testData = data.substring(1, data.length());
            return doesTextContainMainOperation(testData);
        }
        return false;
    }
}