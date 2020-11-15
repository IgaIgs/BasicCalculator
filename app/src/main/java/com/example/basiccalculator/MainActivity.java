package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.basiccalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    // doubles needed to store values for calculations
    private double firstNo, secondNo;
    // string to contain the current operator
    private String op;
    // to keep track of "=" pressed for repeated calculations
    private boolean result;
    // keep track of operators for multiple operations
    private boolean multiop;
    // indicate whether an operator was pressed to hide it from the lower screen
    private boolean wasAnOp;

    /**
     * Display the layout of the app on start
     * @param savedInstanceState - display the last saved state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }

    /**
     * The beaviour of the app. Depending on the button (view) being pressed by the user,
     * certain tasks are performed by the app and content being displayed on the two screens.
     * @param v - the view clicked by the user
     */
    public void onClick(View v) {

        // assign the two text views to local variables for easier use
        TextView Calc = binding.CalculationView;
        TextView Num = binding.NumbersView;

        // get the length of the current text being displayed on both screens
        int l1 = binding.CalculationView.getText().length();
        int l2 = binding.NumbersView.getText().length();

        //get the text of the pressed button
        Button button  = findViewById(v.getId());
        String com = (String) button.getText();

        // get the id of the chosen view to find out which button was pressed
        int id = v.getId();

        // set the text on the screens and perform calculations depending on the user's action
        if (id == R.id.bCE) {

            // delete everything on both screens when CE pressed
            Calc.setText("");
            Num.setText("");

        } else if (id == R.id.bplusminus) { // show the negative value of chosen number on lower screen

            if ((l2 != 0) && (Num.getText().charAt(0) != '-')) {
                Num.setText(getString(R.string.twostrings, "-", Num.getText()));
            }

        } else if (id == R.id.bbckspce) {
            if (l2 == 0) {  // if nothing on screen - break to avoid out of bounds exception
                return;
            }
            // delete last character of the strings displayed on both screens
            Num.setText(((String) Num.getText()).substring(0, (l2 - 1)));
            Calc.setText(((String) Calc.getText()).substring(0, (l1 - 1)));

        } else if (id == R.id.bplus || id == R.id.bminus || id == R.id.bdvde || id == R.id.bpercent
                || id == R.id.bmltply) {
            if (multiop){ // if the user performs multiple calculations in one go
                // assign what's currently on the lower screen to the secondNo
                secondNo = Double.parseDouble((String) Num.getText());
                // assign the result from the previous calculation as the firstNo
                firstNo = CalculatorClass.calculations(firstNo, secondNo, op);
                // set the operator to currently chosen one
                op = com;
                // continue calculations on the upper screen
                Calc.setText(getString(R.string.threestrings, Calc.getText(), Num.getText(), com));
            }
            else{
                // take the first operand from what's currently on the lower screen
                firstNo = Double.parseDouble((String) Num.getText());
                // set the operator to currently chosen one
                op = com;
                // continue showing everything on the upper screen // show what's currently on lower screen on the upper
                Calc.setText(getString(R.string.twostrings, Num.getText(), com));
            }
            // update the boolean trackers
            multiop = true; // might be a multi calculation
            wasAnOp = true; // an operator was just pressed
        } else if (id == R.id.bequal) {

            if (result){
                // if a result has already been obtained, so it's this case when the user repeats
                // the same operation by pressing "=" multiple times
                // show the new/continuing calculation on upper screen

                if (secondNo % 1 == 0){ // if it's a whole number, display without the decimal ending
                    Calc.setText(getString(R.string.threestringsninteger, Num.getText(), op, (int) secondNo, com));
                }
                else { //display as a double to maintain accuracy
                    Calc.setText(getString(R.string.threestringsndouble, Num.getText(), op, secondNo, com));
                }

                // assign the previous result (which is now on the lower screen) as the firstNo
                firstNo = Double.parseDouble((String) Num.getText());
                // don't change the secondNo so it's the same as before, as is the operator
            }
            else{
                // show the whole calculation on the upper screen
                Calc.setText(getString(R.string.threestrings, Calc.getText(), Num.getText(), com));
                // take the second operand for the calculation from the lower screen
                secondNo = Double.parseDouble((String) Num.getText());
            }
            // access the calculations functionality from the CalculatorClass class
            double output = CalculatorClass.calculations(firstNo, secondNo, op);

            if (output % 1 == 0){ // if it's a whole number, display without the decimal ending
                Num.setText(getString(R.string.stringninteger, "", (int) output));
            }
            else { //display as a double to maintain accuracy
                Num.setText(getString(R.string.stringndouble, "", output));
            }

            //update the boolean trackers
            result = true; // a result was obtained
            multiop = false; // reset the multiop tracker cuz the calcation finished
        } else {
            // if the previous character was a '0' (at the start) or
            // an operator or "=" show only the currently chosen value on the lower screen
            if (Num.getText().equals("0") || wasAnOp || result) {
                Num.setText(com);
                // and update the trackers
                result = false;
                wasAnOp = false;
            }
            // if the previous character was an operand, continue typing values as usual
            else {
                Num.setText((getString(R.string.twostrings, Num.getText(), com)));
            }
        }
        }
    }