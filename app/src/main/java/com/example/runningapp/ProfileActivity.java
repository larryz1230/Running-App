package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runningapp.Login.LoginAcitivty;

public class ProfileActivity extends AppCompatActivity {

//    Todo: optional (if time)
//    TOdo: functionality: display total hours, name, email, location? idk + num credit, distance, pfp
    TextView fName, lName, email, credits;
    ImageView pfp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fName = findViewById(R.id.fNameP);
        lName = findViewById(R.id.lNameP);
        email = findViewById(R.id.emailP);
        credits = findViewById(R.id.creditsP);
        pfp = findViewById(R.id.pfp);

        User u = LoginAcitivty.user;
        fName.setText(u.getFname());
        lName.setText(u.getLname());
        credits.setText("Credits: "+u.getCredits());
        email.setText(u.getEmail());
    }
}