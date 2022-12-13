package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class TeacherProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView txtTeacherName, txtClassAndNumOfStu, txtTeacherAge, txtAddress, txtTeacherContactNum, txtTeacherContactMail;
    private Button editButton, profileButton, homeButton, menuButton;
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
        profileButton = findViewById(R.id.profilebtn);
        homeButton = findViewById(R.id.homebtn);
        menuButton = findViewById(R.id.menubtn);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinderdata-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher tmp = snapshot.child("Teacher").child(mUser.getUid()).getValue(Teacher.class);
                // TODO: 13.12.2022 Get profile image 
                txtTeacherName.setText(tmp.getUsername());
                txtClassAndNumOfStu.setText("Class and Number of Students: " + tmp.getClassName() + " " + tmp.getStudentList().size());
                txtTeacherAge.setText("Age: " + tmp.getAge());
                txtAddress.setText("Address: " + tmp.getAddress());
                txtTeacherContactNum.setText("Contact Number: " + tmp.getContactNum());
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
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TeacherProfileActivity.this, "Already in profile page", Toast.LENGTH_SHORT).show();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 13.12.2022 uncomment following code when TeacherHomeActivity class is ready 
                //startActivity(new Intent(TeacherProfileActivity.this, TeacherHomeActivity.class));
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 13.12.2022 implement menu button
            }
        });
    }
}
