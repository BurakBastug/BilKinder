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

public class FeedActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener{
    BottomNavigationView bottomNavigationView;
    ArrayList<Event> list = new ArrayList<>();
    //Event event;
    Context context = this;
    //int image;
    RecyclerView recyclerView;
    FeedAdaptor adaptor;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mData;
    private DatabaseReference nData;
    Teacher t;
    Child c;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        recyclerView = findViewById(R.id.rec_view);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        nData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Events");
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);

        mData.addValueEventListener(new ValueEventListener() {
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
                for(DataSnapshot eventObject : snapshot.getChildren()){
                    Event tmp = eventObject.getValue(Event.class);
                    if(tmp.getTeacherName().equals(t.getUsername())){
                        list.add(tmp);
                    }
                }
                createFeed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    public void createFeed(){

        adaptor = new FeedAdaptor(context,list);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.profile:
                System.out.println("basıldı");
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(FeedActivity.this, StudentProfileActivity.class));
                            System.out.println("öğrenci");

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(FeedActivity.this, TeacherProfileActivity.class));
                            System.out.println("hoca");
                        }
                        System.out.println("method");

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
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(FeedActivity.this, StudentHomeActivity.class));
                            System.out.println("öğrenci");

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(FeedActivity.this, TeacherHomeActivity.class));
                            System.out.println("hoca");
                        }
                        System.out.println("method");

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
                        startActivity(new Intent(FeedActivity.this, SettingsActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

        }
    }
}
