package com.example.basiccalculator;

public class CalculatorClass {

    public static double calculations(double n, double m, String operator){
        double result = 0;
        switch (operator){
            case "/":
                result = n / m;
                break;
            case "*":
                result = n * m;
                break;
            case "+":
                result = n + m;
                break;
            case "-":
                result = n - m;
                break;
            case "%":
                result = (n/100) * m;
                break;
        }
        return result;
    }

}
