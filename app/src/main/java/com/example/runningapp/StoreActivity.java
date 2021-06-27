package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    Button b1, b2, b3, b4, b5, b6, b7, b8;
    ImageView sItem1; //idk if we need imageview
    private static String URL_ADD = LoginAcitivty.ngrokID+"/RunningApp/addplace.php";
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

        b1 = findViewById(R.id.sButton1);
        b2 = findViewById(R.id.sButton2);
        b3 = findViewById(R.id.sButton3);
        b4 = findViewById(R.id.sButton4);
        b5 = findViewById(R.id.sButton5);
        b6 = findViewById(R.id.sButton6);
        b7 = findViewById(R.id.sButton7);
        b8 = findViewById(R.id.sButton8);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (creditsss<4000){
                    Toast.makeText(StoreActivity.this, "Not enough credits", Toast.LENGTH_SHORT).show();
                } else {
                    add(4000);
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (creditsss<500){
                    Toast.makeText(StoreActivity.this, "Not enough credits", Toast.LENGTH_SHORT).show();
                } else {
                    add(500);
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (creditsss<800){
                    Toast.makeText(StoreActivity.this, "Not enough credits", Toast.LENGTH_SHORT).show();
                } else {
                    add(800);
                }
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (creditsss<2000){
                    Toast.makeText(StoreActivity.this, "Not enough credits", Toast.LENGTH_SHORT).show();
                } else {
                    add(2000);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (creditsss<5000){
                    Toast.makeText(StoreActivity.this, "Not enough credits", Toast.LENGTH_SHORT).show();
                } else {
                    add(5000);
                }
            }
        });




//        bItem1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                credits -= 1; // placeholder values rn
//                LoginAcitivty.user.setCredits(credits);
//                creditText.setText("Credits: " + credits);
//            }
//        });




    }


    private void add(int sub){
//        mRegister.setVisibility(View.GONE);



        final Double distance = LoginAcitivty.user.getDistance();
        final Integer credits = LoginAcitivty.user.getCredits()-sub;
        final Integer id = LoginAcitivty.user.getId();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");



                            if (success.equals("1")){
                                Toast.makeText(StoreActivity.this, "Congratulations, you have bought an item", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MenuActivity.class));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                            System.out.println(e.toString());
                            Toast.makeText(StoreActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
//                            mRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Toast.makeText(StoreActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
//                        mRegister.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put ("id", id.toString());
                params.put ("credits", credits.toString());
                params.put("distance", distance.toString());

                System.out.println(distance);
                return params;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}