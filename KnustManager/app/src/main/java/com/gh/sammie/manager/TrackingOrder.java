package com.gh.sammie.manager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.gh.sammie.manager.Common.Common;
import com.gh.sammie.manager.Common.DirectionJSONParser;
import com.gh.sammie.manager.Remote.IGeoCoordinates;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingOrder extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String TAG = "TrackingOrder";

    private GoogleMap mMap;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private final static int LOCATION_PERMISSION_REQUEST =  1001;

    private Location mLastLocation;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private static int UPDATE_INTERVAL = 1000;
    private static int FASTEST_INTERVAL = 5000;
    private static int DISPLACEMENT = 10;

    private IGeoCoordinates mServices;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_order);

        Log.d(TAG, "onCreate: Tracking order");

        mServices = Common.getGeoCodeService();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestRuntimePermission();

        }
        else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
            }
        }
        
        displayLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestRuntimePermission();

        }
        else
        {
            mLastLocation =  LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null)
            {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();


                //ADD MARKER IN YOUR LOCATION AND MOVE THE CAMERA
                LatLng yourLocation = new LatLng(latitude,longitude);
                mMap.addMarker(new MarkerOptions().position(yourLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));


               drawRoute (yourLocation,Common.currentRequest.getAddress());
            }
            else {
                Toasty.error(this, "Could not get location", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", "Could not get location");

            }

        }

    }

    private void drawRoute(final LatLng yourLocation, String address) {
        mServices.getGeoCode(address).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
              try{
                  JSONObject jsonObject  = new JSONObject(response.body().toString());

                  String lat = ((JSONArray)jsonObject.get("results"))
                          .getJSONObject(0)
                          .getJSONObject("geometry")
                          .getJSONObject("location")
                          .get("lat").toString();


                  String lng = ((JSONArray)jsonObject.get("results"))
                          .getJSONObject(0)
                          .getJSONObject("geometry")
                          .getJSONObject("location")
                          .get("lng").toString();

                  LatLng orderLocation =  new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));

                  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box);
                  bitmap =  Common.scaleBitmap(bitmap,70,70);

//                  MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitmap))
//                          .title("Order of" + Common.currentRequest.getPhone())
//                          .position(orderLocation);
//
//                  mMap.addMarker(marker);

                  //draw route

                  mServices.getDirections(yourLocation.latitude + "," +yourLocation.longitude,
                  orderLocation.latitude+"," + orderLocation.longitude)
                          .enqueue(new Callback<String>() {
                              @Override
                              public void onResponse(Call<String> call, Response<String> response) {
                                  new ParserTask().execute(response.body().toString());

                              }

                              @Override
                              public void onFailure(Call<String> call, Throwable t) {

                              }
                          });



              } catch (JSONException e) {

              }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

     protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();


    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();

            } else
                {
                Toasty.warning(this, "This device is not supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }



    private void requestRuntimePermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {
                       Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                },LOCATION_PERMISSION_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUEST:
                if (grantResults.length  > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
                {
                    if (checkPlayServices())
                    {
                        buildGoogleApiClient();
                        createLocationRequest();

                        displayLocation();
                    }
                }
                break;
        }
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
    }



    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdate();

    }

    private void startLocationUpdate() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
           return;

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();

    }


    private class ParserTask extends AsyncTask<String,Integer,List<List<HashMap<String,String>>>> {
        ProgressDialog mDialog = new ProgressDialog(TrackingOrder.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setMessage("Please waiting ...");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
           mDialog.dismiss();

            ArrayList points = null;
            PolylineOptions lineOptions = null;

            for (int i =0;i<lists.size();i++)
            {
                points =  new ArrayList();
                lineOptions =  new PolylineOptions();

                List<HashMap<String, String>>  path = lists.get(i);

                for (int j = 0; j<path.size();j++ )
                {
                    HashMap<String , String> point = path.get(j);

                    double  lat = Double.parseDouble(point.get("lat"));
                    double  lng =Double.parseDouble(point.get("lng"));

                    LatLng position = new LatLng(lat,lng);

                    points.add(position);


                }
                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.BLUE);
                lineOptions.geodesic(true);



            }
            mMap.addPolyline(lineOptions);

        }

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
           JSONObject jsonObject;
           List<List<HashMap<String, String>>> routes =null;

           try{
               jsonObject =  new JSONObject(strings[0]);
               DirectionJSONParser parser = new DirectionJSONParser();

               routes  = parser.parse(jsonObject);

           } catch (JSONException e) {
               e.printStackTrace();
           }

           return routes;

        }


    }
}
