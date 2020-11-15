package com.example.basiccalculator;

public class CalculatorClass {

    /**
     * This is the calculation functionality for the app. The template for the calculations.
     * @param n - 1st value
     * @param m - 2nd value
     * @param operator - chosen operator to determine which operation to perform
     * @return - result of the calculation in double format
     */
    public static double calculations(double n, double m, String operator){
        double result = 0;
        switch (operator){
            case "÷":
                result = n / m;
                break;
            case "×":
                result = n * m;
                break;
            case "+":
                result = n + m;
                break;
            case "–":
                result = n - m;
                break;
            case "%":
                result = (n/100) * m;
                break;
        }
        return result;
    }

}
