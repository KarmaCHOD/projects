package com.gcitcomplaint.voice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class AddServiceProvider extends AppCompatActivity {
    private TextInputLayout sp_name, sp_id, sp_email, sp_passd;
    private Button sp_btnAdd;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_provider);

        sp_name = findViewById(R.id.username);
        sp_id = findViewById(R.id.employeeID);
        sp_email = findViewById(R.id.email);
        sp_passd = findViewById(R.id.password);
        sp_btnAdd = (Button)findViewById(R.id.add_sp);
        sp_btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() |!validatePassword() | !validateEmail() | !validateId()) {
                    return;
                }
                rootNode = FirebaseDatabase.getInstance();
                reference= rootNode.getReference().child("ServiceProviders");
                //Get All the values
                String name = sp_name.getEditText().getText().toString().trim();
                String email = sp_email.getEditText().getText().toString().trim();
                String id = sp_id.getEditText().getText().toString().trim();
                String password = sp_passd.getEditText().getText().toString().trim();

                ServiceProviders serviceProviders = new ServiceProviders(name, id, email, password);
                reference.child(id).setValue(serviceProviders);
                Toast.makeText(AddServiceProvider.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                Intent obj = new Intent(AddServiceProvider.this, AdminDashboard.class);
                startActivity(obj);
            }
        });
    }
    private boolean validateName(){
        String val = sp_name.getEditText().getText().toString().trim();
        if (val.isEmpty()){
            sp_name.setError("Field Cannot be empty");
            return false;
        }
        else if (!val.matches("[a-zA-Z ]+")){
            sp_name.setError("Name cannot be digits or numbers");
            return false;
        }
        else{
            sp_name.setError(null);
            return true;
        }

    }

     private boolean validateId() {
        String val = sp_id.getEditText().getText().toString().trim();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            sp_id.setError("Field Cannot be empty");
            return false;
        } else if (val.length() != 11) {
            sp_id.setError("Use Employement ID");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            sp_id.setError("White Spaces are not allowed");
            return false;
        } else {
            sp_id.setError(null);
            sp_id.setErrorEnabled(false);
            return true;

        }
    }
    private boolean validateEmail(){
        String val = sp_email.getEditText().getText().toString();
        String emailPattern = "^[0-9]+\\.gcit@rub\\.edu\\.bt";
        Pattern EMAIL_PATTERN = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);

        if (val.isEmpty()) {
            sp_email.setError("Field cannot be empty");
            return false;
        } else if (!EMAIL_PATTERN.matcher(val).matches()) {
            sp_email.setError("Invalid email address");
            return false;
        } else {
            sp_email.setError(null);
            sp_email.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validatePassword(){
        String val = sp_passd.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
//                "(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                //"(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            sp_passd.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            sp_passd.setError("Password is too weak");
            return false;
        } else {
            sp_passd.setError(null);
            sp_passd.setErrorEnabled(false);
            return true;
        }
    }
}