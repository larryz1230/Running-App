package com.example.runningapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;


import com.example.runningapp.Login.LoginAcitivty;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private Button tomenu;
    private GoogleMap mMap;
    private Marker selected;
    private boolean clicked = false;
    private Bitmap institution_icon;
    public static String desc1;

    private static LatLng clickPos;
    //public static Institutions institute;
    public static boolean addedanything = false;
    private static final String TAG = "MapActivity";
    private boolean autoclick = false;
    public static boolean notNull = false;
    public static List<Marker> toDelete = new ArrayList<>();
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -58), new LatLng(71, 80));

    // Current Location Stuff
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    LatLng latLngg;


    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps;
    public static Marker marker1;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    //public static  Institutions institute = new Institutions("bob", "farm", "bob@gmail.com", 5, 12345);
    private boolean selectedAnything = false, selectedMarker = false;
    //only one of these will be legit so you always have to check.
    String userr;
    DrawerLayout d1;


    public ArrayList<POIS> onemirad = new ArrayList<>();
    public ArrayList<POIS> threemirad = new ArrayList<>();
    public ArrayList<POIS> fivemirad = new ArrayList<>();



    private boolean hasStarted = false;
    Button refresh;
    private int bitmapWidth = 100, bitmapHeight = 100;
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//        swiper_no_swiping = findViewById(R.id.swipethismuthafucka);

        tomenu = findViewById(R.id.menu);

        tomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });

        onemirad.add(new POIS("Ardenwood Farms", 37.554900, -122.051040));

//        swiper_no_swiping.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getCoordinates();
//                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
//            }
//        });
//        soupKitchen.addLocations(new Institution(soupKitchen, new LatLng(37.775592, -122.433313 ), "We provide food for the San Francisco, California area and provide a wide variety of extravagant cuisine" , "Momma's Food" , "Soup Kitchen"));
//        soupKitchen.addLocations(new Institution(soupKitchen, new LatLng(40.644315, -73.957997) , "We provide food for the New York City, New York area and provide a wide variety of extravagant cuisine" , "Taste From Home" , "Soup Kitchen"));
//        housing.addLocations(new Institution(housing, new LatLng(47.580100, -122.329141) , "We provide affordable housing units for the Seattle, Washington area" , "Affordable Housing First" , "Affordable Housing"));
//        housing.addLocations(new Institution(housing, new LatLng(30.259667, -97.746062), "We provide affordable housing units for the Austin, Texas area" , "Texas Housing" , "Affordable Housing"));
//        donation.addLocations(new Institution(donation, new LatLng(25.773600, -80.214817) , "We provide previously owned items ranging from toys to clothes for the financially unstable people in the Miami, Florida area" , "GoodWish" , "Donation"));
//        donation.addLocations(new Institution(donation, new LatLng(33.446787, -112.077546) , "We provide previously owned items ranging from toys to clothes for the financially unstable people in the Phoenix, Arizona area" , "CitizenLove" , "Donation"));


        //method to get coordinates




//        BitmapDrawable bitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.institution_icon);
//        Bitmap b = bitmap.getBitmap();
//        institution_icon = Bitmap.createScaledBitmap(b, bitmapWidth, bitmapHeight, false);

        // Current Location Stuff
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        client = LocationServices.getFusedLocationProviderClient(this);

        // Check Permission
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When permission granted
            getCurrentLocation();
            client.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            latLngg = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                    });
        } else {
            //When permission denied
            //Request permission
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mSearchText = findViewById(R.id.input_search);
        d1 = findViewById(R.id.d1);




    }



//    private void addCoordinates(final String email, final String coordinate){
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDCOOR,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try{
//                            System.out.println("CONNECTED");
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//
//                            if (success.equals("1")){
//                                Toast.makeText(MapsActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(MapsActivity.this, "Register Error" + e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MapsActivity.this, "Register Error" + error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map <String, String> params = new HashMap<>();
//                params.put("email", email);
//                params.put("coordinate", coordinate);
//                System.out.println("email "+email);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                //When success
                if (location != null) {
                    //Sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Inintialize lat lng
                            latLngg = new LatLng(location.getLatitude()
                                    ,location.getLongitude());

                            System.out.println("latlng " + latLngg.latitude + " " + latLngg.longitude);
                            //Create marker options
                            MarkerOptions options = new MarkerOptions().position(latLngg)
                                    .title("Current Location")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            //Zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngg,7));
                            //Add marker on map
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //When permission granted
                getCurrentLocation();
            }
        }
    }

    private void init(){
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
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
    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
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

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }
    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        autoclick = true;
