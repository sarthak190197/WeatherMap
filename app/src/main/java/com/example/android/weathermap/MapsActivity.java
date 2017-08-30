package com.example.android.weathermap;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.weathermap.adapters.CustomInfoWindowAdapter;
import com.example.android.weathermap.pojos.City;
import com.example.android.weathermap.pojos.Weather;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Toolbar myToolbar;
    public static Context cxt;
    public static final String TAG = "MapsActivity";



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        SpannableString str = new SpannableString(getTitle());
        str.setSpan(new ForegroundColorSpan(Color.WHITE), 0, getTitle().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        myToolbar.setTitle(str);
        Window window = this.getWindow();
        cxt = getApplicationContext();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        setSupportActionBar(myToolbar);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        //Toast.makeText(this, ""+searchView.getQuery().toString(), Toast.LENGTH_SHORT).show();
        searchView.setQueryHint("City");
        searchView.setBackgroundColor(Color.WHITE);
//        final String[] requestCity = new String[1];
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: ");
                Toast.makeText(MapsActivity.this, "" + searchView.getQuery(), Toast.LENGTH_SHORT).show();
                addMarkertoCity(searchView.getQuery().toString());
                //  requestCity[0] = searchView.getQuery().toString();
                searchItem.collapseActionView();
                searchView.clearFocus();
                searchView.setIconified(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: ");
                // addMarkertoCity(requestCity[0]);
                return false;
            }
        });
        return true;
    }

    private void addMarkertoCity(String cityRequest) {
        // appid=56a0cb4bc6ecf21e2a9eb9ac73172503
        Log.d(TAG, "addMarkertoCity: " + cityRequest);
        String url = null;
        try {
            url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityRequest + "&appid=56a0cb4bc6ecf21e2a9eb9ac73172503";
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Gson gson = new Gson();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "onResponse: " + response.toString());
                            City city = gson.fromJson(response.toString(), City.class);
                            float lat = city.getCoord().getLat();
                            float lon = city.getCoord().getLon();
                            LatLng loc = new LatLng(lat, lon);
                            Log.d(TAG, "onResponse: " + city.getWeather().get(0).getIcon());
                            Weather weather = city.getWeather().get(0);
                            String iconurl = "http://openweathermap.org/img/w/" + weather.getIcon() + ".png";
                            String cityName = city.getName();
                            String description = weather.getDescription();
                            String temp = (city.getMain().getTemp() - 273) + "";
                            Log.d(TAG, "onResponse: " + iconurl);

                            mMap.addMarker(new MarkerOptions().position(loc).title(city.getName()));
                            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this, iconurl, cityName, description, temp));
                            CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(loc, 8);
                            mMap.moveCamera(cu);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(jsonObjectRequest);

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public static Context getContext() {
        return cxt;

    }



}

