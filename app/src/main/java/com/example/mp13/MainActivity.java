package com.example.mp13;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.text.NumberFormat;
public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener {
    private TextView textViewBillAmount;
    private TextView textViewPercent;
    private TextView tipTextView;
    private double billAmount = 0;
    private double percent = .15;
    private EditText editTextBillAmount;
    private TextView totalViewAmount;
    private SeekBar seekBarPercent;

    private Spinner peopleSpinner;
    private RadioGroup roundingGroup;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBillAmount = findViewById(R.id.editText_BillAmount);
        editTextBillAmount.addTextChangedListener(this);

        textViewBillAmount = findViewById(R.id.textView_BillAmount);
        textViewPercent = findViewById(R.id.textView_Percent);
        tipTextView = findViewById(R.id.tipTextView);
        totalViewAmount = findViewById(R.id.totalViewAmount);

        seekBarPercent = findViewById(R.id.seekBar_Percent); // Initialize the SeekBar
        seekBarPercent.setOnSeekBarChangeListener(this);

        Spinner peopleSpinner = findViewById(R.id.people_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.number_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        peopleSpinner.setAdapter(adapter);

        RadioGroup roundingGroup = findViewById(R.id.rounding_radio_group);
        roundingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            }
        });


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d(TAG, "inside onTextChanged method: charSequence= " + charSequence);
        billAmount = Double.parseDouble(charSequence.toString()) / 100;
        Log.d(TAG, "Bill Amount = " + billAmount);
        textViewBillAmount.setText(currencyFormat.format(billAmount));
        calculate();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        percent = progress / 100.0;
        try {
            billAmount = Double.parseDouble(editTextBillAmount.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        calculate();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void calculate() {
        Log.d(TAG, "inside calculate method");


        // format percent and display in percentTextView
        textViewPercent.setText(percentFormat.format(percent));

        // calculate the tip and total
        double tip = billAmount * percent;

        //use the tip example to do the same for the Total
        // display tip and total formatted as currency
        //user currencyFormat instead of percentFormat to set the textViewTip
        tipTextView.setText(currencyFormat.format(tip));

        //use the tip example to do the same for the Total
        double total = billAmount + tip;
        totalViewAmount.setText(currencyFormat.format(total));

    }


}
