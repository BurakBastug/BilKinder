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
    private DatabaseReference mData;
    private DatabaseReference nData;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        recyclerView = findViewById(R.id.rec_view);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Events");
        nData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Event event = snap.getValue(Event.class);
                    list.add(event);
                }
                createFeed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    public void createFeed(){
        System.out.println(list);
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
                nData.addValueEventListener(new ValueEventListener() {
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
                nData.addValueEventListener(new ValueEventListener() {
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
                nData.addValueEventListener(new ValueEventListener() {
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
