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
    private double firstNo, secondNo;
    // string to contain the current operator
    private String op;
    private boolean result;
    private boolean multiop;

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
        int id = v.getId();
        if (id == R.id.bCE) {
            Calc.setText("");
            Num.setText(""); // delete everything on both screens when CE pressed
        } else if (id == R.id.bplusminus) { // show the negative value of chosen number on lower screen
            if ((l2 != 0) && (Num.getText().charAt(0) != '-')) {
                // Num.setText("-" + Num.getText());
                Num.setText(getString(R.string.twostrings, "-", Num.getText()));
            }
        } else if (id == R.id.bbckspce) {
            if (l2 == 0) {  // if nothing on screen - break to avoid out of bounds exception
                return;
            }
            Num.setText(((String) Num.getText()).substring(0, (l2 - 1))); // delete last character of the string on the lower screen
            Calc.setText(((String) Calc.getText()).substring(0, (l1 - 1)));
        } else if (id == R.id.bplus || id == R.id.bminus || id == R.id.bdvde || id == R.id.bpercent || id == R.id.bmltply) {// take the first operand from what's currently on the lower screen
            if (multiop){
                secondNo = Double.parseDouble((String) Num.getText());
                firstNo = CalculatorClass.calculations(firstNo, secondNo, op);
                // set the operator to currently chosen one
                op = com;
                // continue calculations on the upper screen
                Calc.setText(getString(R.string.threestrings, Calc.getText(), Num.getText(), com));
            }
            else{
                firstNo = Double.parseDouble((String) Num.getText());
                // set the operator to currently chosen one
                op = com;
                // continue showing everything on the upper screen // show what's currently on lower screen on the upper
                Calc.setText(getString(R.string.twostrings, Num.getText(), com));
            }
            // update the lower screen to show the button pressed
            Num.setText(com);
            multiop = true;
        } else if (id == R.id.bequal) {
            if (result){ //if this is the case when the user repeats the same operation by pressing "=" multiple times
                //show the new/continuing calculation on upper screen
                if (secondNo % 1 == 0){ // if it's a whole number, display without the decimal ending
                    Calc.setText(getString(R.string.threestringsninteger, Num.getText(), op, (int) secondNo, com));
                }
                else { //display as a double to maintain accuracy
                    Calc.setText(getString(R.string.threestringsndouble, Num.getText(), op, secondNo, com));
                }
                // assign the previous result (which is now on the lower screen) as the firstNo
                firstNo = Double.parseDouble((String) Num.getText());
                // don't change the secondNo so it's the same as before, as is the operator.
            }
            else{
                // show the whole calculation on the upper screen
                Calc.setText(getString(R.string.threestrings, Calc.getText(), Num.getText(), com));
                // take the second operand for the calculation from the lower screen
                secondNo = Double.parseDouble((String) Num.getText());
            }

            // access the calculator functionality from the CalculatorClass class
            double output = CalculatorClass.calculations(firstNo, secondNo, op);

            if (output % 1 == 0){ // if it's a whole number, display without the decimal ending
                Num.setText(getString(R.string.stringninteger, "", (int) output));
            }
            else { //display as a double to maintain accuracy
                Num.setText(getString(R.string.stringndouble, "", output));
            }

            result = true;
            multiop = false;
        } else {/* String s = ((String) Calc.getText()).substring(0,(l1-1)); // get the last character from the upper screen*/
            // if the previous character was a '0' or "=" or
            // an operator show only the currently chosen value on the lower screen
            if (Num.getText().equals("0") || Arrays.asList(ops).contains((String) Num.getText()) || result) {
                Num.setText(com);
                result = false;
            }
            // if the previous character was an operand, continue typing values as usual
            else {
                Num.setText((getString(R.string.twostrings, Num.getText(), com)));
            }
        }
        }
    }