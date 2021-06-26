package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreActivity extends AppCompatActivity {

    //    Todo: Austin + Frank: show num credits the user has, and have options to buy items (can hard code})

    TextView storeTitle, creditText;
    Button bItem1;
    ImageView sItem1; //idk if we need imageview
    int credits = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        storeTitle = findViewById(R.id.storeTitle);
        creditText = findViewById(R.id.credits);

        bItem1 = findViewById(R.id.bItem1);
        sItem1 = findViewById(R.id.sItem1);

        creditText.setText("Credits: " + credits);
        bItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credits -= 1; // placeholder values rn
                creditText.setText("Credits: " + credits);
            }
        });


    }
}