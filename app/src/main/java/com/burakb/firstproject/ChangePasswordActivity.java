package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    EditText psw1,psw2;
    BottomNavigationView bottomNavigationView;
    Button submit;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;
    String oldPassword;


    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        psw1 = findViewById(R.id.password);
        psw2 = findViewById(R.id.passwordagain);
        submit = findViewById(R.id.submitbtn);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);



        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            User user = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                            //System.out.println(user.getEmail());
                            oldPassword = user.getPassword();
                            //System.out.println(oldPassword);
                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            User user = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                            //System.out.println(user.getEmail());
                            oldPassword = user.getPassword();

                            //System.out.println(oldPassword);

                        }
                        updatePsw(oldPassword);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }

    public void updatePsw(String oldPassword){

        String newInput = psw1.getText().toString();
        String oldInput = psw2.getText().toString();
        System.out.println(newInput);
        System.out.println(oldInput);


        if(TextUtils.isEmpty(oldInput )){
            psw2.setError("Enter a password");
        }
        else if(!oldInput.equals(oldPassword)){
            psw2.setError("Incorrect password");
        }
        else if(TextUtils.isEmpty(newInput)){
            psw1.setError("Enter a password");
        }
        else{
            mData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                        System.out.println("Student");
                        Child child = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                        child.setPassword(newInput);
                        mData.child("Students").child(mUser.getUid()).setValue(child);
                        System.out.println(child.getPassword());
                        //mUser.updatePassword(newInput);


                    }
                    else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                        System.out.println("Teacher");
                        Teacher teacher = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                        teacher.setPassword(newInput);
                        mData.child("Teachers").child(mUser.getUid()).setValue(teacher);
                        System.out.println(teacher.getPassword());
                        //mUser.updatePassword(newInput);

                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mUser.updatePassword(newInput);
            Toast.makeText(ChangePasswordActivity.this, "Password changed", Toast.LENGTH_LONG).show();



        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.homee:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(ChangePasswordActivity.this, StudentHomeActivity.class));
                            //System.out.println("öğrenci");

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(ChangePasswordActivity.this, TeacherHomeActivity.class));
                            //System.out.println("hoca");
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
                        startActivity(new Intent(ChangePasswordActivity.this, SettingsActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            case R.id.profile:
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(ChangePasswordActivity.this, StudentProfileActivity.class));
                            //System.out.println("öğrenci");

                        }
                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                            startActivity(new Intent(ChangePasswordActivity.this, TeacherProfileActivity.class));
                            //System.out.println("hoca");
                        }
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
