package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText userName, password, passwordCheck, email;
    private Button submit;
    private RadioButton studentRatio,teacherRatio;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private ArrayList<Teacher> teacherObjectList = new ArrayList<>();
    private ArrayList<String> teacherUi = new ArrayList<>();
    private Teacher yourTeacher = new Teacher("","","");
    private String teacherUiNumber;
            Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        userName = findViewById(R.id.teacherName);
        password = findViewById(R.id.password);
        passwordCheck = findViewById(R.id.passwordagain);
        email = findViewById(R.id.email);
        submit = findViewById(R.id.submitbtn);
        studentRatio = findViewById(R.id.studentradiobtn);
        teacherRatio = findViewById(R.id.teacherradiobtn);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.schools, R.layout.dropdown_menu_list_items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");

        DatabaseReference teacherReference = mData.child("Teachers");

        teacherReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot teacherObject : snapshot.getChildren()){
                    Teacher teacher = teacherObject.getValue(Teacher.class);
                    teacherObjectList.add(teacher);
                    teacherUi.add(teacherObject.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String mail = email.getText().toString().trim();
        String name = userName.getText().toString().trim();
        String psw = password.getText().toString();
        String pswAgain = passwordCheck.getText().toString();


        if(TextUtils.isEmpty(name)) {
            userName.setError("Full name is required");
        }
        else if(TextUtils.isEmpty(psw) || psw.length() < 6) {
            password.setError("Password is too short");
        }
        else if(TextUtils.isEmpty(pswAgain)) {
            passwordCheck.setError("Enter your password again");
        }
        else if(TextUtils.isEmpty(mail)) {
            email.setError("E-mail is required");
        }
        else if(!(studentRatio.isChecked() || teacherRatio.isChecked())){
            studentRatio.setError("");
            studentRatio.setError("Please select user type");
        }
        else if(!password.getText().toString().equals(passwordCheck.getText().toString()) || TextUtils.isEmpty(psw) ) {
            passwordCheck.setError("Different password");
        }
        else {
            boolean flag = false;

            for(int i = 0; i < teacherObjectList.size(); i++){
                if(name.equalsIgnoreCase(teacherObjectList.get(i).getUsername())){
                    flag = true;
                    yourTeacher = teacherObjectList.get(i);
                    teacherUiNumber = teacherUi.get(i);
                }
            }

            if(!flag && studentRatio.isChecked()){
                userName.setError("Teacher is not registered");
            }
            else{
                mAuth.createUserWithEmailAndPassword(mail, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            if(studentRatio.isChecked()) {
                                Child child = new Child("", mail, psw);
                                child.setTeacherName(name);
                                yourTeacher.addStudent(mAuth.getCurrentUser().getUid());
                                mData.child("Teachers").child(teacherUiNumber).setValue(yourTeacher);
                                mData.child("Students").child(mAuth.getInstance().getCurrentUser().getUid()).setValue(child);
                                startActivity(new Intent(RegisterActivity.this,StartEditProfileActivity.class));
                            }

                            else if(teacherRatio.isChecked()) {
                                Teacher teacher = new Teacher(name, mail, psw);

                                mData.child("Teachers").child(mAuth.getInstance().getCurrentUser().getUid()).setValue(teacher);
                                startActivity(new Intent(RegisterActivity.this,StartEditProfileActivity.class));
                            }
                            Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
