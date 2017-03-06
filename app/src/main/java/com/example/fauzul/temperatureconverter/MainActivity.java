package com.example.fauzul.temperatureconverter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static com.example.fauzul.temperatureconverter.R.id.inputF;
import static com.example.fauzul.temperatureconverter.R.id.inputC;
import static com.example.fauzul.temperatureconverter.R.id.inputK;
import static com.example.fauzul.temperatureconverter.R.id.inputR;



public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    private EditText textF;
    private EditText textC;
    private EditText textK;
    private EditText textR;
    private String data;
    private double latitude;
    private double longitude;
    private int state;
    private CallWeather weather;
    private boolean textChanger;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gets the EditText fields for local access
        textF = (EditText) findViewById(inputF);
        textC = (EditText) findViewById(inputC);
        textK = (EditText) findViewById(inputK);
        textR = (EditText) findViewById(inputR);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        //Sets listeners based on touch so that the application knows where the input is occurring
        //States 0, 1, 2, 3 respectively for F,C,K,R
        //If in State 0, input is occurring in F
        textF.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                state = 0;
                return false;
            }
        });

        textC.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                state = 1;
                return false;
            }
        });

        textK.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                state = 2;
                return false;
            }
        });

        textR.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                state = 3;
                return false;
            }
        });


        //Text Listeners to change all text fields based on input in one

        //For Fahrenheit field
        textF.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }


            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (state == 0) {
                    if (s.length()  != 0 && !s.toString().equals("-"))  {
                        textK.setText(s);
                        float inputVal = Float.parseFloat(textF.getText().toString());
                        textC.setText(String.valueOf(Converter.convertFtoC(inputVal)));
                        textK.setText(String.valueOf(Converter.convertFtoK(inputVal)));
                        textR.setText(String.valueOf(Converter.convertFtoR(inputVal)));
                    }
                    else {
                        textC.setText("");
                        textK.setText("");
                        textR.setText("");
                    }
                }
            }
        });

        //For Celsius field
        textC.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }


            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(state == 1)  {
                    if (s.length()  != 0 && !s.toString().equals("-"))  {
                        float inputVal = Float.parseFloat(textC.getText().toString());
                        textF.setText(String.valueOf(Converter.convertCtoF(inputVal)));
                        textK.setText(String.valueOf(Converter.convertCtoK(inputVal)));
                        textR.setText(String.valueOf(Converter.convertCtoR(inputVal)));
                    }
                    else {
                        textF.setText("");
                        textK.setText("");
                        textR.setText("");
                    }
                }
            }
        });

        //For Kelvin field
        textK.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }


            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(state == 2)  {
                    if (s.length()  != 0 && !s.toString().equals("-"))  {
                        float inputVal = Float.parseFloat(textK.getText().toString());
                        textF.setText(String.valueOf(Converter.convertKtoF(inputVal)));
                        textC.setText(String.valueOf(Converter.convertKtoC(inputVal)));
                        textR.setText(String.valueOf(Converter.convertKtoR(inputVal)));
                    }
                    else {
                        textF.setText("");
                        textC.setText("");
                        textR.setText("");
                    }
                }
            }
        });

        //for Rankine field
        textR.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }


            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(state == 3)  {
                    if (s.length()  != 0 && !s.toString().equals("-"))  {
                        float inputVal = Float.parseFloat(textR.getText().toString());
                        textF.setText(String.valueOf(Converter.convertRtoF(inputVal)));
                        textC.setText(String.valueOf(Converter.convertRtoC(inputVal)));
                        textK.setText(String.valueOf(Converter.convertRtoK(inputVal)));
                    }
                    else {
                        textF.setText("");
                        textC.setText("");
                        textK.setText("");
                    }
                }
            }
        });

        //calls on OpenWeatherMap's API
        weather = new CallWeather();
        weather.execute();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static interface LocationCallback {
        public void onNewLocationAvailable(double longitude, double latitude);
    }

    public void onClick(View view) throws JSONException, IOException {
        switch (view.getId()) {
            //Reset button
            case R.id.button1:
                textF.setText("");
                textC.setText("");
                textK.setText("");
                textR.setText("");
                break;
            //Locate button
            case R.id.button2:
                //checks if location permission is granted
                if (ContextCompat.checkSelfPermission(this,
                        ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    //location is available via GPS which would be more accurate
                    if(isGPSEnabled)    {
                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_FINE);
                        locationManager.requestSingleUpdate(criteria, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                            }
                            @Override public void onStatusChanged(String provider, int status, Bundle extras) { }
                            @Override public void onProviderEnabled(String provider) { }
                            @Override public void onProviderDisabled(String provider) { }
                        }, null);
                        display();
                        //Toast.makeText(this, "Latitude: " + latitude + "\nLongitude: " + longitude + "\nGPS Detected", Toast.LENGTH_LONG).show();
                    }
                    //Location services available via network which is less accurate
                    else if(isNetworkEnabled && !isGPSEnabled)    {
                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                        locationManager.requestSingleUpdate(criteria, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                            }
                            @Override public void onStatusChanged(String provider, int status, Bundle extras) { }
                            @Override public void onProviderEnabled(String provider) { }
                            @Override public void onProviderDisabled(String provider) { }
                        }, null);
                        display();
                        //Toast.makeText(this, "Latitude: " + latitude + "\nLongitude: " + longitude + "\nNetwork Detected", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this, "Location Not Detected", Toast.LENGTH_LONG).show();
                    }
                }
                else   {
                    //requests location permission
                    if (ContextCompat.checkSelfPermission(this,
                            ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this,
                                new String[]{ACCESS_FINE_LOCATION},
                                10);
                    }
                }
            break;
        }
    }

    public void display() throws JSONException {
        weather = new CallWeather();
        weather.execute();
        if(data != null)    {
            JSONObject jObj = new JSONObject(data);

            JSONObject coordObj = jObj.getJSONObject("coord");
            double tmpLat = (double) coordObj.getDouble("lat");
            double tmpLon = (double) coordObj.getDouble("lon");

            if(tmpLat != 0 && tmpLon != 0) {
                JSONObject mainObj = jObj.getJSONObject("main");
                float temp = (float) mainObj.getDouble("temp");
                if (state == 0) {
                    textF.setText(String.valueOf(Converter.convertKtoF(temp)));
                }
                if (state == 1) {
                    textC.setText(String.valueOf(Converter.convertKtoC(temp)));
                }
                if (state == 2) {
                    textK.setText(String.valueOf(temp));
                }
                if (state == 3) {
                    textR.setText(String.valueOf(Converter.convertKtoR(temp)));
                }
            }
            else    {
                Toast.makeText(this, "Obtaining Location", Toast.LENGTH_LONG).show();
            }

        }
    }

    private class CallWeather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            data = ((new WeatherClient()).getWeatherData(latitude, longitude));
            return data;
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void onConnected(Bundle connectionHint) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latitude=mLastLocation.getLatitude();
            longitude=mLastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


}