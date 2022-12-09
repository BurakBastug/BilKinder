package com.burakb.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartEditProfileActivity extends AppCompatActivity {

    private Button toEditProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starteditingpage);

        toEditProfileButton = findViewById(R.id.startbtn);

        toEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the user is not teacher
                startActivity(new Intent(StartEditProfileActivity.this, EditProfileActivity.class));
            }
        });
    }
}
