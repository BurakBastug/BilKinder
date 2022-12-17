package com.burakb.firstproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class NotificationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    RecyclerView recyclerView;
    Notification_adapter adapter;
    BottomNavigationView bottomNavigationView;
    ArrayList<Notification> list = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData,nData;
    Teacher t;
    Child c;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        recyclerView = findViewById(R.id.notification_recycler_view);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        nData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Notifications");

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                    c = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                    for(DataSnapshot teacherObject : snapshot.child("Teachers").getChildren()){
                        Teacher tmp = teacherObject.getValue(Teacher.class);
                        if(c.getTeacherName().equals(tmp.getUsername())){
                            t = tmp;
                        }
                    }

                }
                else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                    t = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot notifObjects : snapshot.getChildren()){
                    Notification tmpNotif = notifObjects.getValue(Notification.class);
                    if(tmpNotif.getTeacher().equals(t.getUsername())){
                        list.add(tmpNotif);
                    }
                }
                createFeed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //implement the list of notifications this is sample list with the simple constructor(not original constructor)





    }

    public void createFeed(){
        adapter = new Notification_adapter(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
