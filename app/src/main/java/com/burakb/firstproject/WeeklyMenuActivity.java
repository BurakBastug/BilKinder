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

import java.util.HashMap;

public class WeeklyMenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;
    private Teacher searched;
    BottomNavigationView bottomNavigationView;
    private TextView mondayMeal, tuesdayMeal,wednesdayMeal,thursdayMeal,fridayMeal;
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_menu);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mondayMeal = findViewById(R.id.monday_meal);
        tuesdayMeal = findViewById(R.id.tuesday_meal);
        wednesdayMeal = findViewById(R.id.Wednesday_meal);
        thursdayMeal = findViewById(R.id.Thursday_meal);
        fridayMeal = findViewById(R.id.Friday_meal);

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Child child = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);

                String teacherName = child.getTeacherName();
                for(DataSnapshot teacherObject : snapshot.child("Teachers").getChildren()){
                    Teacher t = teacherObject.getValue(Teacher.class);
                    if(t.getUsername().equalsIgnoreCase(teacherName)){
                        searched = t;
                    }
                }
                setData(searched.getWeeklyMenu().get("Monday"),searched.getWeeklyMenu().get("Tuesday"),searched.getWeeklyMenu().get("Wednesday"),searched.getWeeklyMenu().get("Thursday"),searched.getWeeklyMenu().get("Friday"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void setData(String mon,String tue, String wed, String thu, String fri){
        mondayMeal.setText(mon);
        tuesdayMeal.setText(tue);
        wednesdayMeal.setText(wed);
        thursdayMeal.setText(thu);
        fridayMeal.setText(fri);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.homee:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        startActivity(new Intent(WeeklyMenuActivity.this, StudentHomeActivity.class));
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
                        startActivity(new Intent(WeeklyMenuActivity.this, StudentProfileActivity.class));
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
                        startActivity(new Intent(WeeklyMenuActivity.this, SettingsActivity.class));
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
