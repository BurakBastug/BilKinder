package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button feed, myProfile, chat, teacherInfo, weeklySchedule, weeklyMenu, notification,aboutUs,navigationBarProfile, navigationBarHome, navigationBarHamMenu  ;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_menu);

        feed = findViewById(R.id.feed);
        myProfile = findViewById(R.id.myProfile);
        chat = findViewById(R.id.privateChat);
        teacherInfo = findViewById(R.id.teacherInformation);
        weeklySchedule = findViewById(R.id.weeklySchedule);
        weeklyMenu = findViewById(R.id.weeklyMenu);
        notification = findViewById(R.id.notification);
        aboutUs = findViewById(R.id.aboutUs);
        //navigationBarProfile = findViewById(R.id.navigatorBarMyProfile);
        //navigationBarHome = findViewById(R.id.navigatorBarReturnHome);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinderdata-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentHomeActivity.this, FeedActivity.class));
            }
        });
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentHomeActivity.this, StudentProfileActivity.class));
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        teacherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        weeklyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentHomeActivity.this, WeeklyMenuActivity.class));
            }
        });
        weeklySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentHomeActivity.this, WeeklyScheduleActivity.class));
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        startActivity(new Intent(StudentHomeActivity.this, StudentProfileActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }
        return false;
    }
}
