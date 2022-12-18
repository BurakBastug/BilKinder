package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Button aboutUs, askForPermission,
        viewWeeklyMenu, viewWeeklySchedule, viewStudent, privateChat, myProfile,feed,
        startaPoll;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData;
    BottomNavigationView bottomNavigationView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_menu);

        aboutUs = findViewById(R.id.aboutUs);
        askForPermission = findViewById(R.id.askForPermission);
        viewWeeklyMenu = findViewById(R.id.viewWeeklyMenu);
        viewWeeklySchedule = findViewById(R.id.viewWeeklySchedule);
        viewStudent = findViewById(R.id.viewStudentsStatus);
        privateChat = findViewById(R.id.privateChat);
        myProfile = findViewById(R.id.announce);
        feed = findViewById(R.id.feed);
        startaPoll = findViewById(R.id.startAPoll);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        askForPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherHomeActivity.this, NotifCreationActivity.class));
            }
        });
        viewWeeklyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherHomeActivity.this,WeeklyMenuEditActivity.class));
            }
        });
        viewWeeklySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherHomeActivity.this, NotificationActivity.class));
            }
        });
        viewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherHomeActivity.this, ChildrenStatusActivity.class));
            }
        });
        privateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherHomeActivity.this,ChatListActivity.class));
            }
        });
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherHomeActivity.this, EventCreationActivity.class));
            }
        });
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherHomeActivity.this, FeedActivity.class));
            }
        });
        startaPoll.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(TeacherHomeActivity.this, TeacherProfileActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(TeacherHomeActivity.this, SettingsActivity.class));
                break;
        }
        return false;
    }
}
