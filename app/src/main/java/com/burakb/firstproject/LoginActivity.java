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

    private EditText txtEMail, txtPassword;
    private Button loginButton, toRegisterButton;
    FirebaseAuth mAuth;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtEMail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);
        toRegisterButton = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinderdata-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String mail = txtEMail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(mail)) {
                    Toast.makeText(LoginActivity.this, "Mail is required", Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Password is required", Toast.LENGTH_LONG).show();
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
                                            // TODO: 10.12.2022 go to student home page if profile data is not set up, else go to starteditprofile page
                                        }
                                        else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                                            Toast.makeText(LoginActivity.this, "Teacher login", Toast.LENGTH_LONG).show();
                                            // TODO: 10.12.2022 go to teacher home page
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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
