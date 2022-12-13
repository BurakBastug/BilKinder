package com.burakb.firstproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {

    ArrayList<Event> list = new ArrayList<>();
    Event event;
    Context context = this;
    int image;
    RecyclerView recyclerView;
    FeedAdaptor adaptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        recyclerView = findViewById(R.id.rec_view);
        event = new Event("Memati","Usta sen ne dedin",image);
        list.add(event);
        event = new Event("Polat","Ameliyatla Ali olsana",image);
        list.add(event);
        event = new Event("Çakır","Ölüyoruz canpolat",image);
        list.add(event);
        event = new Event("Bulut","Ben sana gülüm demem, gülün ömrü az olur",image);
        list.add(event);
        event = new Event("Memati","Usta sen ne dedin",image);
        list.add(event);
        event = new Event("Polat","Ameliyatla Ali olsana",image);
        list.add(event);
        event = new Event("Çakır","Ölüyoruz canpolat",image);
        list.add(event);
        event = new Event("Bulut","Ben sana gülüm demem, gülün ömrü az olur",image);
        list.add(event);
        event = new Event("Memati","Usta sen ne dedin",image);
        list.add(event);
        event = new Event("Polat","Ameliyatla Ali olsana",image);
        list.add(event);
        event = new Event("Çakır","Ölüyoruz canpolat",image);
        list.add(event);
        event = new Event("Bulut","Ben sana gülüm demem, gülün ömrü az olur",image);
        list.add(event);
        adaptor = new FeedAdaptor(context,list);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));



    }
}
