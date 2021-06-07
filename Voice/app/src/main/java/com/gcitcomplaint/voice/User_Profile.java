package com.gcitcomplaint.voice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class User_Profile extends AppCompatActivity {
    private TextView txt_name, txt_id, txt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);
        txt_name = findViewById(R.id.textView_name);
        txt_id = findViewById(R.id.textView_id);
        txt_email = findViewById(R.id.textView_email);

        Toolbar my_toolbar = findViewById(R.id.mtoolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("    Profile");
        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.ic_baseline_keyboard_backspace_24));
        my_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showAllData();
    }
    private void showAllData() {
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("id");
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        txt_name.setText(user_name);
        txt_id.setText(user_id);
        txt_email.setText(user_email);
    }

    public void updatePaddword(View view) {
        Intent in = getIntent();
        String userid = in.getStringExtra("id");
        String password = in.getStringExtra("password");
        Intent obj = new Intent(getApplicationContext(), ChangePassword.class);
        obj.putExtra("id", userid);
        obj.putExtra("password", password);
        startActivity(obj);

    }
}