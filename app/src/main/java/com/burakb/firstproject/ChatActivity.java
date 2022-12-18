package com.burakb.firstproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    Context context = this;
    RecyclerView recyclerView;
    MessageAdaptor adapter;
    ArrayList<Message> list = new ArrayList<Message>();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        recyclerView = findViewById(R.id.chat_rec_view);
        //initialize list
        Message m = new Message("pavyon");
        list.add(m);
        m = new Message("pide");
        list.add(m);
        m = new Message("etli ekmek");
        list.add(m);
        m = new Message("çük");
        list.add(m);
        m = new Message("pavyon");
        list.add(m);
        m = new Message("pide");
        list.add(m);
        m = new Message("etli ekmek");
        list.add(m);
        m = new Message("çük");
        list.add(m);
        m = new Message("pavyon");
        list.add(m);
        m = new Message("pide");
        list.add(m);
        m = new Message("etli ekmek");
        list.add(m);
        m = new Message("çük");
        list.add(m);






        adapter = new MessageAdaptor(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));





    }
}
