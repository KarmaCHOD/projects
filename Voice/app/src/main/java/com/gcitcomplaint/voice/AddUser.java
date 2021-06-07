package com.gcitcomplaint.voice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class AddUser extends AppCompatActivity {
    private TextInputLayout regUsername, regUserid, regEmail, regPassd;
    private Button btnAdd;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        regUsername = findViewById(R.id.username);
        regUserid = findViewById(R.id.userID);
        regEmail = findViewById(R.id.email);
        regPassd = findViewById(R.id.password);
        btnAdd = findViewById(R.id.add_user);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() |!validatePassword() | !validateEmail() | !validateId()) {
                    return;
                }
                rootNode = FirebaseDatabase.getInstance();
                reference= rootNode.getReference().child("Users");

                //Get All the values
                String name = regUsername.getEditText().getText().toString().trim();
                String email = regEmail.getEditText().getText().toString().trim();
                String id = regUserid.getEditText().getText().toString().trim();
                String password = regPassd.getEditText().getText().toString().trim();

                Users users = new Users(name, email, id, password);
                reference.child(id).setValue(users);
                Toast.makeText(AddUser.this, "User added successfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
    private boolean validateName(){
        String val = regUsername.getEditText().getText().toString().trim();
        if (val.isEmpty()){
            regUsername.setError("Field Cannot be empty");
            return false;
        }
        else if (!val.matches("[a-zA-Z ]+")){
            regUserid.setError("Name cannot be digits or numbers");
            return false;
        }
        else{
            regUsername.setError(null);
            return true;
        }

    }
    private boolean validateId() {
        String val = regUserid.getEditText().getText().toString().trim();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            regUserid.setError("Field Cannot be empty");
            return false;
        } else if (val.length() != 8) {
            regUserid.setError("userId should be their enrolment number");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUserid.setError("White Spaces are not allowed");
            return false;
        }
        else {
            regUserid.setError(null);
            regUsername.setErrorEnabled(false);
            return true;

        }
    }
    private boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "^[0-9]+\\.gcit@rub\\.edu\\.bt";
        Pattern EMAIL_PATTERN = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        }else if (!EMAIL_PATTERN.matcher(val).matches()) {
            regEmail.setError("Use College email address");
            return false;
        }
        else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validatePassword(){
        String val = regPassd.getEditText().getText().toString();
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
            regPassd.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassd.setError("Password is too weak, use atleast 1 alphabet and digit");
            return false;
        } else {
            regPassd.setError(null);
            regPassd.setErrorEnabled(false);
            return true;
        }

    }

}
