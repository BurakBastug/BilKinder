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
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, password, passwordCheck, email; //schoolName may be with scrollbar
    private Button submit;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        userName = findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        passwordCheck = findViewById(R.id.passwordagain);
        email = findViewById(R.id.email);
        submit = findViewById(R.id.submitbtn);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String mail = email.getText().toString().trim();
        String name = userName.getText().toString();
        String psw = password.getText().toString();
        String pswAgain = passwordCheck.getText().toString();


        if(TextUtils.isEmpty(name)) {
            Toast.makeText(RegisterActivity.this, "Don't forget entering your name", Toast.LENGTH_LONG).show();
            userName.setError("Full name is required");
            //userName.requestFocus();
        }
        if(TextUtils.isEmpty(psw)) {
            Toast.makeText(RegisterActivity.this, "Don't forget entering your password", Toast.LENGTH_LONG).show();
            password.setError("Password is required");
            //password.requestFocus();
        }
        if(TextUtils.isEmpty(pswAgain)) {
            Toast.makeText(RegisterActivity.this, "Don't forget entering your password again", Toast.LENGTH_LONG).show();
            passwordCheck.setError("Full name is required");
            //passwordCheck.requestFocus();
        }
        if(TextUtils.isEmpty(mail)) {
            Toast.makeText(RegisterActivity.this, "Don't forget entering your email", Toast.LENGTH_LONG).show();
            email.setError("E-mail is required");
            //email.requestFocus();
        }
        if(!password.getText().toString().equals(passwordCheck.getText().toString()) || TextUtils.isEmpty(psw) ) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            passwordCheck.setError("Different password");
            //passwordCheck.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(mail, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        sendUserToNextActivity();
                        Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
