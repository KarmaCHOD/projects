package com.gcitcomplaint.voice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderDashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    List<Complaints> complaints;
    RecyclerView recyclerView;
//    HelperAdapter helperAdapter;
    SPHelperAdapter spHelperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_dashboard);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaints = new ArrayList<>();
        FirebaseRecyclerOptions<Complaints> options =
                new FirebaseRecyclerOptions.Builder<Complaints>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints"), Complaints.class)
                        .build();

        spHelperAdapter=new SPHelperAdapter(options);
        recyclerView.setAdapter(spHelperAdapter);

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu_sp);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        Intent intent = new Intent(ServiceProviderDashboard.this, ServiceProviderDashboard.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_profile:
                        Intent intent2 = getIntent();
                        String user_id = intent2.getStringExtra("id");
                        String user_name = intent2.getStringExtra("name");
                        String user_email = intent2.getStringExtra("email");
                        String user_password = intent2.getStringExtra("password");
                        Intent obj = new Intent(getApplicationContext(), User_Profile.class);
                        obj.putExtra("name", user_name);
                        obj.putExtra("email", user_email);
                        obj.putExtra("id", user_id);
                        obj.putExtra("password", user_password);
                        startActivity(obj);
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        break;
                    case  R.id.nav_share:{

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    }
                    break;
                }
                return false;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        spHelperAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        spHelperAdapter.stopListening();
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout_sp);
        Toolbar toolbar = findViewById(R.id.toolbar_sp);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

//    public void UpdateStatus(View view) {
//        Intent intent = new Intent(getApplicationContext(), UpdateComplaintStatus.class);
//        startActivity(intent);
//    }
}