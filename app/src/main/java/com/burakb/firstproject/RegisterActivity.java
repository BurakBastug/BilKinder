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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

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
        submit.setOnClickListener(this);
    }

    private void registerUser() {
        //FirebaseAuth auth = FirebaseAuth.getInstance();
        /*auth.createUserWithEmailAndPassword(mail, psw)*/

        String mail = email.getText().toString().trim();
        String name = userName.getText().toString();
        String psw = password.getText().toString();
        String pswAgain = passwordCheck.getText().toString();
        //System.out.println(name);


        if(TextUtils.isEmpty(name)) {
            Toast.makeText(RegisterActivity.this, "Don't forget entering your name", Toast.LENGTH_LONG).show();
            //System.out.println("asfasgasgasgasgasfg");
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

        }




    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.submitbtn){
            registerUser();
        }
    }
}
