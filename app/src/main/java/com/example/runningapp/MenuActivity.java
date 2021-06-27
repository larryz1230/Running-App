package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MenuActivity extends AppCompatActivity {

    private Button tostore, toprofile, mlogout;
    private TextView textView;
    private String URL_CREDITS = LoginAcitivty.ngrokID + "/RunningApp/getCreds.php";

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

        getCredits(LoginAcitivty.user.getId());

    }

    private void getCredits (final Integer id){

//            loading.setVisibility(View.VISIBLE);
//            mLogin.setVisibility(View.GONE);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREDITS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int credits = object.getInt("credits");
                                    double distance = object.getDouble("distance");

                                    System.out.println(LoginAcitivty.user.getDistance());
                                    LoginAcitivty.user.setCredits(credits);
                                    LoginAcitivty.user.setDistance(distance);
                                    System.out.println(LoginAcitivty.user.getDistance());

//                                        loading.setVisibility(View.GONE);
                                }
                            } else{
//                                    loading.setVisibility(View.GONE);
//                                      Toast.LENGTH_SHORT).show();
                                Log.e("anyText",response);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                                loading.setVisibility(View.GONE);
//                                mLogin.setVisibility(View.VISIBLE);
                            Log.e("anyText",response);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            loading.setVisibility(View.GONE);
//                            mLogin.setVisibility(View.VISIBLE);

//                        Toast.makeText(MenuActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}