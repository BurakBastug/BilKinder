package com.burakb.firstproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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

public class ChatListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ChildrenStatusItemListener{

    Context context = this;
    RecyclerView recyclerView;
    ChatListAdapter adapter;
    BottomNavigationView bottomNavigationView;
    ArrayList<Child> list = new ArrayList<>();
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;
    Teacher t;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        recyclerView = findViewById(R.id.privateChat);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                int count = 0;
                for(String childStr : t.getStudentList()){
                    if(count != 0){
                        Child c = snapshot.child("Students").child(childStr).getValue(Child.class);
                        list.add(c);
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
        adapter = new ChatListAdapter(context, list, new ChildrenStatusItemListener() {
            @Override
            public void onItemClick(Child child) {
                Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                intent.putExtra("studentID",child.getEmail());
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
                startActivity(new Intent(ChatListActivity.this, TeacherProfileActivity.class));
                break;
            case R.id.homee:
                startActivity(new Intent(ChatListActivity.this, TeacherHomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(ChatListActivity.this, SettingsActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(Child child) {

    }
}
