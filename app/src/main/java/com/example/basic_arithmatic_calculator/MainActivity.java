package com.example.basic_arithmatic_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Variables to store the display and calculation data
    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double result = 0;
    private boolean isOperatorPressed = false;
    private boolean justCalculated = false;
    private String fullExpression = ""; // Keep track of the full calculation being built

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the display TextView
        display = findViewById(R.id.display);

        // Set up all the number buttons
        setupNumberButtons();

        // Set up all the operator buttons
        setupOperatorButtons();

        // Set up special buttons
        setupSpecialButtons();
    }

    // Method to set up number buttons (0-9 and decimal)
    private void setupNumberButtons() {
        // Number buttons
        findViewById(R.id.btn0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("0");
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("1");
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("2");
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("3");
            }
        });

        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("4");
            }
        });

        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("5");
            }
        });

        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("6");
            }
        });

        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("7");
            }
        });

        findViewById(R.id.btn8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("8");
            }
        });

        findViewById(R.id.btn9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick("9");
            }
        });

        // Decimal point button
        findViewById(R.id.btnDecimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDecimalClick();
            }
        });
    }

    // Method to set up operator buttons (+, -, *, /)
    private void setupOperatorButtons() {
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorClick("+");
            }
        });

        findViewById(R.id.btnSubtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorClick("-");
            }
        });

        findViewById(R.id.btnMultiply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorClick("*");
            }
        });

        findViewById(R.id.btnDivide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorClick("/");
            }
        });
    }

    // Method to set up special buttons (Clear, Delete, Equals)
    private void setupSpecialButtons() {
        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });

        // Delete button
        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLastChar();
            }
        });

        // Equals button
        findViewById(R.id.btnEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });
    }

    // Method called when a number button is clicked
    private void onNumberClick(String number) {
        // If we just calculated, start completely fresh
        if (justCalculated) {
            clearAll();
            justCalculated = false;
        }

        // If operator was just pressed, start new number
        if (isOperatorPressed) {
            currentInput = "";
            isOperatorPressed = false;
        }

        // Add the number to current input
        currentInput = currentInput + number;

        // Update the full expression
        updateFullExpression();

        // Show the full expression on display
        display.setText(fullExpression);
    }

    // Method called when decimal button is clicked
    private void onDecimalClick() {
        // If we just calculated, start completely fresh
        if (justCalculated) {
            clearAll();
            justCalculated = false;
        }

        // If operator was just pressed, start new number
        if (isOperatorPressed) {
            currentInput = "";
            isOperatorPressed = false;
        }

        // Only add decimal if there isn't one already
        if (!currentInput.contains(".")) {
            // If current input is empty, start with "0."
            if (currentInput.isEmpty()) {
                currentInput = "0.";
            } else {
                currentInput = currentInput + ".";
            }

            // Update the full expression
            updateFullExpression();

            // Show the full expression on display
            display.setText(fullExpression);
        }
    }

    // Method called when an operator button is clicked
    private void onOperatorClick(String op) {
        // If we have a current number, we need to process it
        if (!currentInput.isEmpty()) {
            double currentNumber = Double.parseDouble(currentInput);

            // If this is the first number, just store it
            if (operator.isEmpty()) {
                result = currentNumber;
            } else {
                // We have a previous operation to complete
                result = performCalculation(result, currentNumber, operator);

                // Check for division by zero
                if (Double.isInfinite(result) || Double.isNaN(result)) {
                    display.setText("Error: Cannot divide by zero");
                    resetCalculator();
                    return;
                }
            }
        }

        // Store the new operator
        operator = op;
        isOperatorPressed = true;
        justCalculated = false;

        // Update the full expression to show result + operator
        fullExpression = formatNumber(result) + " " + op + " ";
        display.setText(fullExpression);
    }

    // Method to calculate the result
    private void calculateResult() {
        // Make sure we have everything needed for calculation
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            try {
                // Get the current number
                double currentNumber = Double.parseDouble(currentInput);

                // Perform the calculation
                result = performCalculation(result, currentNumber, operator);

                // Check for division by zero
                if (Double.isInfinite(result) || Double.isNaN(result)) {
                    display.setText("Error: Cannot divide by zero");
                    resetCalculator();
                    return;
                }

                // Show the result
                fullExpression = formatNumber(result);
                display.setText(fullExpression);

                // Reset for next calculation but keep the result
                currentInput = formatNumber(result);
                operator = "";
                justCalculated = true;

            } catch (Exception e) {
                display.setText("Error");
                resetCalculator();
            }
        }
    }

    // Method to clear everything
    private void clearAll() {
        currentInput = "";
        operator = "";
        result = 0;
        isOperatorPressed = false;
        justCalculated = false;
        fullExpression = "0";
        display.setText(fullExpression);
    }

    // Method to delete the last character
    private void deleteLastChar() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);

            // Update the full expression
            updateFullExpression();

            // Show updated expression
            if (fullExpression.isEmpty()) {
                fullExpression = "0";
            }
            display.setText(fullExpression);
        }
    }

    // Method to reset calculator after error
    private void resetCalculator() {
        currentInput = "";
        operator = "";
        result = 0;
        isOperatorPressed = false;
        justCalculated = false;
        fullExpression = "0";
    }

    // Helper method to format numbers nicely (show whole numbers without decimal)
    private String formatNumber(double number) {
        if (number == (int) number) {
            return String.valueOf((int) number);
        } else {
            return String.valueOf(number);
        }
    }

    // Helper method to update the full expression display
    private void updateFullExpression() {
        if (operator.isEmpty()) {
            // No operator yet, just show current number
            fullExpression = currentInput.isEmpty() ? "0" : currentInput;
        } else {
            // Show: result operator currentInput
            if (currentInput.isEmpty()) {
                fullExpression = formatNumber(result) + " " + operator + " ";
            } else {
                fullExpression = formatNumber(result) + " " + operator + " " + currentInput;
            }
        }
    }

    // Helper method to perform calculations
    private double performCalculation(double first, double second, String op) {
        switch (op) {
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "*":
                return first * second;
            case "/":
                if (second == 0) {
                    return Double.POSITIVE_INFINITY; // Will be caught as error
                }
                return first / second;
            default:
                return second;
        }
    }
}