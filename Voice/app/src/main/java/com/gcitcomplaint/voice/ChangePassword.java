package com.gcitcomplaint.voice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {
    EditText curPsd, newPsd, conPsd;
    Button save, cancel;
    private DatabaseReference reference;
//    private TextInputLayout oldPassword, newPassword, confirmPassword;
//    private TextView changePasswordMessage;
//    private Button btn;
    private String s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        curPsd = findViewById(R.id.edit_current);
        newPsd = findViewById(R.id.edit_newpassword);
        conPsd = findViewById(R.id.edit_conpassword);
        save = findViewById(R.id.btn_save);
        cancel = findViewById(R.id.btn_cancel);

        String userID = getIntent().getStringExtra("id");
        s1 = userID;

        Toolbar my_toolbar = findViewById(R.id.mtoolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle(" Reset Your Password");
        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.ic_baseline_keyboard_backspace_24));
        my_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void changePassword(View view) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String oldPasswordFromUser = curPsd.getText().toString().trim();
        String newPasswordFormUser = newPsd.getText().toString().trim();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("id").equalTo(s1);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String oldPasswordDB = snapshot.child(s1).child("password").getValue(String.class);
                    if(oldPasswordDB.equals(oldPasswordFromUser)){
                        curPsd.setError(null);
                        curPsd.setEnabled(false);
                        if(!validatePassword()){
                            progressDialog.dismiss();
                            return;
                        }
                        else{
                            progressDialog.dismiss();
                            reference.child(s1).child("password").setValue(newPasswordFormUser);
                            Toast.makeText(getApplicationContext(), "Password Updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        curPsd.setError("Wrong password");
                        curPsd.requestFocus();
                    }
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"No such User",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }
    private boolean validatePassword(){
        String val = newPsd.getText().toString().trim();
        String val1 = conPsd.getText().toString().trim();
        final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-z])" +         //at least 1 lower case letter
//                        "(?=.*[A-Z])" +         //at least 1 upper case letter
                        ".{4,}" +               //at least 4 characters
                        "$");
        if(val.isEmpty()){
            newPsd.setError("Password is Empty");
            newPsd.requestFocus();
            return false;
        }
        else if(val1.isEmpty()){
            conPsd.setError("Confirm Password is Empty");
            conPsd.requestFocus();
            return false;
        }
        else if(!val.isEmpty() && val1.isEmpty()){
            conPsd.setError("Confirm Password is Empty");
            conPsd.requestFocus();
            return false;
        }
        else if(val.length() < 4) {
            newPsd.setError("Password is too short, it should be more than 4 digits");
            newPsd.requestFocus();
            return false;
        }
        else if(!val.equals(val1)){
            conPsd.setError("Confirm Password is didn't match");
            conPsd.requestFocus();
            return false;
        }
        return true;
    }

    public void CancelChange(View view) {
        finish();
    }
}