package com.example.runningapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.runningapp.MapsActivity;
import com.example.runningapp.PlaceAutocompleteAdapter;
import com.example.runningapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class RegisterActivity extends AppCompatActivity {
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -58), new LatLng(71, 80));

    //    TODO: larry

    Button mLogin, mRegister, button;
    EditText fname, lname, email, password;
    private static String URL_REGIST = LoginAcitivty.ngrokID+"/RunningApp/register.php";
    private AutoCompleteTextView mSearchText;
    private static final String TAG = "RegisterActivity";

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;

//    private static String URL_REGIST = "https://127.0.0.1/RunningApp/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mLogin = findViewById(R.id.tologin);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mRegister = findViewById(R.id.register);


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginAcitivty.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                init();
            }
        });


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().length()<1){
                    fname.setError("Please enter name");
                    return;
                }
                if (lname.getText().toString().length()<1){
                    lname.setError("Please enter name");
                    return;
                }
                if (email.getText().toString().length()<1){
                    email.setError("Please enter email");
                    return;
                }
                if (password.getText().toString().length()<1){
                    password.setError("Please enter password");
                    return;
                }
                //creates new row in database
                Regist();
            }
        });

    }



    private void Regist(){
//        mRegister.setVisibility(View.GONE);



        final String fname = this.fname.getText().toString().trim();
        final String lname = this.lname.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");



                            if (success.equals("1")){
                                Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginAcitivty.class));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                            System.out.println(e.toString());
                            Toast.makeText(RegisterActivity.this, "Register Error" + e.toString(), Toast.LENGTH_SHORT).show();
                            mRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Toast.makeText(RegisterActivity.this, "Register Error" + error.toString(), Toast.LENGTH_SHORT).show();
                        mRegister.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put ("fname", fname);
                params.put ("lname", lname);
                params.put("email", email);
                params.put("password", password);

                System.out.println(fname + " " + lname);
                return params;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(RegisterActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

//            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
//                    address.getAddressLine(0));
        }
    }

    public void init() {
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }

        });

    }
}