package com.burakb.firstproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class EventCreationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    EditText title,description;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData, mData2;
    Teacher current;
    BottomNavigationView bottomNavigationView;

    //for photo
    private ImageView eventImage;
    private Uri imagePath;
    Event event = new Event("","","");

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventcreationpage);

        title = findViewById(R.id.eventname);
        description = findViewById(R.id.eventdetails);
        eventImage = findViewById(R.id.eventPhoto);
        submit = findViewById(R.id.publishbtn);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Events");
        mData2 = FirebaseDatabase.getInstance("https://bilkinder2data-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");

        mData2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current = snapshot.child("Teachers").child(mUser.getUid()).getValue(Teacher.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = title.getText().toString();
                String eventDetails = description.getText().toString();
                if(TextUtils.isEmpty(eventName)){
                    title.setError("Please enter the title");
                }
                if(TextUtils.isEmpty(eventDetails)){
                    description.setError("Please enter the description");
                }
                if(!(TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(description.getText().toString()))){
                    String randomDatabaseDest = UUID.randomUUID().toString();
                    event.setName(eventName);
                    event.setDescription(eventDetails);
                    event.setEventDestination(randomDatabaseDest);
                    event.setTeacherName(current.getUsername());
                    mData.child(randomDatabaseDest).setValue(event);
                    mData2.child("Teachers").child(mUser.getUid()).child("EventsOfTeacher").child(event.getName()).setValue(event);
                    startActivity(new Intent(EventCreationActivity.this, TeacherHomeActivity.class));
                    Toast.makeText(EventCreationActivity.this, "Event created successfully", Toast.LENGTH_LONG).show();
                }
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
        eventImage.setImageBitmap(bitmap);
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
                                updateEventPictureDestination(randomKey);
                            }
                        }
                    });
                    Snackbar.make(findViewById(android.R.id.content), "Image uploaded", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(EventCreationActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
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

    private void updateEventPictureDestination(String randomKey) {
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                event.setImageDestination(randomKey);
                mData.child("Events").child(event.getEventDestination()).setValue(event);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profile:
                startActivity(new Intent(EventCreationActivity.this, TeacherProfileActivity.class));
                break;
            case R.id.homee:
                startActivity(new Intent(EventCreationActivity.this, TeacherHomeActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(EventCreationActivity.this, SettingsActivity.class));
                break;
        }
        return false;
    }
}
