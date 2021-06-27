package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.runningapp.Login.LoginAcitivty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StoreActivity extends AppCompatActivity {

    //    Todo: Austin + Frank: show num credits the user has, and have options to buy items (can hard code})

    TextView storeTitle, creditText;
    Button bItem1;
    ImageView sItem1; //idk if we need imageview
    int creditsss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        creditsss = LoginAcitivty.user.getCredits();

        storeTitle = findViewById(R.id.storeTitle);
        creditText = findViewById(R.id.credits);



            creditText.setText("Credits: " + LoginAcitivty.user.getCredits());


//        bItem1 = findViewById(R.id.bItem1);
//        sItem1 = findViewById(R.id.sItem1);


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