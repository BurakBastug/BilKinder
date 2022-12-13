package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentEditProfileActivity extends AppCompatActivity {

    private EditText txtStuName, txtParentName, txtBloodType, txtContactNum, txtContactMailAddress
            , txtHomeAddress, txtSpecialHealthConditions;
    private TextView txtTeacherName;
    private Button saveButton, profileButton, homeButton, menuButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studenteditpage);

        txtStuName = findViewById(R.id.studentname);
        txtTeacherName = findViewById(R.id.teacherandclass);
        txtParentName = findViewById(R.id.parentname);
        txtBloodType = findViewById(R.id.bloodtype);
        txtContactNum = findViewById(R.id.contactnumber);
        txtContactMailAddress = findViewById(R.id.contactmail);
        txtHomeAddress = findViewById(R.id.address);
        txtSpecialHealthConditions = findViewById(R.id.healthissues);

        saveButton = findViewById(R.id.saveButton);
        profileButton = findViewById(R.id.barprofilebtn);
        homeButton = findViewById(R.id.barhomebtn);
        menuButton = findViewById(R.id.barmenubtn);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinderdata-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Child tmp = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                txtStuName.setText(tmp.getUsername());
                txtTeacherName.setText("Teacher name: " + tmp.getTeacherName());
                txtParentName.setText(tmp.getParentName());
                txtBloodType.setText(tmp.getBloodType());
                txtContactNum.setText(tmp.getContactNumber());
                txtContactMailAddress.setText(tmp.getContactMail());
                txtHomeAddress.setText(tmp.getAddress());
                txtSpecialHealthConditions.setText(tmp.getMedicalCondition());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData())
                    startActivity(new Intent(StudentEditProfileActivity.this, StudentHomeActivity.class));
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData())
                    startActivity(new Intent(StudentEditProfileActivity.this, StudentProfileActivity.class));
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData())
                    startActivity(new Intent(StudentEditProfileActivity.this, StudentHomeActivity.class));
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 11.12.2022 implement the menu button
            }
        });
    }

    private boolean checkData() {
        String studentName = txtStuName.getText().toString();
        String parentName = txtParentName.getText().toString();
        String bloodType = txtBloodType.getText().toString();
        String contactNumber = txtContactNum.getText().toString();
        String contactMail = txtContactMailAddress.getText().toString();
        String address = txtHomeAddress.getText().toString();
        String healthConditions = txtSpecialHealthConditions.getText().toString();

        if(TextUtils.isEmpty(studentName)) {
            txtStuName.setError("Student name cannot be empty");
            return false;
        }
        if(TextUtils.isEmpty(parentName)) {
            txtParentName.setError("Parent name cannot be empty");
            return false;
        }
        if(!Child.isCorrectFormOfBloodType(bloodType)) { // it can be optional by removing if statement
            txtBloodType.setError("Type like the form ABRh+");
            return false;
        }
        if(!Child.isCorrectFormOfContactNumber(contactNumber)) { // it can be optional removing if statement
            txtContactNum.setError("Type as the form 0XXXXXXXXXX");
            return false;
        }
        if(TextUtils.isEmpty(contactMail)) { // it can be optional removing if statement
            txtContactMailAddress.setError("Contact mail is required");
            return false;
        }
        if(TextUtils.isEmpty(address)) { // it can be optional removing if statement
            txtHomeAddress.setError("Address is required");
            return false;
        }
        if(TextUtils.isEmpty(healthConditions)) { // it can be optional removing if statement
            txtSpecialHealthConditions.setError("Health conditions ,s required");
            return false;
        }
        else {
            mData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Child tmp = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                    tmp.setAllData(studentName, parentName, bloodType ,contactNumber, contactMail, address, healthConditions);
                    mData.child("Students").child(mUser.getUid()).setValue(tmp);
                    Toast.makeText(StudentEditProfileActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return true;
        }
    }
}
