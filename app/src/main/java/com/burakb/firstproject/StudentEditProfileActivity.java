package com.burakb.firstproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StudentEditProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private EditText txtStuName, txtParentName, txtBloodType, txtContactNum, txtContactMailAddress
            , txtHomeAddress, txtSpecialHealthConditions;
    private TextView txtTeacherName;
    private Button saveButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;

    //for photo
    private ImageView profileImage;
    private Uri imagePath;
    private FirebaseStorage storage;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studenteditpage);

        txtStuName = findViewById(R.id.studentname);
        txtTeacherName = findViewById(R.id.teacherandclass);
        txtParentName = findViewById(R.id.parentname);
        txtBloodType = findViewById(R.id.bloodtype);
        txtContactNum = findViewById(R.id.contactnumber);
        txtContactMailAddress = findViewById(R.id.contactmail);
        txtHomeAddress = findViewById(R.id.address);
        txtSpecialHealthConditions = findViewById(R.id.healthissues);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        saveButton = findViewById(R.id.saveButton);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();

        //for photo
        storage = FirebaseStorage.getInstance();

        profileImage = findViewById(R.id.profilePic);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
        txtContactNum.setInputType(InputType.TYPE_CLASS_NUMBER );
        txtContactNum.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        txtContactNum.setSingleLine(true);

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Child tmp = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                txtStuName.setText(tmp.getUsername());
                txtTeacherName.setText("Teacher name: " + tmp.getTeacherName());
                txtParentName.setText(tmp.getParentName());
                txtBloodType.setText(tmp.getBloodType());
                txtContactNum.setText(tmp.getContactNumber());
                txtContactMailAddress.setText(tmp.getContactMail());
                txtHomeAddress.setText(tmp.getAddress());
                txtSpecialHealthConditions.setText(tmp.getMedicalCondition());

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkStudentData())
                    startActivity(new Intent(StudentEditProfileActivity.this, StudentHomeActivity.class));
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imagePath = data.getData();
            getImageInImageView();
            uploadPicture();
        }
        else {
            Toast.makeText(this, "Error occurred, please try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void getImageInImageView() {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileImage.setImageBitmap(bitmap);
    }

    private void uploadPicture() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();

        FirebaseStorage.getInstance().getReference("images/" + (randomKey + ".jpg")).putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                pd.dismiss();
                if(task.isSuccessful()) {
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()) {
                                updateProfilePicture(randomKey);
                            }
                        }
                    });
                    Snackbar.make(findViewById(android.R.id.content), "Image uploaded", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(StudentEditProfileActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                pd.setMessage("Progress percent: " + (int) progressPercent + "%");
            }
        });
    }

    private void updateProfilePicture(String key) {
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Child tmp = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                tmp.setImageDestination(key);
                mData.child("Students").child(mUser.getUid()).setValue(tmp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean checkStudentData() {
        String studentName = txtStuName.getText().toString();
        String parentName = txtParentName.getText().toString();
        String bloodType = txtBloodType.getText().toString();
        String contactNumber = txtContactNum.getText().toString();
        String contactMail = txtContactMailAddress.getText().toString();
        String address = txtHomeAddress.getText().toString();
        String healthConditions = txtSpecialHealthConditions.getText().toString();
        boolean isEnoughData = true;

        if(TextUtils.isEmpty(studentName)) {
            txtStuName.setError("Student name cannot be empty");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(parentName)) {
            txtParentName.setError("Parent name cannot be empty");
            isEnoughData = false;
        }
        if(!Child.isCorrectFormOfBloodType(bloodType)) { // it can be optional by removing if statement
            txtBloodType.setError("Type like the form ABRh+");
            isEnoughData = false;
        }
        if(!User.isCorrectFormOfContactNumber(contactNumber)) { // it can be optional removing if statement
            txtContactNum.setError("Type as the form 0XXXXXXXXXX");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(contactMail)) { // it can be optional removing if statement
            txtContactMailAddress.setError("Contact mail is required");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(address)) { // it can be optional removing if statement
            txtHomeAddress.setError("Address is required");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(healthConditions)) { // it can be optional removing if statement
            txtSpecialHealthConditions.setError("Health conditions ,s required");
            isEnoughData = false;
        }
        else {
            mData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Child tmp = snapshot.child("Students").child(mUser.getUid()).getValue(Child.class);
                    tmp.setAllData(studentName, parentName, bloodType ,contactNumber, contactMail, address, healthConditions);
                    mData.child("Students").child(mUser.getUid()).setValue(tmp);
                    Toast.makeText(StudentEditProfileActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return isEnoughData;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                if(checkStudentData()) {
                    mData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                                startActivity(new Intent(StudentEditProfileActivity.this, StudentProfileActivity.class));
                            }
                            else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                                startActivity(new Intent(StudentEditProfileActivity.this, TeacherProfileActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                break;
            case R.id.homee:
                if(checkStudentData()) {
                    mData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("Students").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                                startActivity(new Intent(StudentEditProfileActivity.this, StudentHomeActivity.class));
                            }
                            else if(snapshot.child("Teachers").hasChild(mAuth.getInstance().getCurrentUser().getUid())){
                                startActivity(new Intent(StudentEditProfileActivity.this, TeacherHomeActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                break;
            case R.id.settings:
                if(checkStudentData()) {
                    mData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            startActivity(new Intent(StudentEditProfileActivity.this, SettingsActivity.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                break;
        }
        return false;
    }
}
