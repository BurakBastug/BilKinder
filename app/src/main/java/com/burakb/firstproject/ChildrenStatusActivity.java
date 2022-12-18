package com.burakb.firstproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChildrenStatusActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, Children_status_adapter.RecyclerViewInterface {

    Context context = this;
    RecyclerView recyclerView;
    Children_status_adapter adapter;
    BottomNavigationView bottomNavigationView;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData;
    Teacher current;
    ArrayList<Child> list = new ArrayList<Child>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.children_status);
        recyclerView = findViewById(R.id.rec_view_children_status);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");

        //Initialize your student list, I will initialize a list in order to try layout
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                int count = 0;
                for(String id : current.getStudentList()){
                    Child child = snapshot.child("Students").child(id).getValue(Child.class);
                    if(count != 0 ){
                        list.add(child);
                    }
                    count++;
                }
                create();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void create(){
        adapter = new Children_status_adapter(context, list, new ChildrenStatusItemListener() {
            @Override
            public void onItemClick(Child child) {
                Toast.makeText(ChildrenStatusActivity.this, child.getUsername(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ChildrenStatusActivity.this,StudentProfileForTeachersActivity.class);
                intent.putExtra("username", child.getUsername());
                intent.putExtra("parentName",child.getParentName());
                intent.putExtra("contactNumber",child.getContactNumber());
                intent.putExtra("bloodType",child.getBloodType());
                intent.putExtra("address",child.getAddress());
                intent.putExtra("healthIssues",child.getMedicalCondition());
                intent.putExtra("contactMail",child.getContactMail());
                intent.putExtra("teacherName",child.getTeacherName());

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                startActivity(new Intent(ChildrenStatusActivity.this, TeacherProfileActivity.class));
                break;
            case R.id.homee:
                startActivity(new Intent(ChildrenStatusActivity.this, TeacherHomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(ChildrenStatusActivity.this, SettingsActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ChildrenStatusActivity.this,StudentProfileActivity.class);
        intent.putExtra("Name",list.get(position).getUsername());
        intent.putExtra("Contact Number",list.get(position).getContactNumber());
        intent.putExtra("Parent Name:",list.get(position).getParentName());
        startActivity(intent);

    }
}
