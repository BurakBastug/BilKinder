package com.burakb.firstproject;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    Context context = this;
    RecyclerView recyclerView;
    MessageAdaptor adapter;
    ArrayList<Message> list = new ArrayList<Message>();
    Child c;
    Teacher t;
    private FirebaseAuth mAuth;
    private DatabaseReference mData,nData;
    private FirebaseUser mUser;
    private EditText messageInput;
    private Button send;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        recyclerView = findViewById(R.id.chat_rec_view);
        //initialize list
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        nData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Messages");
        mUser = mAuth.getCurrentUser();

        messageInput = findViewById(R.id.message_input);
        send = findViewById(R.id.send_btn);


        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                    c = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                    for(DataSnapshot teacherObj : snapshot.child("Teachers").getChildren()){
                        Teacher tmpT = teacherObj.getValue(Teacher.class);
                        if(c.getTeacherName().equals(tmpT.getUsername())){
                            t = tmpT;
                        }
                        //System.out.println(t.getEmail());
                    }
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String content = messageInput.getText().toString();
                            if(!TextUtils.isEmpty(content)){
                                Message message = new Message(c.getEmail(),t.getEmail(),content);
                                nData.child(UUID.randomUUID().toString()).setValue(message);
                                messageInput.setText("");
                            }
                        }
                    });
                    nData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            for(DataSnapshot messsageObject : snapshot.getChildren()){
                                Message mes = messsageObject.getValue(Message.class);
                                if(mes.getSenderMail().equals(c.getEmail()) || mes.getReceiverMail().equals(c.getEmail())){
                                    list.add(mes);
                                }
                            }
                            Collections.sort(list);
                            adapter = new MessageAdaptor(context,list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                    t = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                    String studentID = "";
                    Bundle extras = getIntent().getExtras();
                    if(extras != null){
                        studentID = extras.getString("studentID");
                    }
                    for(DataSnapshot targetChild : snapshot.child("Students").getChildren()){
                        Child tmp = targetChild.getValue(Child.class);
                        if(studentID.equals(tmp.getEmail())){
                            c = tmp;
                        }
                    }
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String content = messageInput.getText().toString();
                            if(!TextUtils.isEmpty(content)){
                                Message message = new Message(t.getEmail(),c.getEmail(),content);
                                nData.child(UUID.randomUUID().toString()).setValue(message);
                                messageInput.setText("");
                            }
                        }
                    });
                    nData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            for(DataSnapshot messsageObject : snapshot.getChildren()){
                                Message mes = messsageObject.getValue(Message.class);
                                if(mes.getSenderMail().equals(t.getEmail()) || mes.getReceiverMail().equals(t.getEmail())){
                                    list.add(mes);
                                }
                            }

                            Collections.sort(list);
                            adapter = new MessageAdaptor(context,list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });













    }
}
