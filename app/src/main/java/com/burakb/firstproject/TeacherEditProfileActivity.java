package com.burakb.firstproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class TeacherEditProfileActivity extends AppCompatActivity {

    private ImageView image;
    private TextView txtTeacherName, txtClassAndNumberOfStudent;
    private EditText txtAge, txtAddress, txtContactNum, txtContactMail;
    private Button saveButton, profileButton, homeButton, menuButton;

    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_profile_editing);

        image = findViewById(R.id.teacherProfilePicture);
        txtTeacherName = findViewById(R.id.teacherName);
        txtClassAndNumberOfStudent = findViewById(R.id.classAndNumberOfStudent);
        txtAge = findViewById(R.id.teacherAge);
        txtAddress = findViewById(R.id.teacherAddress);
        txtContactNum = findViewById(R.id.teacherContactNumber);
        txtContactMail = findViewById(R.id.teacherContactMail);

        saveButton = findViewById(R.id.saveButton);
        profileButton = findViewById(R.id.profilebtn);
        homeButton = findViewById(R.id.homebtn);
        menuButton = findViewById(R.id.menubtn);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinderdata-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                // TODO: 13.12.2022 get profile image
                txtTeacherName.setText(teacher.getUsername());
                txtClassAndNumberOfStudent.setText(teacher.getClassName() + teacher.getStudentList().size());
                txtAge.setText(teacher.getAge());
                txtAddress.setText(teacher.getAddress());
                txtContactNum.setText(teacher.getContactNum());
                txtContactMail.setText(teacher.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTeacherInfo();
            }
        });
    }

    private void setTeacherInfo() {
        String teacherName = txtTeacherName.getText().toString();
        //String teacherName = txtClassAndNumberOfStudent.getText().toString();
        int age = Integer.parseInt(txtAge.getText().toString());
        String address = txtAddress.getText().toString();
        String contactNum = txtContactNum.getText().toString();
        String contactMail = txtContactMail.getText().toString();

        if(TextUtils.isEmpty(teacherName)) {
            txtTeacherName.setError("Teacher name cannot be empty");
        }
        if(age == 0) {
            txtAge.setError("Age cannot be empty");
        }
        if(TextUtils.isEmpty(address)) {
            txtAddress.setError("Address cannot be empty");
        }
        if(TextUtils.isEmpty(contactNum)) {
            txtContactNum.setError("Contact number cannot be empty");
        }
        if(TextUtils.isEmpty(contactMail)) {
            txtContactMail.setError("Contact mail cannot be empty");
        }
        else {
            mData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Teacher teacher = snapshot.child("Teacher").child(mUser.getUid()).getValue(Teacher.class);
                    teacher.setAllData(teacherName, age, address, contactNum, contactMail);
                    mData.child("Teacher").child(mUser.getUid()).setValue(teacher);
                    // TODO: 13.12.2022 find allstudents belong to changed teacher and change teacherNames

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
