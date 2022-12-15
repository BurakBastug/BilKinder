package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class TeacherProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private ImageView profileImage;
    private TextView txtTeacherName, txtClassAndNumOfStu, txtTeacherAge, txtAddress, txtTeacherContactNum, txtTeacherContactMail;
    private Button editButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_profile);

        profileImage = findViewById(R.id.teacherProfilePicture);
        txtTeacherName = findViewById(R.id.teacherName);
        txtClassAndNumOfStu = findViewById(R.id.classAndNumberOfStudent);
        txtTeacherAge = findViewById(R.id.teacherAge);
        txtAddress = findViewById(R.id.teacherAddress);
        txtTeacherContactNum = findViewById(R.id.teacherContactNumber);
        txtTeacherContactMail = findViewById(R.id.teacherContactMail);
        editButton = findViewById(R.id.editButton);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher tmp = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                // TODO: 13.12.2022 Get profile image
                txtTeacherName.setText(tmp.getUsername());
                txtClassAndNumOfStu.setText("Class and Number of Students: " + tmp.getUsername() + " " + (tmp.getStudentList().size()-1));
                txtTeacherAge.setText("Age: " + tmp.getAge());
                txtAddress.setText("Address: " + tmp.getAddress());
                txtTeacherContactNum.setText("Contact Number: " + tmp.getTelNum());
                txtTeacherContactMail.setText("Contact Mail:" + tmp.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherProfileActivity.this, TeacherEditProfileActivity.class));
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
                        startActivity(new Intent(TeacherProfileActivity.this, TeacherHomeActivity.class));
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
                        startActivity(new Intent(TeacherProfileActivity.this, SettingsActivity.class));
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
