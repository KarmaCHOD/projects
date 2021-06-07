package com.gcitcomplaint.voice;

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
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private ArrayList<Complaints> complaints;
    //
    RecyclerView recyclerView;
    UserHelperAdapter adapter;
  //  HelperAdapter helperAdapter;
    DatabaseReference reference, refChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaints = new ArrayList<>();


        Intent in = getIntent();
        String userI = in.getStringExtra("id");
        reference = FirebaseDatabase.getInstance().getReference(userI);
//        refChild = reference.child(userI);

        FirebaseRecyclerOptions<Complaints> options =
                new FirebaseRecyclerOptions.Builder<Complaints>()
                        .setQuery(reference, Complaints.class)
                        .build();
        adapter=new UserHelperAdapter(options);
        recyclerView.setAdapter(adapter);
        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu_user);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:

                        Intent intent = new Intent(UserDashboard.this, UserDashboard.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_profile:
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
                    case  R.id.nav_lodge:
                        Intent intent3 = getIntent();
                        String userName = intent3.getStringExtra("name");
                        String userId = intent3.getStringExtra("id");
                        Intent obj1 = new Intent(UserDashboard.this, LogdeComplaint.class);
                        obj1.putExtra("name", userName);
                        obj1.putExtra("id", userId);
                        startActivity(obj1);
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
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout_user);
        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}