package com.burakb.firstproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class StudentProfileForTeachersActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView studentName,teacherAndClass,parentName,bloodType,contactNumber,contactMail,addresss,healthIssues;
    private ImageView studentProfilePic;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData;
    FirebaseStorage storage;
    BottomNavigationView bottomNavigationView;


    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stdent_profile_for_teachers);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        storage = FirebaseStorage.getInstance();

        studentName = findViewById(R.id.studentName);
        teacherAndClass = findViewById(R.id.teacherandclass);
        parentName = findViewById(R.id.parentName);
        bloodType = findViewById(R.id.bloodType);
        contactNumber = findViewById(R.id.contactNumber);
        contactMail = findViewById(R.id.contactMail);
        addresss = findViewById(R.id.address);
        healthIssues = findViewById(R.id.healthIssues);
        studentProfilePic = findViewById(R.id.profilePhoto);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        String username = "";
        String teacherandclass = "";
        String parentname = "";
        String bloodtype = "";
        String contactnumber = "";
        String contactmail = "";
        String address = "";
        String healthissues = "";
        //String imageDestination = "";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
            parentname = extras.getString("parentName");
            contactnumber = extras.getString("contactNumber");
            bloodtype = extras.getString("bloodType");
            address = extras.getString("address");
            contactmail = extras.getString("contactMail");
            teacherandclass = extras.getString("teacherName");
            healthissues = extras.getString("healthIssues");
            //imageDestination = extras.getString("imageDestination");
        }

        studentName.setText(username);
        teacherAndClass.setText(teacherandclass);
        parentName.setText(parentname);
        bloodType.setText(bloodtype);
        contactNumber.setText(contactnumber);
        contactMail.setText(contactmail);
        addresss.setText(address);
        healthIssues.setText(healthissues);

        /*StorageReference ref = storage.getReference().child("images/" + imageDestination + ".jpg");
        System.out.println(imageDestination);
        try {
            final File localFile = File.createTempFile(imageDestination, "jpg");
            ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    studentProfilePic.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                startActivity(new Intent(StudentProfileForTeachersActivity.this, TeacherProfileActivity.class));
                break;
            case R.id.homee:
                startActivity(new Intent(StudentProfileForTeachersActivity.this, TeacherHomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(StudentProfileForTeachersActivity.this, SettingsActivity.class));
                break;

        }
        return false;
    }
}
