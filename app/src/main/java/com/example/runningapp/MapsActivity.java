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
import android.os.Parcelable;
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
//    private GoogleMap mMap;
    private Marker selected;
    public static HashMap<String,Marker> hashMapMarker = new HashMap<>();
    private boolean clicked = false;
    private Bitmap institution_icon;
    public static String desc1;

    private static LatLng clickPos;
    //public static Institutions institute;
    public static boolean addedanything = false;
    private static final String TAG = "MapActivity";
    public static List<Marker> AllMarkers = new ArrayList<Marker>();
    private boolean autoclick = false;
    public static boolean notNull = false;
    public static List<Marker> toDelete = new ArrayList<>();
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    public static GoogleMap mMap;
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


    public static ArrayList<POIS> onemirad = new ArrayList<>();



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

        onemirad.add(new POIS("Karl Nordvik Park", "a park", 37.5555, -122.0605, 0.8));
        onemirad.add(new POIS("Patterson Elementary School", "description", 37.561956654969386, -122.03129274411957, 2.0));
        onemirad.add(new POIS("John F Kennedy Elementary School", "description", 37.552601866282345, -122.04221115390615, 1.4));
        onemirad.add(new POIS("James Logan High School", "description", 37.59012841393866, -122.026838044119, 3.7));
        onemirad.add(new POIS("Central Park Dog Park", "description", 37.55249342436547, -121.96680645946422, 6.8));





//        BitmapDrawable bitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.institution_icon);
//        Bitmap b = bitmap.getBitmap();
//        institution_icon = Bitmap.createScaledBitmap(b, bitmapWidth, bitmapHeight, false);

        // Current Location Stuff
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);



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
        LatLng latLng;
        if (LoginAcitivty.user!=null){
             latLng= new LatLng(LoginAcitivty.user.getLat(), LoginAcitivty.user.getLng());
        } else {
             latLng = new LatLng(37.55429832885183, -122.05426336730469);
        }



        CameraUpdate point = CameraUpdateFactory.newLatLng(latLng);
                mMap.moveCamera(point);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));

        mMap.addMarker(new MarkerOptions().position(latLng).title("Starting Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        init();


        for (POIS poi : onemirad){
            Marker marker =  mMap.addMarker(new MarkerOptions().position(poi.getLatLng()).title(poi.getLocationname()));
            hashMapMarker.put(poi.getLocationname(),marker);
            AllMarkers.add(marker);
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            private boolean doit = false;

            @Override
            public boolean onMarkerClick(final  Marker marker) {
                Toast.makeText(MapsActivity.this, "Selected", Toast.LENGTH_SHORT).show();
                clickPos = marker.getPosition();
                clicked = true;
                if(autoclick){
                    marker.remove();
                    autoclick = false;
                }
                for(int j =0; j<onemirad.size(); j++){
                    POIS i = onemirad.get(j);
                    if(i.getLatLng().equals(MapsActivity.getClickPos())){
                        System.out.println(i.getLocationname());
                        Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                        intent.putExtra("lat", i.getLat());
                        intent.putExtra("lng", i.getLng());
                        intent.putExtra("place", i.getLocationname());
                        intent.putExtra("description", i.getDescription());
                        intent.putExtra("distance", i.getDistance());
                        intent.putExtra("index", j);
                        startActivity(intent);
                    }
                }

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
                return clicked;
            }
        });
    }


    public static LatLng getClickPos() {
        return clickPos;
    }

    public static void removeMark(String name){
        Marker marker = hashMapMarker.get(name);
        System.out.println("title "+ marker.getTitle());
        if (marker!=null){
            marker.remove();
            hashMapMarker.remove(name);
            System.out.println("delted");
        }

//        mMap.clear();

    }


    public static void removeAllMarkers() {
        for (Marker mLocationMarker: AllMarkers) {
            mLocationMarker.remove();
        }
        AllMarkers.clear();

    }


}

