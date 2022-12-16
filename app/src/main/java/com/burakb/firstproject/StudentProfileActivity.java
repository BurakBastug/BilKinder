package com.burakb.firstproject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.database.collection.LLRBNode;

public class StudentProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ImageView profileImage;
    private TextView txtStudentName, txtTeacherName, txtParentName, txtBloodType, txtContactNumber,
            txtContactMail, txtAddress, txtMedicalCondition;
    private Button editButton, currentlySickButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;
    BottomNavigationView bottomNavigationView;
    private CheckBox checkIsSick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentprofile);

        profileImage = findViewById(R.id.profilePhoto);
        txtStudentName = findViewById(R.id.studentName);
        txtTeacherName = findViewById(R.id.teacherandclass);
        txtParentName = findViewById(R.id.parentName);
        txtBloodType = findViewById(R.id.bloodType);
        txtContactNumber = findViewById(R.id.contactNumber);
        txtContactMail = findViewById(R.id.contactMail);
        txtAddress = findViewById(R.id.address);
        txtMedicalCondition = findViewById(R.id.healthIssues);
        checkIsSick = findViewById(R.id.checkBox);
        
        editButton = findViewById(R.id.editbtn);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Child tmp = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                checkIsSick.setChecked(tmp.getIsSick());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        checkIsSick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Child tmp = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                        tmp.setIsSick(checkIsSick.isChecked());
                        if(checkIsSick.isChecked()){
                            tmp.setMedicalCondition("Currently sick");
                        }
                        else{
                            tmp.setMedicalCondition("Not sick");
                        }

                        mData.child("Students").child(mUser.getUid()).setValue(tmp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Child tmp = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                txtStudentName.setText(tmp.getUsername());
                txtTeacherName.setText("Teacher and Class: " + tmp.getTeacherName());
                txtParentName.setText("Parent name: " + tmp.getParentName());
                txtBloodType.setText("Blood Type: " + tmp.getBloodType());
                txtContactNumber.setText("Contact Number: " + tmp.getContactNumber());
                txtContactMail.setText("Contact mail: " + tmp.getContactMail());
                txtAddress.setText("Address: " + tmp.getAddress());
                txtMedicalCondition.setText("Medical Condition: " + tmp.getMedicalCondition());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentProfileActivity.this, StudentEditProfileActivity.class));
            }
        });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.homee:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        startActivity(new Intent(StudentProfileActivity.this, StudentHomeActivity.class));
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
                        startActivity(new Intent(StudentProfileActivity.this, SettingsActivity.class));
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
