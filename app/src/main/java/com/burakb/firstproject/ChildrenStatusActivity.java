package com.burakb.firstproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChildrenStatusActivity extends AppCompatActivity {

    Context context = this;
    RecyclerView recyclerView;
    Children_status_adapter adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.children_status);
        recyclerView = findViewById(R.id.rec_view_children_status);
        //Initialize your student list, I will initialize a list in order to try layout
        ArrayList<Child> list = new ArrayList<Child>();
        list.add(new Child("burak","123","djjd"));
        list.add(new Child("berkin","123","djjd"));
        list.add(new Child("arda","123","djjd"));
        list.add(new Child("göktuğ","123","djjd"));
        adapter = new Children_status_adapter(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}
