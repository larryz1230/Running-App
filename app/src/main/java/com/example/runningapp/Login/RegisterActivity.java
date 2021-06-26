package com.example.runningapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.runningapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RegisterActivity extends AppCompatActivity {

    //    TODO: larry

    Button mLogin, mRegister;
    EditText fname, lname, email, password;
    private static String URL_REGIST = LoginAcitivty.ngrokID+"/RunningApp/register.php";

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




    private void Registt(){
//        loading.setVisibility(View.VISIBLE);
        mRegister.setVisibility(View.GONE);

        final String fname = this.fname.getText().toString().trim();
        final String lname = this.lname.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
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
                            Toast.makeText(RegisterActivity.this, "Register Error" + e.toString(), Toast.LENGTH_SHORT).show();
//                            loading.setVisibility(View.GONE);
                            mRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register Error" + error.toString(), Toast.LENGTH_SHORT).show();
//                        loading.setVisibility(View.GONE);
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
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}