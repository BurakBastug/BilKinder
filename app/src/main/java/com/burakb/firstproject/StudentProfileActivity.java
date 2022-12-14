package com.burakb.firstproject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.database.collection.LLRBNode;

public class StudentProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView txtStudentName, txtTeacherName, txtParentName, txtBloodType, txtContactNumber,
            txtContactMail, txtAddress, txtMedicalCondition;
    private Button editButton, currentlySickButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;


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
        
        editButton = findViewById(R.id.editbtn);
        currentlySickButton = findViewById(R.id.sickbtn);


        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinderdata-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

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
        currentlySickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Child tmp = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                        if (tmp.getIsSick()==false){
                            tmp.setIsSick(true);
                        }
                        else if (tmp.getIsSick()==true) {
                            tmp.setIsSick(false);
                        }
                        if(tmp.getIsSick()) {
                            currentlySickButton.setBackgroundColor(getResources().getColor(R.color.purple_200));
                            currentlySickButton.setText("Currently Sick");
                            System.out.println("kırmızı");

                        }
                        else {
                            currentlySickButton.setBackgroundColor(getResources().getColor(R.color.teal_200));
                            currentlySickButton.setText("Currently Not Sick");
                            System.out.println("yeşil");
                        }

                        mData.child("Students").child(mUser.getUid()).setValue(tmp);



                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}
