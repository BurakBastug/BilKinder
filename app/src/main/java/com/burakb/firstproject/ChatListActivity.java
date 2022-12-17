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

public class ChatListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    Context context = this;
    RecyclerView recyclerView;
    ChatListAdapter adapter;
    BottomNavigationView bottomNavigationView;
    ArrayList<Child> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        recyclerView = findViewById(R.id.privateChat);
        //implement the list of notifications this is sample list with the simple constructor(not original constructor)
        Child ch = new Child("name","dsds","dsfdsf");
        list.add(ch);
        ch = new Child("name","dsds","dsfdsf");
        list.add(ch);
        ch = new Child("name","dsds","dsfdsf");
        list.add(ch);

        adapter = new ChatListAdapter(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
