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

public class TeacherEditProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView txtTeacherName, txtClassAndNumberOfStudent;
    private EditText txtAge, txtAddress, txtContactNum, txtContactMail;
    private Button saveButton;

    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    //for photo
    private ImageView profileImage;
    private Uri imagePath;
    private FirebaseStorage storage;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_profile_editing);

        profileImage = findViewById(R.id.teacherProfilePicture);
        txtTeacherName = findViewById(R.id.teacherName);
        txtClassAndNumberOfStudent = findViewById(R.id.classAndNumberOfStudent);
        txtAge = findViewById(R.id.teacherAge);
        txtAddress = findViewById(R.id.teacherAddress);
        txtContactNum = findViewById(R.id.teacherContactNumber);
        txtContactMail = findViewById(R.id.teacherMail);

        saveButton = findViewById(R.id.saveButton);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        mUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();

        txtAge.setInputType(InputType.TYPE_CLASS_NUMBER );
        txtAge.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        txtAge.setSingleLine(true);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        txtContactNum.setInputType(InputType.TYPE_CLASS_NUMBER );
        txtContactNum.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        txtContactNum.setSingleLine(true);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher tmp = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);

                txtTeacherName.setText(tmp.getUsername());
                txtClassAndNumberOfStudent.setText("Class Name: " + tmp.getUsername() + "\nNumber of Students: " + (tmp.getStudentList().size()-1));
                txtAge.setText(tmp.getAge());
                txtAddress.setText(tmp.getAddress());
                txtContactNum.setText(tmp.getTelNum());
                txtContactMail.setText(tmp.getEmail());

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

                if(checkTeacherData())
                    startActivity(new Intent(TeacherEditProfileActivity.this, TeacherHomeActivity.class));
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
                    Toast.makeText(TeacherEditProfileActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
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
                Teacher tmp = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                tmp.setImageDestination(key);
                mData.child("Teachers").child(mUser.getUid()).setValue(tmp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean checkTeacherData() {
        String teacherName = txtTeacherName.getText().toString();
        String age = txtAge.getText().toString();
        String address = txtAddress.getText().toString();
        String contactNum = txtContactNum.getText().toString();
        String contactMail = txtContactMail.getText().toString();
        boolean isEnoughData = true;

        if(TextUtils.isEmpty(teacherName)) {
            txtTeacherName.setError("Teacher name cannot be empty");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(age)) {
            txtAge.setError("Invalid age");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(address)) {
            txtAddress.setError("Address cannot be empty");
            isEnoughData = false;
        }
        if(!User.isCorrectFormOfContactNumber(contactNum)) {
            txtContactNum.setError("Type as the form 0XXXXXXXXXX");
            isEnoughData = false;
        }
        if(TextUtils.isEmpty(contactMail)) {
            txtContactMail.setError("Contact mail cannot be empty");
            isEnoughData = false;
        }
        else {
            mData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Teacher teacher = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
                    teacher.setTeacherData(age, address, contactNum, contactMail);
                    mData.child("Teachers").child(mUser.getUid()).setValue(teacher);
                    Toast.makeText(TeacherEditProfileActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
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
            case R.id.settings:
                if(checkTeacherData()) {
                    mData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            startActivity(new Intent(TeacherEditProfileActivity.this, SettingsActivity.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                break;
            case R.id.homee:
                if(checkTeacherData()) {
                    mData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            startActivity(new Intent(TeacherEditProfileActivity.this, TeacherHomeActivity.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                break;
            case R.id.profile:
                if(checkTeacherData()) {
                    mData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            startActivity(new Intent(TeacherEditProfileActivity.this, TeacherProfileActivity.class));
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
