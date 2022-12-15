package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class TeacherEditProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ImageView image;
    private TextView txtTeacherName, txtClassAndNumberOfStudent;
    private EditText txtAge, txtAddress, txtContactNum, txtContactMail;
    private Button saveButton;

    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_profile_editing);

        image = findViewById(R.id.teacherProfilePicture);
        txtTeacherName = findViewById(R.id.teacherName);
        txtClassAndNumberOfStudent = findViewById(R.id.classAndNumberOfStudent);
        txtAge = findViewById(R.id.teacherAge);
        txtAddress = findViewById(R.id.teacherAddress);
        txtContactNum = findViewById(R.id.teacherContactNumber);
        txtContactMail = findViewById(R.id.teacherMail);

        saveButton = findViewById(R.id.saveButton);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        txtAge.setInputType(InputType.TYPE_CLASS_NUMBER );
        txtAge.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        txtAge.setSingleLine(true);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        txtContactNum.setInputType(InputType.TYPE_CLASS_NUMBER );
        txtContactNum.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        txtContactNum.setSingleLine(true);

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                // TODO: 13.12.2022 get profile image
                txtTeacherName.setText(teacher.getUsername());
                txtClassAndNumberOfStudent.setText("Class Name: " + teacher.getUsername() + "%nNumber of Students: " + (teacher.getStudentList().size()-1));
                txtAge.setText(teacher.getAge());
                txtAddress.setText(teacher.getAddress());
                txtContactNum.setText(teacher.getTelNum());
                txtContactMail.setText(teacher.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTeacherData())
                    startActivity(new Intent(TeacherEditProfileActivity.this, TeacherHomeActivity.class));
            }
        });
    }

    private boolean checkTeacherData() {
        String teacherName = txtTeacherName.getText().toString();
        String age = txtAge.getText().toString();
        String address = txtAddress.getText().toString();
        String contactNum = txtContactNum.getText().toString();
        String contactMail = txtContactMail.getText().toString();
        boolean isEnoughData = true;

        if(TextUtils.isEmpty(teacherName)) {
            txtTeacherName.setError("Teacher name cannot be empty");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(age)) {
            txtAge.setError("Invalid age");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(address)) {
            txtAddress.setError("Address cannot be empty");
            isEnoughData = false;
        }
        if(!User.isCorrectFormOfContactNumber(contactNum)) {
            txtContactNum.setError("Type as the form 0XXXXXXXXXX");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(contactMail)) {
            txtContactMail.setError("Contact mail cannot be empty");
            isEnoughData = false;
        }
        else {
            mData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Teacher teacher = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                    teacher.setTeacherData(age, address, contactNum, contactMail);
                    mData.child("Teachers").child(mUser.getUid()).setValue(teacher);
                    Toast.makeText(TeacherEditProfileActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return isEnoughData;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.settings:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        startActivity(new Intent(TeacherEditProfileActivity.this, SettingsActivity.class));
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
                        startActivity(new Intent(TeacherEditProfileActivity.this, TeacherHomeActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            case R.id.profile:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        startActivity(new Intent(TeacherEditProfileActivity.this, TeacherProfileActivity.class));
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
