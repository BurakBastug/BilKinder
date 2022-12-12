package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText txtemail, txtPassword;
    private Button loginButton, toRegisterButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtemail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);
        toRegisterButton = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinderdata-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String mail = txtemail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(mail)) {
                    txtemail.setError("Mail is required");
                }
                if(TextUtils.isEmpty(password)) {
                    txtPassword.setError("Password is required");
                }
                else {
                    mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                mData.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())) {
                                            Toast.makeText(LoginActivity.this, "Student login", Toast.LENGTH_LONG).show();

                                            //finds the location of current user in the database and checks whether the user was initialized or not. teacherName = "" by default
                                            //if there is no info about the child, the app is redirected to StartEditProfileActivity.
                                            //
                                            if(snapshot.child("Students").child(mAuth.getInstance().getCurrentUser().getUid()).child("teacherName").getValue(String.class).equals("")) {
                                                startActivity(new Intent(LoginActivity.this, StartEditProfileActivity.class));
                                            }
                                            else {
                                                startActivity(new Intent(LoginActivity.this, StudentHomeActivity.class));
                                            }
                                        }
                                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                                            Toast.makeText(LoginActivity.this, "Teacher login", Toast.LENGTH_LONG).show();

                                            //finds the location of current user in the database and checks whether the user was initialized or not. age = 0 by default
                                            //if there is no info about the child, the app is redirected to StartEditProfileActivity.

                                            if(snapshot.child("Teachers").child(mAuth.getInstance().getCurrentUser().getUid()).child("age").getValue(Integer.class) == 0) {
                                                startActivity(new Intent(LoginActivity.this, TeacherMenuActivity.class));
                                            }
                                            else {
                                                // TODO: 12.12.2022 start activity to teacher edit page if teacher data is not set up, else go to TeacherHomeActivity 
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else {
                                txtPassword.setError("User not found");
                            }
                        }
                    });
                }
            }
        });

        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
