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

public class NotificationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    RecyclerView recyclerView;
    Notification_adapter adapter;
    BottomNavigationView bottomNavigationView;
    ArrayList<Notification> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.notification_recycler_view);

        //implement the list of notifications



    }
    public void create(){
        adapter = new Notification_adapter(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
