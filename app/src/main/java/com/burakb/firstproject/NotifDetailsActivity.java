package com.burakb.firstproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class NotifDetailsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    RecyclerView recyclerView;
    NotifDetailsAdapter adapter;
    BottomNavigationView bottomNavigationView;
    ArrayList<Child> list = new ArrayList<>();
    CheckBox attend;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData,nData;
    Notification searched;
    String notifName;
    Child c;
    ArrayList<String> tempList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif_details);
        recyclerView = findViewById(R.id.notif_details_recview);
        attend = findViewById(R.id.attend_checkbox);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        nData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Notifications");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        notifName = "";
        if(extras != null){
            notifName = extras.getString("notifName");
        }

        nData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot notifObject : snapshot.getChildren()){
                    Notification tmpNotif = notifObject.getValue(Notification.class);
                    if(tmpNotif.getNotifName().equals(notifName)){
                        searched = tmpNotif;
                    }
                }
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            c = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                            for (Child ch : searched.getAllowedList()){
                                if(ch.equals(c)){
                                    attend.setChecked(true);
                                }
                            }

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            attend.setEnabled(false);
                        }
                        attend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if(b){
                                    searched.getAllowedList().add(c);
                                    Toast.makeText(NotifDetailsActivity.this, "Attended", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    int count = 0;
                                    for(int i = 0; i < searched.getAllowedList().size();i++){
                                        if(c.getEmail().equals(searched.getAllowedList().get(i).getEmail())){
                                            count = i;
                                        }
                                    }
                                    searched.getAllowedList().remove(count);
                                    Toast.makeText(NotifDetailsActivity.this, "Not Attended", Toast.LENGTH_LONG).show();
                                }
                                nData.child(searched.getNotifName()).setValue(searched);

                            }
                        });
                        list = searched.getAllowedList();
                        create();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    public void create(){
        adapter = new NotifDetailsAdapter(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(NotifDetailsActivity.this, StudentProfileActivity.class));
                            System.out.println("öğrenci");

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(NotifDetailsActivity.this, TeacherProfileActivity.class));
                            System.out.println("hoca");
                        }
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
                            startActivity(new Intent(NotifDetailsActivity.this, StudentHomeActivity.class));
                            System.out.println("öğrenci");

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(NotifDetailsActivity.this, TeacherHomeActivity.class));
                            System.out.println("hoca");
                        }
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
                        startActivity(new Intent(NotifDetailsActivity.this, SettingsActivity.class));
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
