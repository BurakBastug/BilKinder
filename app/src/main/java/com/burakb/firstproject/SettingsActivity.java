package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class SettingsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button openNavBar, navBarReturnHome, navBarProfile,notifPref,edit,changePsw,logOut,bugReport;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData, nData;
    User user;
    BottomNavigationView bottomNavigationView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //openNavBar = findViewById(R.id.openNavigatorBar);
        //navBarReturnHome = findViewById(R.id.navigatorBarReturnHome);
        //navBarProfile = findViewById(R.id.navigatorBarMyProfile);
        notifPref = findViewById(R.id.notificationPreferences);
        edit = findViewById(R.id.editProfile);
        changePsw = findViewById(R.id.changePassword);
        logOut = findViewById(R.id.logoutBtn);
        bugReport = findViewById(R.id.bugReport);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        nData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Events");
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        bugReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,ReportActivity.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(SettingsActivity.this, "User logout", Toast.LENGTH_LONG).show();
                startActivity(new Intent(new Intent(SettingsActivity.this, LoginActivity.class)));
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
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(SettingsActivity.this, StudentEditProfileActivity.class));
                            System.out.println("öğrenci");

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(SettingsActivity.this, TeacherEditProfileActivity.class));
                            System.out.println("hoca");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
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
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(SettingsActivity.this, StudentProfileActivity.class));
                            System.out.println("öğrenci");

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(SettingsActivity.this, TeacherProfileActivity.class));
                            System.out.println("hoca");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            case R.id.homee:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(SettingsActivity.this, StudentHomeActivity.class));
                            System.out.println("öğrenci");

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(SettingsActivity.this, TeacherHomeActivity.class));
                            System.out.println("hoca");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            case R.id.settings:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
        }

        return false;
    }
}
