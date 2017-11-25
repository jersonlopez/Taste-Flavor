package co.edu.udea.compumovil.gr04_20172.proyecto.map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import co.edu.udea.compumovil.gr04_20172.proyecto.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private double lat = 6.266953;
    private double lng = -75.569111;
    private double latIn, lngIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        latIn = Double.parseDouble(getIntent().getStringExtra("latitude"));
        lngIn = Double.parseDouble(getIntent().getStringExtra("longitude"));

        //Toast.makeText(getApplication(), lat+" "+lng, Toast.LENGTH_SHORT).show();

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        myUbication();
    }

    private void addMarker(double lat, double lng) {
        LatLng coordinates = new LatLng(lat, lng);
        LatLng coordinatesIn = new LatLng(latIn,lngIn);
        float zoom = 15;
        CameraUpdate myUbication = CameraUpdateFactory.newLatLngZoom(coordinates, zoom);
        if (marker != null) marker.remove();
        marker = mMap.addMarker(new MarkerOptions().position(coordinates).title("Mi Ubicación")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        marker = mMap.addMarker(new MarkerOptions().position(coordinatesIn).title("Restaurante")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.animateCamera(myUbication);
        //mMap.addPolyline(new PolylineOptions().add(coordinates, coordinatesIn).width(5).color(Color.BLUE));
        DownloadRouteAndDraw downloadRouteAndDraw;
        downloadRouteAndDraw = new DownloadRouteAndDraw(mMap);
        downloadRouteAndDraw.execute(getDirectionsUrl(coordinates, coordinatesIn, "driving"));
    }

    private void updateUbication(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            //Toast.makeText(getApplication(), lat+" "+lng, Toast.LENGTH_SHORT).show();
            addMarker(lat, lng);
        }else{
            Toast.makeText(this, "Por favor encienda el GPS", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateUbication(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void myUbication() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplication(), "no hay permisos", Toast.LENGTH_SHORT).show();
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateUbication(location);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0/*BIND_ABOVE_CLIENT*/, 0, locationListener);

    }

    private String getDirectionsUrl(LatLng origin, LatLng dest, String streetMode) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        String mode = "mode=" + streetMode;

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }
}
