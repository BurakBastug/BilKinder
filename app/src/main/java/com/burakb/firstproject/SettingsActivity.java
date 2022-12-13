package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    Button openNavBar, navBarReturnHome, navBarProfile,notifPref,edit,changePsw;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData;
    User user;
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //openNavBar = findViewById(R.id.openNavigatorBar);
        //navBarReturnHome = findViewById(R.id.navigatorBarReturnHome);
        //navBarProfile = findViewById(R.id.navigatorBarMyProfile);
        notifPref = findViewById(R.id.notificationPreferences);
        edit = findViewById(R.id.editProfile);
        changePsw = findViewById(R.id.changePassword);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinderdata-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");


        openNavBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        navBarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mUser.getUid())) {
                            startActivity(new Intent(SettingsActivity.this, StudentEditProfileActivity.class));
                        }
                        else if(snapshot.child("Teachers").hasChild(mUser.getUid())) {
                            startActivity(new Intent(SettingsActivity.this, TeacherHomeActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        navBarReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mUser.getUid())) {
                            startActivity(new Intent(SettingsActivity.this, StudentHomeActivity.class));
                        }
                        else if(snapshot.child("Teachers").hasChild(mUser.getUid())) {
                            startActivity(new Intent(SettingsActivity.this, TeacherHomeActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        notifPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        

    }
}
