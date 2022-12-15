package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EventCreationActivity extends AppCompatActivity {

    EditText title,description;
    Button openNavBar, navBarReturnHome, navBarProfile, submit;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData, mData2;
    Teacher current;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventcreationpage);

        title = findViewById(R.id.eventname);
        description = findViewById(R.id.eventdetails);
        submit = findViewById(R.id.publishbtn);
        //openNavBar = findViewById(R.id.barhomebtn);
        //navBarProfile = findViewById(R.id.barprofilebtn);
        //navBarReturnHome = findViewById(R.id.barhomebtn);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Events");





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
                    Event event = new Event(eventName,eventDetails);
                    mData.child(event.getName()).setValue(event);
                    startActivity(new Intent(EventCreationActivity.this, TeacherHomeActivity.class));
                    Toast.makeText(EventCreationActivity.this, "Event created successfully", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
