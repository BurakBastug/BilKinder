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

public class TeacherProfileForStudentsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;
    private TextView teacherName,classAndNumberOfSudents,teacherAge
            ,teacherAdress, teacherContactNumber, teacherContactMail;
    private Teacher searched;
    BottomNavigationView bottomNavigationView;


    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_profile_for_students);

        teacherName = findViewById(R.id.teacherName);
        classAndNumberOfSudents = findViewById(R.id.classAndNumberOfStudent);
        teacherAge = findViewById(R.id.teacherAge);
        teacherAdress = findViewById(R.id.teacherAddress);
        teacherContactNumber = findViewById(R.id.teacherContactNumber);
        teacherContactMail = findViewById(R.id.teacherContactMail);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println("First");
                Child child = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);

                String teacherName = child.getTeacherName();
                for(DataSnapshot teacherObject : snapshot.child("Teachers").getChildren()){
                    Teacher t = teacherObject.getValue(Teacher.class);
                    if(t.getUsername().equalsIgnoreCase(teacherName)){
                        searched = t;
                    }
                }
                setData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                        startActivity(new Intent(TeacherProfileForStudentsActivity.this, StudentHomeActivity.class));
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
                        startActivity(new Intent(TeacherProfileForStudentsActivity.this, StudentProfileActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
                break;
        }

        return false;
    }

    public void setData(){
        teacherName.setText(searched.getUsername());
        classAndNumberOfSudents.setText("Student Count: " + (searched.getStudentList().size() - 1));
        teacherAge.setText("Age: " + searched.getAge());
        teacherAdress.setText("Adress: " + searched.getAddress());
        teacherContactNumber.setText(searched.getTelNum());
        teacherContactMail.setText(searched.getEmail());
    }
}
