package com.gcitcomplaint.voice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogdeComplaint extends AppCompatActivity {
    private TextView name, id;
    private EditText lodge_com, com_title;
    private Button sumbit_btn, cancel_btn;
    DatabaseReference voiceDbRef, User_voice;
    long complaintID=0;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logde_complaint);

        name = findViewById(R.id.name);
        id = findViewById(R.id.email);

        lodge_com = findViewById(R.id.edit_com);
        com_title = findViewById(R.id.editText_tile);
        spinner = findViewById(R.id.complaintType_Spinner);
        sumbit_btn = findViewById(R.id.com_submit);
        cancel_btn = findViewById(R.id.com_cancel);
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("id");

        name.setText(user_name);
        id.setText(user_email);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Complaint_Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        voiceDbRef = FirebaseDatabase.getInstance().getReference().child("Complaints");
        User_voice = FirebaseDatabase.getInstance().getReference().child(user_email);

        voiceDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    complaintID = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        sumbit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateTitle() || !validateDescription()) {
                    return;
                }
                String per_name = name.getText().toString().trim();
                String per_id = id.getText().toString().trim();
                String type = spinner.toString().trim();
                String description = lodge_com.getText().toString().trim();
                String title = com_title.getText().toString().trim();
                String status = "Pending";
                String id = voiceDbRef.push().getKey();
                Complaints complaints = new Complaints(per_name, per_id, title, type, description, status);
                complaints.setComplaint_type(spinner.getSelectedItem().toString());
                Toast.makeText(LogdeComplaint.this, "Complaint Lodged Successfully", Toast.LENGTH_SHORT).show();
              //  String mssgComplaint = lodge_com.getText().toString();
//                voiceDbRef.child(String.valueOf(complaintID+1)).setValue(complaints);
                voiceDbRef.child(id).setValue(complaints);
                User_voice.child(id).setValue(complaints);
                finish();
//                voiceDbRef.child(String.valueOf(complaintID+1)).setValue(mssgComplaint);

//                Toast.makeText(LogdeComplaint.this, "Your complaint is in pending status" +
//                        "Complaint Lodged Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                startActivity(intent);
            }
        });

    }


    private boolean validateTitle(){
        String val = lodge_com.getText().toString().trim();
        if (val.isEmpty()){
            lodge_com.setError("Field Cannot be empty");
            return false;
        }
        else{
            lodge_com.setError(null);
            return true;
        }
    }
    private boolean validateDescription(){
        String val = lodge_com.getText().toString().trim();
        if (val.isEmpty()){
            lodge_com.setError("Field Cannot be empty");
            return false;
        }
        else{
            lodge_com.setError(null);
            return true;
        }
    }
}