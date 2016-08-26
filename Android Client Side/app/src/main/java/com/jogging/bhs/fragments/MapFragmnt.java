package com.jogging.bhs.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.jogging.bhs.R;
import com.jogging.bhs.managers.DirectionsJSONParser;
import com.jogging.bhs.managers.PermissionUtils;
import com.jogging.bhs.managers.SharedPrefsMgr;

import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class MapFragmnt extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMapLongClickListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    int mStackLevel = 0, radious = 1000;
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private Bundle extras;
    private LatLng interestCenterLongLat, CircuitStartLongLat, CircuitEndLongLat;
    private AlertDialog dialog;
    private Circle mapCircle=null;
    private Marker mapMarker = null, mapMarkerStart = null, mapMarkerEnd = null,LastmapMarker=null;
    private  Polyline polyline=null;
    private SharedPrefsMgr spm;
    private String launcher = "";
    private TextView tvDistanceDuration;
    private MaterialStyledDialog materiel_style_dialog;
    private int rythme=7;
    private double distance_meters=0,running_time=0;
    private  Handler handler;
    private RelativeLayout map_relative_layout=null ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        map_relative_layout= (RelativeLayout) findViewById(R.id.map_layout);

        tvDistanceDuration= (TextView) findViewById(R.id.tv_distance_time);
        handler = new Handler();





        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        extras = getIntent().getExtras();
        if (extras != null) {
            launcher = extras.getString("launcher");
        }


    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (launcher.equals("ParametersFragment")) {
            FixingUserParams(mMap);
        } else {
            FixingJoggingCircuit(mMap);
        }


    }

    private void FixingJoggingCircuit(GoogleMap mMap) {
        spm = new SharedPrefsMgr();
        rythme = spm.getIntValue(MapFragmnt.this, "rythm");
        enableMyLocation();
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapLongClickListener(this);

        if (mapMarkerStart==null &&mapMarkerEnd==null) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ShowDialogStartPoint();
                }
            }, 1000);
        }




    }

    private void FixingUserParams(GoogleMap mMap) {
        spm = new SharedPrefsMgr();

        radious = spm.getIntValue(getApplicationContext(), "radious");
        Snackbar.make(map_relative_layout, "Make long click to pick up a location",
                Snackbar.LENGTH_LONG).show();
        if (spm.exists("latitude_interest_center",this) && spm.exists("longitude_interest_center",this))
        {
            LastmapMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng((Double.longBitsToDouble(spm.getLongValue(getApplicationContext(),
                            "latitude_interest_center"))),
                            (Double.longBitsToDouble(spm.getLongValue(getApplicationContext(),
                                    "longitude_interest_center")))))
                    .title("Your Center of interest")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        }

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapLongClickListener(this);
        enableMyLocation();


    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onMyLocationButtonClick() {


        return false;
    }

    @Override
    public void onMapLongClick(LatLng point) {
        if (launcher.equals("ParametersFragment")) {
            ExecutedWhenFixingUserParams(point);
        } else {

            ExecutedWhenFixingCircuit(point);

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void ExecutedWhenFixingCircuit(LatLng point) {
        if (mapMarkerStart != null && mapMarkerEnd != null) {
            mapMarkerStart.remove();
            mapMarkerEnd.remove();
            polyline.remove();
            mapMarkerStart = null;
            mapMarkerEnd = null;
        }
        if (mapMarkerStart == null) {

            mapMarkerStart = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title("Start point")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            CircuitStartLongLat = point;
            if (mapMarkerStart!=null &&mapMarkerEnd==null) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogEndPoint();
                    }
                }, 1000);
            }



        } else {
            mapMarkerEnd = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title("End point")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            showDialogUserFixCircuit();
            CircuitEndLongLat = point;

        }


    }

    private void ExecutedWhenFixingUserParams(LatLng point) {
        interestCenterLongLat = point;

        if (mapCircle != null) {
            mapCircle.remove();
        }
        if (mapMarker != null) {
            mapMarker.remove();
        }
        mapMarker = mMap.addMarker(new MarkerOptions()
                .position(point)
                .title("Center of interest")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mapCircle = mMap.addCircle(new CircleOptions()
                .center(point)
                .radius(radious * 1000)
                .strokeWidth(0f)
                .fillColor(0x550000FF));
        showDialogUserFixParams();




    }

    private void showDialogUserFixParams() {



        materiel_style_dialog=new MaterialStyledDialog(MapFragmnt.this);
        materiel_style_dialog.setTitle("Settings Confirm !")
                .setHeaderColor(R.color.green)
                .setIcon(R.drawable.ic_nearby)
                .setDescription("Do you want to save these settings ?")
                .withIconAnimation(true)
                .withDialogAnimation(true)
                .setCancelable(false)
                .setPositive("Ok", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        spm.setLongValue(getApplicationContext(), "longitude_interest_center",
                                Double.doubleToLongBits(interestCenterLongLat.longitude));
                        spm.setLongValue(getApplicationContext(), "latitude_interest_center",
                                Double.doubleToLongBits(interestCenterLongLat.latitude));
                        dialog.dismiss();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3000);


                    }
                }).setNegative("Cancel", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
               dialog.dismiss();
                mapMarker.remove();
                mapCircle.remove();

            }
        })
                .show();



    }

    private void showDialogUserFixCircuit() {


        materiel_style_dialog=new MaterialStyledDialog(MapFragmnt.this);
        materiel_style_dialog.setTitle("Settings Confirm !")
                .setHeaderColor(R.color.green)
                .setIcon(R.drawable.ic_nearby)
                .setDescription("Do you want to save these settings ?")
                .withIconAnimation(true)
                .withDialogAnimation(true)
                .setCancelable(false)
                .setPositive("Ok", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        spm.setBoolValue(getApplicationContext(),"make_scroll_time",true);


                        spm.setLongValue(getApplicationContext(), "longitude_start_point",
                                Double.doubleToLongBits(CircuitStartLongLat.longitude));
                        spm.setLongValue(getApplicationContext(), "latitude_start_point",
                                Double.doubleToLongBits(CircuitStartLongLat.latitude));
                        spm.setLongValue(getApplicationContext(), "longitude_end_point",
                                Double.doubleToLongBits(CircuitEndLongLat.longitude));
                        spm.setLongValue(getApplicationContext(), "latitude_end_point",
                                Double.doubleToLongBits(CircuitEndLongLat.latitude));

                        String url = getDirectionsUrl(CircuitStartLongLat, CircuitEndLongLat);


                        DownloadTask downloadTask = new DownloadTask();


                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);

                        dialog.dismiss();


                    }
                }).setNegative("Cancel", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                spm.setBoolValue(getApplicationContext(),"make_scroll_time",false);

                dialog.dismiss();
               finish();




            }
        })
                .show();


    }
    private void ShowDialogStartPoint()
    {
        materiel_style_dialog=new MaterialStyledDialog(MapFragmnt.this);
        materiel_style_dialog.setTitle("Starting Point")
                .setHeaderColor(R.color.green)
                .setIcon(R.drawable.ic_nearby)
                .setDescription("Make a long click to choose a starting point ?")
                .withIconAnimation(true)
                .withDialogAnimation(true)
                .setCancelable(false)
                .setPositive("Ok", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {


                        dialog.dismiss();


                    }
                }).show();

    }
    private void ShowDialogEndPoint()
    {
        materiel_style_dialog=new MaterialStyledDialog(MapFragmnt.this);
        materiel_style_dialog.setTitle("Finishing Point ")
                .setHeaderColor(R.color.green)
                .setIcon(R.drawable.ic_nearby)
                .setDescription("Make a long click to choose a Finishing point ?")
                .withIconAnimation(true)
                .withDialogAnimation(true)
                .setCancelable(false)
                .setPositive("Ok", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {


                        dialog.dismiss();


                    }
                }).show();

    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;


    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new SpotsDialog(MapFragmnt.this,R.style.Custom);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

            dialog.dismiss();

        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();



                // Starts parsing data
                routes = parser.parse(jObject);
                distance_meters=parser.getDistance_meters();
                Log.e("distance_meters", String.valueOf(distance_meters));
                running_time=((distance_meters*60)/(rythme*1000) );
                Log.e("distance_meters", String.valueOf(running_time));
                spm.setLongValue(getApplicationContext(), "running_time",
                        Double.doubleToLongBits(running_time));
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process



        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions=null;

            String distance = "";
            String duration = "";
            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0){    // Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(3);
                lineOptions.color(Color.BLUE);
            }
            if (distance != null && duration!=null)
            {
                tvDistanceDuration.setText("Distance:"+distance + ", Driving:"+duration+",Running Time in mins:"+String.format("%.3f", running_time));
            }

            // Drawing polyline in the Google MapFragmnt for the i-th route
            if (lineOptions!=null) {
                polyline = mMap.addPolyline(lineOptions);
            }
            else {
                Toast.makeText(MapFragmnt.this, "Sorry ! Couldn't get Itinerary !", Toast.LENGTH_LONG).show();


            }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();


                    }
                }, 5000);



        }
    }
}
