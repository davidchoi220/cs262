package wc25.cs262.calvin.edu.simplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.AdapterView.OnItemSelectedListener;


public class MainActivity extends Activity implements OnItemSelectedListener {

    Button buttonCalculate;
    EditText val1, val2;
    TextView tv1;
    Spinner spinOps;
    String opSelected;
    String[] ops = {"+", "-", "*", "/"};
    double a, b, c;


    private OnClickListener myClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            a = Double.parseDouble(val1.getText().toString());
            b = Double.parseDouble(val2.getText().toString());

            if (opSelected.equals("+")) {
                c = a + b;
                tv1.setText(Double.toString(c));
            } else if (opSelected == "-") {
                c = a - b;
                tv1.setText(Double.toString(c));
            } else if (opSelected == "*") {
                c = a * b;
                tv1.setText(Double.toString(c));
            } else if (opSelected == "/") {
                c = a / b;
                tv1.setText(Double.toString(c));
            } else {
                tv1.setText("Select operation!");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.result);


        buttonCalculate = (Button) findViewById(R.id.buttonCalculate);
        buttonCalculate.setText("Result: ");
        buttonCalculate.setOnClickListener(myClickListener);

        val1 = (EditText) findViewById(R.id.editText4);
        val1.setText("");
        val2 = (EditText) findViewById(R.id.editText6);
        val2.setText("");

        spinOps = (Spinner) findViewById(R.id.spinner01);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, ops);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinOps.setAdapter(adapter);
        spinOps.setOnItemSelectedListener(this);
        //code from YouTube guide "Text Spinner - Android Studio Tutorial" by Coding in Flow
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        opSelected = ops[position];
        tv1.setText("You have chosen " + opSelected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        tv1.setText("Please select operation.");

    }
}
