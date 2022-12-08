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

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, password, passwordCheck, email; //schoolName may be with scrollbar
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        userName = findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        passwordCheck = findViewById(R.id.passwordagain);
        email = findViewById(R.id.email);

        submit = findViewById(R.id.submitbtn);
        submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = userName.getText().toString();
            String psw = password.getText().toString();
            String passwordControl = passwordCheck.getText().toString();
            String mail = email.getText().toString();

            if(TextUtils.isEmpty(name)) {
                Toast.makeText(RegisterActivity.this, "Don't forget entering your name", Toast.LENGTH_LONG).show();
                userName.setError("Full name is required");
                userName.requestFocus();
            }
            else if(TextUtils.isEmpty(psw)) {
                Toast.makeText(RegisterActivity.this, "Don't forget entering your password", Toast.LENGTH_LONG).show();
                password.setError("Password is required");
                password.requestFocus();
            }
            else if(TextUtils.isEmpty(passwordControl)) {
                Toast.makeText(RegisterActivity.this, "Don't forget entering your password again", Toast.LENGTH_LONG).show();
                passwordCheck.setError("Full name is required");
                passwordCheck.requestFocus();
            }
            else if(TextUtils.isEmpty(mail)) {
                Toast.makeText(RegisterActivity.this, "Don't forget entering your school name", Toast.LENGTH_LONG).show();
                email.setError("School name is required");
                email.requestFocus();
            }
            else if(!password.equals(passwordCheck)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                passwordCheck.setError("Different password");
                passwordCheck.requestFocus();
            }
            else {
                registerUser(name, mail, psw);
            }
        }
    });
    }

    private void registerUser(String name, String mail, String psw) {
        //FirebaseAuth auth = FirebaseAuth.getInstance();
        /*auth.createUserWithEmailAndPassword(mail, psw)*/

        User user = new User(name, mail, psw);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().setValue(user).
        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                    FirebaseUser user = auth.getCurrentUser();

                    user.sendEmailVerification();
                    //Intent intent = new Intent(RegisterActivity.this, );
                }
            }

        });
    }
}
