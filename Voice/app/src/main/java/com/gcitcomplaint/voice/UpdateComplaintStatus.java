package com.gcitcomplaint.voice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class UpdateComplaintStatus extends AppCompatActivity {
    private Spinner spinner;
    private Button sumbit_btn, cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_complaint_status);
        spinner = findViewById(R.id.status_Spinner);
        sumbit_btn = findViewById(R.id.submit);
        cancel_btn = findViewById(R.id.cancel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Status_Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}