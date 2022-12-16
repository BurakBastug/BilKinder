package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

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

public class WeeklyMenuEditActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;
    private EditText meal1,meal2,meal3,meal4,meal5;
    private Teacher teacher;
    BottomNavigationView bottomNavigationView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_menu_edit_page);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);





        meal1 = findViewById(R.id.editMeal1);
        meal2 = findViewById(R.id.editMeal2);
        meal3 = findViewById(R.id.editMeal3);
        meal4 = findViewById(R.id.editMeal4);
        meal5 = findViewById(R.id.editMeal5);

        String mealOne = meal1.getText().toString();
        String mealTwo = meal2.getText().toString();
        String mealThree =meal3.getText().toString();
        String mealFour = meal4.getText().toString();
        String mealFive = meal5.getText().toString();

        setData(mealOne,mealTwo,mealThree,mealFour,mealFive);



    }

    public void setData(String meal1, String meal2, String meal3, String meal4, String meal5){
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teacher = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                HashMap<String,String> menu = teacher.getWeeklyMenu();
                menu.put("Monday",meal1);
                menu.put("Tuesday",meal2);
                menu.put("Wednesday",meal3);
                menu.put("Thursday",meal4);
                menu.put("Friday",meal5);
                teacher.setWeeklyMenu(menu);
                mData.child("Teachers").child(mUser.getUid()).setValue(teacher);

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
            case R.id.profile:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        startActivity(new Intent(WeeklyMenuEditActivity.this, TeacherProfileActivity.class));
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
                        startActivity(new Intent(WeeklyMenuEditActivity.this, SettingsActivity.class));
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
                        startActivity(new Intent(WeeklyMenuEditActivity.this, TeacherHomeActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }
        return false;
    }
}
