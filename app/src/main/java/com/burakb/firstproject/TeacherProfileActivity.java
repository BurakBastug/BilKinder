package com.burakb.firstproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class TeacherProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private ImageView profileImage;
    private TextView txtTeacherName, txtClassAndNumOfStu, txtTeacherAge, txtAddress, txtTeacherContactNum, txtTeacherContactMail;
    private Button editButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;
    FirebaseStorage storage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_profile);

        profileImage = findViewById(R.id.teacherProfilePicture);
        txtTeacherName = findViewById(R.id.teacherName);
        txtClassAndNumOfStu = findViewById(R.id.classAndNumberOfStudent);
        txtTeacherAge = findViewById(R.id.teacherAge);
        txtAddress = findViewById(R.id.teacherAddress);
        txtTeacherContactNum = findViewById(R.id.teacherContactNumber);
        txtTeacherContactMail = findViewById(R.id.teacherContactMail);
        editButton = findViewById(R.id.editButton);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher tmp = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                txtTeacherName.setText(tmp.getUsername());
                txtClassAndNumOfStu.setText("Class and Number of Students: " + tmp.getUsername() + " " + (tmp.getStudentList().size()-1));
                txtTeacherAge.setText("Age: " + tmp.getAge());
                txtAddress.setText("Address: " + tmp.getAddress());
                txtTeacherContactNum.setText("Contact Number: " + tmp.getTelNum());
                txtTeacherContactMail.setText("Contact Mail:" + tmp.getEmail());

                StorageReference ref = storage.getReference().child("images/" + tmp.getImageDestination() + ".jpg");
                if(!tmp.getImageDestination().equals("")) {
                    try {
                        final File localFile = File.createTempFile(tmp.getImageDestination(), "jpg");
                        ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                profileImage.setImageBitmap(bitmap);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherProfileActivity.this, TeacherEditProfileActivity.class));
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.homee:
                startActivity(new Intent(TeacherProfileActivity.this, TeacherHomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(TeacherProfileActivity.this, SettingsActivity.class));
                break;
        }
        return false;
    }
}
