package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
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

public class NotifCreationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData,nData;
    EditText title,description;
    Button submit;
    Teacher teacher;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_creation);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        nData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Notifications");

        title = findViewById(R.id.postName);
        description = findViewById(R.id.postDescription);
        submit = findViewById(R.id.postThePost);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teacher = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                createNotif();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createNotif(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleStr = title.getText().toString();
                String descriptionStr = description.getText().toString();
                if(titleStr.equals("")){
                    title.setError("Please enter a title");
                }
                if(descriptionStr.equals("")){
                    description.setError("Please enter a description");
                }
                if(!(title.getText().toString().equals("") || description.getText().toString().equals(""))) {
                    Notification notif = new Notification(titleStr,descriptionStr,teacher.getUsername());
                    nData.child(notif.getNotifName()).setValue(notif);
                    mData.child("Teachers").child(mUser.getUid()).setValue(teacher);
                    Toast.makeText(NotifCreationActivity.this, "Notification created", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(NotifCreationActivity.this,TeacherHomeActivity.class));
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                startActivity(new Intent(NotifCreationActivity.this, TeacherProfileActivity.class));
                break;
            case R.id.homee:
                startActivity(new Intent(NotifCreationActivity.this, TeacherHomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(NotifCreationActivity.this, SettingsActivity.class));
                break;
        }
        return false;
    }
}
