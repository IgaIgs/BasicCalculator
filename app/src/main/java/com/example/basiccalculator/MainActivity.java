package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.basiccalculator.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    // doubles needed for the calculations and current mock getResult method
    double firstNo, secondNo;
    // string to contain the current operator
    String op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }

    public void onClick(View v) {

        // array with all the operators comes in handy later when checking the screen contents
        String[] ops = {"+", "-", "/", "%", "*"};

        TextView Calc = binding.CalculationView;
        TextView Num = binding.NumbersView;

        // get the length of the current text being displayed on both screens
        int l1 = binding.CalculationView.getText().length();
        int l2 = binding.NumbersView.getText().length();

        //get the text of the pressed button
        Button button  = findViewById(v.getId());
        String com = (String) button.getText();

        // set the text on the screen depending on the user's action
        switch (v.getId()) {
            case R.id.bCE:
                Calc.setText("");
                Num.setText(""); // delete everything on both screens when CE pressed
                break;
            case R.id.bplusminus: // show the negative value of chosen number on lower screen
                if ((l2 != 0) && (Num.getText().charAt(0) != '-')) {
                    Num.setText("-" + Num.getText());
                }
                break;
            case R.id.bbckspce:
                if (l2 == 0){  // if nothing on screen - break to avoid out of bounds exception
                    break;
                }
                Num.setText(((String) Num.getText()).substring(0,(l2-1))); // delete last character of the string on the lower screen
                Calc.setText(((String) Calc.getText()).substring(0,(l1-1)));
                break;
            case R.id.bplus: case R.id.bminus: case R.id.bdvde: case R.id.bpercent: case R.id.bmltply:
                // take the first operand from what's currently on the lower screen
                firstNo = Double.parseDouble((String) Num.getText());
                // set the operator to currently chosen one
                op = com;
                // continue showing everything on the upper screen // show what's currently on lower screen on the upper
                Calc.setText(Num.getText() + com);

                /*// update the lower screen to show the button pressed
                Num.setText(com);*/
                break;
            case R.id.bequal:
                // show the whole calculation on the upper screen
                Calc.setText((String) Calc.getText() + Num.getText() + com);
                // take the second operand for the calculation from the lower screen
                secondNo = Double.parseDouble((String) Num.getText());
                // access the calculator functionality from the CalculatorClass class
                double output = CalculatorClass.calculations(firstNo,secondNo,op);
                Num.setText(""+ output);
                break;
            default:
               /* String s = ((String) Calc.getText()).substring(0,(l1-1)); // get the last character from the upper screen*/
                // if the previous character was a '0' or
                // an operator show only the currently chosen value on the lower screen
                if (Num.getText().equals("0") || Arrays.asList(ops).contains(Num.getText())){ Num.setText(com);}
                // if the previous character was an operand, continue typing values as usual
                else { Num.setText(Num.getText() + com);}
                break;
        }
        }
    }