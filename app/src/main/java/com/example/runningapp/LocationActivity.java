package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {
    // TODO: text view displaying locations name, and button

    TextView location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        location = findViewById(R.id.location);

        Intent intent = getIntent();
        String locationname = intent.getStringExtra("place");
        location.setText(locationname);
    }
}