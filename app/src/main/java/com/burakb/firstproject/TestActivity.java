package com.burakb.firstproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    private TextView name;
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        name = findViewById(R.id.textView10);

        String username = "Username not set";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
        }
        System.out.println(username);

        name.setText(username);
    }
}
