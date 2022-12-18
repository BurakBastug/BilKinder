package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

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

public class StudentProfileForTeachersActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView studentName,teacherAndClass,parentName,bloodType,contactNumber,contactMail,addresss,healthIssues;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData;
    BottomNavigationView bottomNavigationView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stdent_profile_for_teachers);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");

        studentName = findViewById(R.id.studentName);
        teacherAndClass = findViewById(R.id.teacherandclass);
        parentName = findViewById(R.id.parentName);
        bloodType = findViewById(R.id.bloodType);
        contactNumber = findViewById(R.id.contactNumber);
        contactMail = findViewById(R.id.contactMail);
        addresss = findViewById(R.id.address);
        healthIssues = findViewById(R.id.healthIssues);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        String username = "";
        String teacherandclass = "";
        String parentname = "";
        String bloodtype = "";
        String contactnumber = "";
        String contactmail = "";
        String address = "";
        String healthissues = "";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
            parentname = extras.getString("parentName");
            contactnumber = extras.getString("contactNumber");
            bloodtype = extras.getString("bloodType");
            address = extras.getString("address");
            contactmail = extras.getString("contactMail");
            teacherandclass = extras.getString("teacherName");
            healthissues = extras.getString("healthIssues");
        }

        studentName.setText(username);
        teacherAndClass.setText(teacherandclass);
        parentName.setText(parentname);
        bloodType.setText(bloodtype);
        contactNumber.setText(contactnumber);
        contactMail.setText(contactmail);
        addresss.setText(address);
        healthIssues.setText(healthissues);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                startActivity(new Intent(StudentProfileForTeachersActivity.this, TeacherProfileActivity.class));
                break;
            case R.id.homee:
                startActivity(new Intent(StudentProfileForTeachersActivity.this, TeacherHomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(StudentProfileForTeachersActivity.this, SettingsActivity.class));
                break;

        }
        return false;
    }
}