//        mMap.addMarker(options).showInfoWindow();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LAT_LNG_BOUNDS, 0));

        LatLng latLng = new LatLng(LoginAcitivty.user.getLat(), LoginAcitivty.user.getLng());

        CameraUpdate point = CameraUpdateFactory.newLatLng(latLng);
                mMap.moveCamera(point);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

        mMap.addMarker(new MarkerOptions().position(latLng).title("Starting Location"));

        init();

//        mMap.addMarker(new MarkerOptions().position(onemirad.get(0).getLatLng()).title(onemirad.get(0).getLocationname() + " 5 points"));
//        mMap.addMarker(new MarkerOptions().position(threemirad.get(0).getLatLng()).title(threemirad.get(0).getLocationname() + "20 points"));
//        mMap.addMarker(new MarkerOptions().position(fivemirad.get(0).getLatLng()).title(fivemirad.get(0).getLocationname() + " 40 points"));
//




//        // moves camera to coordinates

//// animates camera to coordinates
//        mMap.animateCamera(point);

//        System.out.println(getClickPos().latitude);


//        for(LatLng l : locCor){
//            LatLng institutePos = l;
//            mMap.addMarker(new MarkerOptions().position(institutePos).title("Institution").icon(BitmapDescriptorFactory.fromBitmap(institution_icon)));
//        }
//        System.out.println(locCor.size());
////        for(Institution i : soupKitchen.getLocations()){
////            LatLng institutePos = i.getPos();
////            mMap.addMarker(new MarkerOptions().position(institutePos).title("Soup Kitchen").icon(BitmapDescriptorFactory.fromBitmap(institution_icon)));
////        }
//        removelocation = findViewById(R.id.remove);
//        if(!(citizen.getEmail().equals("notcitizen"))){
//            removelocation.setVisibility(View.GONE);
//        } else {
//            removelocation.setVisibility(View.VISIBLE);
//        }
//
//
//        // Add a marker in Sydney and move the camera
////        LatLng sydney = new LatLng(-34, 151);
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        if(addedanything) {
//            for (Institution i : institutions.getLocations()) {
//                LatLng institutePos = i.getPos();
//                Marker m = mMap.addMarker(new MarkerOptions().position(institutePos).title("Your Institute").icon(BitmapDescriptorFactory.fromBitmap(institution_icon)));
//                //get coordinates from data
//            }
//        }

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                if(selected != null){
//                    selected.remove();
//                }
//
//                clickPos = latLng;
//                selectedAnything = true;
//
//
//                selected = mMap.addMarker(new MarkerOptions().position(latLng).title(""));
//                clicked = true;
//
//            }
//
//        });

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            private boolean doit = false;
//
//            @Override
//            public boolean onMarkerClick(final  Marker marker) {
//                Institution inst = null;
//                Toast.makeText(MapsActivity.this, "Selected a marker", Toast.LENGTH_SHORT).show();
//                clickPos = marker.getPosition();
//                clicked = true;
//                if(autoclick){
//                    marker.remove();
//                    autoclick = false;
//                }
//                for(Institution i : institutions.getLocations()){
//                    if(i.getPos().equals(MapsActivity.getClickPos())){
//                        inst = i;
//                    }
//                }
//
//                details = findViewById(R.id.details);
//                details.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //marker.getPostition()
//                        // if(description1 == null || type1 == null){
//                        //  Toast.makeText(MapsActivity.this, "Details not available", Toast.LENGTH_SHORT).show();
//                        //   } else {
//                        startActivity(new Intent(MapsActivity.this, DetailsActivity.class));
//                        finish();
//                        return;
//                        //}
//                    }
//
//                });
//
//
//                removelocation.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        marker.remove();
//                        for (Institution i : institutions.getLocations()) {
//                            if(marker.getPosition().equals(i.getPos())){
//                                Toast.makeText(MapsActivity.this, "Removed a location", Toast.LENGTH_SHORT).show();
//                                institutions.getLocations().remove(i);
//                            }
//                        }
//                    }
//                });
//                return clicked;
//            }
//        });


    }
    public static LatLng getClickPos() {
        return clickPos;
    }




}

