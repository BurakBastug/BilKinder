package com.burakb.firstproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText txtStuName, txtTeacherName, txtParentName, txtBloodType, txtContactNum, txtContactMailAddress
            , txtHomeAddress, txtSpecialHealthConditions;
    private Button saveButton, profileButton, homeButton, menuButton;

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserInfo();
            }
        });
    }

    private void setUserInfo() {
        String studentName = txtStuName.getText().toString();
        String teacherName = txtTeacherName.getText().toString();
        String parentName = txtParentName.getText().toString();
        String bloodType = txtBloodType.getText().toString();
        String contactNumber = txtContactNum.getText().toString();
        String contactMail = txtContactMailAddress.getText().toString();
        String address = txtHomeAddress.getText().toString();
        String healthConditions = txtSpecialHealthConditions.getText().toString();

        if(TextUtils.isEmpty(studentName)) {
            txtStuName.setError("Student name cannot be empty");
        }
        if(TextUtils.isEmpty(teacherName)) {
            txtTeacherName.setError("Teacher name cannot be empty");
        }
        if(TextUtils.isEmpty(parentName)) {
            txtStuName.setError("Parent name cannot be empty");
        }
        if(!Child.isCorrectFormOfBloodType(bloodType)) { // it can be optional by removing if statement
            txtStuName.setError("Type as the form XXRh");
        }
        if(!Child.isCorrectFormOfContactNumber(contactNumber)) { // it can be optional removing if statement
            txtStuName.setError("Type as the form 0XXXXXXXXXX");
        }
        if(TextUtils.isEmpty(contactMail)) { // it can be optional removing if statement
            txtStuName.setError("Contact mail is required");
        }
        if(TextUtils.isEmpty(address)) { // it can be optional removing if statement
            txtStuName.setError("Address is required");
        }
        if(TextUtils.isEmpty(healthConditions)) { // it can be optional removing if statement
            txtStuName.setError("Health conditions ,s required");
        }

        else {

        }


    }


}
