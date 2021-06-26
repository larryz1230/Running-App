package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.runningapp.Login.LoginAcitivty;

public class MenuActivity extends AppCompatActivity {

    private Button tostore, toprofile, mlogout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        toprofile = findViewById(R.id.toprofile);
        tostore = findViewById(R.id.tostore);
        mlogout = findViewById(R.id.logout);
        textView = findViewById(R.id.welcome);



        tostore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StoreActivity.class));
            }
        });

        toprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginAcitivty.class));
            }
        });

        textView.setText("Welcome "+LoginAcitivty.user.getFname());

    }
}