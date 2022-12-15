package com.burakb.firstproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.UUID;

public class StudentEditProfileActivity extends AppCompatActivity {

    private EditText txtStuName, txtParentName, txtBloodType, txtContactNum, txtContactMailAddress
            , txtHomeAddress, txtSpecialHealthConditions;
    private TextView txtTeacherName;
    private Button saveButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private FirebaseUser mUser;

    //for photo
    private ImageView profileImg;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference ref;


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

        saveButton = findViewById(R.id.saveButton);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();
        //for photo
        storage = FirebaseStorage.getInstance();
        ref = storage.getReference();

        profileImg = findViewById(R.id.profilePic);
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        txtContactNum.setInputType(InputType.TYPE_CLASS_NUMBER );
        txtContactNum.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        txtContactNum.setSingleLine(true);

        mData.addValueEventListener(new ValueEventListener() {
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

                ref.getFile(tmp.getImageUrl()).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        profileImg.setImageURI(tmp.getImageUrl());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData())
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
        if(requestCode == 1 /*&& requestCode == RESULT_OK && data != null && data.getData() != null*/) {
            imageUri = data.getData();
            profileImg.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference r = ref.child("Images/" + randomKey);
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User tmp = snapshot.child("Students").child(mUser.getUid()).getValue(User.class);
                tmp.setImageDestination(randomKey);
                tmp.setImageUrl(imageUri);
                mData.child("Students").child(mUser.getUid()).setValue(tmp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        r.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image uploaded", Snackbar.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                pd.dismiss();
                double progressPercent = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                pd.setMessage("Progress: " + (int) progressPercent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentEditProfileActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkData() {
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
}
