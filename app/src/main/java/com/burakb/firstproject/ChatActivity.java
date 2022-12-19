package com.burakb.firstproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    Context context = this;
    RecyclerView recyclerView;
    MessageAdaptor adapter;
    ArrayList<Message> list = new ArrayList<Message>();
    Child c;
    Teacher t;
    private FirebaseAuth mAuth;
    private DatabaseReference mData,nData;
    private FirebaseUser mUser;
    private TextView userName;
    private EditText messageInput;
    private Button send;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        recyclerView = findViewById(R.id.chat_rec_view);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        nData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Messages");
        mUser = mAuth.getCurrentUser();

        userName = findViewById(R.id.chat_userName);
        messageInput = findViewById(R.id.message_input);
        send = findViewById(R.id.send_btn);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                    c = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                    for(DataSnapshot teacherObj : snapshot.child("Teachers").getChildren()){
                        Teacher tmpT = teacherObj.getValue(Teacher.class);
                        if(c.getTeacherName().equals(tmpT.getUsername())){
                            t = tmpT;
                            userName.setText(t.getUsername() + "(Your teacher)");
                        }
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
                                if((mes.getSenderMail().equals(c.getEmail()) || mes.getReceiverMail().equals(c.getEmail())) && (mes.getSenderMail().equals(t.getEmail()) || mes.getReceiverMail().equals(t.getEmail()))){
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
                            userName.setText(c.getUsername() + "(Parent Name: " + c.getParentName() + ")");
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
                                if((mes.getSenderMail().equals(c.getEmail()) || mes.getReceiverMail().equals(c.getEmail())) && (mes.getSenderMail().equals(t.getEmail()) || mes.getReceiverMail().equals(t.getEmail()))){
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                    mData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                                startActivity(new Intent(ChatActivity.this, StudentProfileActivity.class));
                            }
                            else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                                startActivity(new Intent(ChatActivity.this, TeacherProfileActivity.class));
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
                                startActivity(new Intent(ChatActivity.this, StudentHomeActivity.class));
                            }
                            else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                                startActivity(new Intent(ChatActivity.this, TeacherHomeActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                break;
            case R.id.settings:
                startActivity(new Intent(ChatActivity.this, SettingsActivity.class));
                break;
        }
        return false;
    }
}
