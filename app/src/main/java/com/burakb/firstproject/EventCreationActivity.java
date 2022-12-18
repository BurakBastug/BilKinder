package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class EventCreationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    EditText title,description;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData, mData2;
    Teacher current;
    BottomNavigationView bottomNavigationView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventcreationpage);

        title = findViewById(R.id.eventname);
        description = findViewById(R.id.eventdetails);
        submit = findViewById(R.id.publishbtn);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Events");
        mData2 = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");


        mData2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = title.getText().toString();
                String eventDetails = description.getText().toString();
                if(TextUtils.isEmpty(eventName)){
                    title.setError("Please enter the title");
                }
                if(TextUtils.isEmpty(eventDetails)){
                    title.setError("Please enter the description");
                }
                else{
                    Event event = new Event(eventName,eventDetails,current.getUsername());
                    mData.child(event.getName()).setValue(event);
                    mData2.child("Teachers").child(mUser.getUid()).child("EventsOfTeacher").child(event.getName()).setValue(event);
                    startActivity(new Intent(EventCreationActivity.this, TeacherHomeActivity.class));
                    Toast.makeText(EventCreationActivity.this, "Event created successfully", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                startActivity(new Intent(EventCreationActivity.this, TeacherProfileActivity.class));
                break;
            case R.id.homee:
                startActivity(new Intent(EventCreationActivity.this, TeacherHomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(EventCreationActivity.this, SettingsActivity.class));
                break;
        }
        return false;
    }
}
