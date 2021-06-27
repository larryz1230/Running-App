package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LocationActivity extends AppCompatActivity {
    // TODO: text view displaying locations name, and button

    private FusedLocationProviderClient fusedLocationClient;
    private FusedLocationProviderClient client;
    private GoogleApiClient mGoogleApiClient;

    TextView location, distance, desc, creds, categ;
    ImageView imageview;
    Integer numcreds;
    String category;
    Double distancee;
    String locationname;
    int index;
    Button check, submit;
    private static String URL_ADD = LoginAcitivty.ngrokID+"/RunningApp/addplace.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        location = findViewById(R.id.location);
        distance = findViewById(R.id.distance);
        desc = findViewById(R.id.desc);
        imageview = findViewById(R.id.imageView);
        check = findViewById(R.id.button);
        submit = findViewById(R.id.submit);

        categ = findViewById(R.id.categ);
        creds = findViewById(R.id.creditss);
        Intent intent = getIntent();
        locationname = intent.getStringExtra("place");
         distancee = intent.getDoubleExtra("distance", 0.00);
        String descr = intent.getStringExtra("description");
        index = intent.getIntExtra("index", 0);

        distance.setText(distancee.toString() + " miles");
        desc.setText(descr);
        location.setText(locationname);

        if (distancee<2.0){
            numcreds = 5;
            category = "Short";
        } else if (distancee<4.0){
            numcreds = 20;
            category = "Medium";
        } else{
            numcreds = 50;
            category = "Long";
        }
        creds.setText(numcreds.toString() + " Credits");
        categ.setText("Category: " + category);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        client = LocationServices.getFusedLocationProviderClient(this);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO: go to database;
                add();

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 0:
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);

                    imageview.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                }

                break;
        }


    }



    private void add(){
//        mRegister.setVisibility(View.GONE);



        final Double distance = LoginAcitivty.user.getDistance() + distancee;
        final Integer credits = LoginAcitivty.user.getCredits() + numcreds;
        final Integer id = LoginAcitivty.user.getId();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");



                            if (success.equals("1")){
                                Toast.makeText(LocationActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//                                TODO: delete marker from map.
//                                MapsActivity.removeMark(locationname);
//                                System.out.println("index "+index);
//                                MapsActivity.removeAllMarkers();
                                startActivity(new Intent(getApplicationContext(), MapsActivity.class));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                            System.out.println(e.toString());
                            Toast.makeText(LocationActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
//                            mRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Toast.makeText(LocationActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
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