package com.burakb.firstproject;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class NotifDetailsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    RecyclerView recyclerView;
    NotifDetailsAdapter adapter;
    BottomNavigationView bottomNavigationView;
    ArrayList<Child> list = new ArrayList<>();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif_details);
        recyclerView = findViewById(R.id.notif_details_recview);

        //Implement the list here. This is just a test list
        Child c = new Child("a","b","c");
        list.add(c);
        c = new Child("a","b","c");
        list.add(c);
        c = new Child("a","b","c");
        list.add(c);







        adapter = new NotifDetailsAdapter(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
