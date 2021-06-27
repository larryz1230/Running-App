package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runningapp.Login.LoginAcitivty;

public class StoreActivity extends AppCompatActivity {

    //    Todo: Austin + Frank: show num credits the user has, and have options to buy items (can hard code})

    TextView storeTitle, creditText;
    Button b1, b2, b3, b4, b5, b6, b7, b8;
    ImageView sItem1; //idk if we need imageview
    int credits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        credits = LoginAcitivty.user.getCredits();

        storeTitle = findViewById(R.id.storeTitle);
        creditText = findViewById(R.id.credits);

        b1 = findViewById(R.id.sButton1);
        b2 = findViewById(R.id.sButton2);
        b3 = findViewById(R.id.sButton3);
        b4 = findViewById(R.id.sButton4);
        b5 = findViewById(R.id.sButton5);
        b6 = findViewById(R.id.sButton6);
        b7 = findViewById(R.id.sButton7);
        b8 = findViewById(R.id.sButton8);

        creditText.setText("Credits: " + credits);
//        bItem1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                credits -= 1; // placeholder values rn
//                LoginAcitivty.user.setCredits(credits);
//                creditText.setText("Credits: " + credits);
//            }
//        });


    }
}