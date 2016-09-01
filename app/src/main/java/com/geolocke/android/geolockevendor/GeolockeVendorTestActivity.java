package com.geolocke.android.geolockevendor;

import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.geolocke.android.vendorsdk.beans.Geofence;
import com.geolocke.android.vendorsdk.beans.GeofenceCircle;
import com.geolocke.android.vendorsdk.beans.GeofencePolyline;
import com.geolocke.android.vendorsdk.contentprovider.GeofencesContract;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class GeolockeVendorTestActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<Marker> mMarkerArrayList;
    private int mCurMarkerIndex;
    private static int mMarkerCount;
    private com.google.android.gms.maps.model.Circle mCircle;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mFragment;

    public GeofenceCircle mGeofenceCircle;
    public GeofencePolyline mGeofencePolyline;
    public Geofence mGeofence;

    public ArrayList<GeofencePolyline> mGeofencePolylineArrayList;
    public ArrayList<Polyline> mPolylineArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocke_vendor_test);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragment = new ChooseTypeFragment();
        mFragmentTransaction.add(R.id.fragmentLayout, mFragment).commit();

        mMarkerArrayList = new ArrayList<>();
        mMarkerCount = 0;

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
//        LatLng sydney = new LatLng(-34, 151);
//        Marker initialMarker;
//        initialMarker = mMap.addMarker(new MarkerOptions().position(sydney).draggable(true));
//        mMarkerArrayList.add(initialMarker);
//        mMarkerCount++;
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker pMarker) {

            }

            @Override
            public void onMarkerDrag(Marker pMarker) {

            }

            @Override
            public void onMarkerDragEnd(Marker pMarker) {

            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng pLatLng) {
                Marker newMarker = mMap.addMarker(new MarkerOptions().position(pLatLng).draggable(true));
                mMarkerArrayList.add(newMarker);
                mMarkerCount++;
                mCurMarkerIndex = mMarkerArrayList.indexOf(newMarker);
            }
        });
    }

    public void selectCircular(View pView){
        Fragment fragment = new ChooseCircleCentreFragment();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout,fragment).commit();

        mGeofence = new Geofence();
        mGeofence.setType(Geofence.TYPE_CIRCULAR);

        mGeofenceCircle = new GeofenceCircle();
        mGeofenceCircle.setType(Geofence.TYPE_FENCE);
    }

    public void getCentreLatLng(View pView){
        LatLng centre = mMarkerArrayList.get(0).getPosition();
        mMarkerArrayList.get(0).setDraggable(false);
        mGeofenceCircle.setCentreLatitude(centre.latitude);
        mGeofenceCircle.setCentreLongitude(centre.longitude);

        Fragment fragment = new ChooseCircleBoundaryFragment();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout,fragment).commit();

        Marker newMarker = mMap.addMarker(new MarkerOptions().position(centre).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMarkerArrayList.add(newMarker);
        mMarkerCount++;
    }

    public void getBoundaryLatLng(View pView){
        Marker marker = mMarkerArrayList.get(1);
        LatLng boundaryPoint = marker.getPosition();
        //mBoundaryMarker.setDraggable(false);
        marker.remove();
        //mGeofenceCircle.setBoundaryPoint(boundaryPoint);

        Fragment fragment = new ConfirmCircleFragment();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout,fragment).commit();

        double radius = 1000 * haversine(mGeofenceCircle.getCentreLatitude(), mGeofenceCircle.getCentreLongitude(), marker.getPosition().latitude, marker.getPosition().longitude);
        mGeofenceCircle.setRadius(radius);
        mCircle = mMap.addCircle(new CircleOptions().center(new LatLng(mGeofenceCircle.getCentreLatitude(), mGeofenceCircle.getCentreLongitude())).radius(radius).strokeWidth(4));

    }

    public void createCircularGeofence(View pView){
        // store the geofence

        Uri uri;
        ContentValues geofenceContentValues = new ContentValues();
        ContentValues circularGeofenceContentValues = new ContentValues();

        geofenceContentValues = mGeofence.getContentValues();

        uri = getContentResolver().insert(GeofencesContract.GEOFENCES_CONTENT_URI,geofenceContentValues);
        long geofenceId = ContentUris.parseId(uri);

        mGeofenceCircle.setMainGeofenceId(geofenceId);
        circularGeofenceContentValues = mGeofenceCircle.getContentValues();
        uri = getContentResolver().insert(GeofencesContract.GEOFENCECIRCLES_CONTENT_URI,circularGeofenceContentValues);

        Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_LONG).show();
    }

    public void selectPolygonal(View pView){
        Fragment fragment = new ChoosePolygonStartPointFragment();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();
        Log.i("Changed:","Start_point");

        mGeofence = new Geofence();
        mGeofence.setType(Geofence.TYPE_POLYGONAL);
        mGeofencePolylineArrayList = new ArrayList<>();
        mPolylineArrayList = new ArrayList<>();

        mGeofencePolyline = new GeofencePolyline();
        mGeofencePolyline.setType(Geofence.TYPE_FENCE);
    }

    public void getFirstPoint(View pView){
        Fragment fragment = new ChoosePolygonNextPointFragment();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();

        mGeofencePolyline.setLatitudePointOne(mMarkerArrayList.get(mCurMarkerIndex).getPosition().latitude);
        mGeofencePolyline.setLongitudePointOne(mMarkerArrayList.get(mCurMarkerIndex).getPosition().longitude);
    }

    public void getNextPoint(View pView){
        Fragment fragment = new ChoosePolygonNextPointFragment();
        Log.i("Changed:","fragment_choose_next");
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();

        mGeofencePolyline.setLatitudePointTwo(mMarkerArrayList.get(mCurMarkerIndex).getPosition().latitude);
        mGeofencePolyline.setLongitudePointTwo(mMarkerArrayList.get(mCurMarkerIndex).getPosition().longitude);

        long id = mGeofencePolyline.getMainGeofenceId();
        String type = mGeofencePolyline.getType();

        mGeofencePolylineArrayList.add(mGeofencePolyline);

        //add polyline to map
        Polyline line = mMap.addPolyline(new PolylineOptions().add(new LatLng(mGeofencePolyline.getLatitudePointOne(), mGeofencePolyline.getLongitudePointOne()),new LatLng(mGeofencePolyline.getLatitudePointTwo(), mGeofencePolyline.getLongitudePointTwo())).width(5).color(Color.RED));
        mPolylineArrayList.add(line);

        mGeofencePolyline = new GeofencePolyline();
        mGeofencePolyline.setMainGeofenceId(id);
        mGeofencePolyline.setType(type);
        mGeofencePolyline.setLatitudePointOne(mMarkerArrayList.get(mCurMarkerIndex).getPosition().latitude);
        mGeofencePolyline.setLongitudePointOne(mMarkerArrayList.get(mCurMarkerIndex).getPosition().longitude);
    }

    public void getLastPoint(View pView){
        Fragment fragment = new ConfirmPolygonFragment();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();
        Log.i("Changed:","confirm_polygon");

        mGeofencePolyline.setLatitudePointTwo(mMarkerArrayList.get(mCurMarkerIndex).getPosition().latitude);
        mGeofencePolyline.setLongitudePointTwo(mMarkerArrayList.get(mCurMarkerIndex).getPosition().longitude);

        //long id = mGeofencePolyline.getMainGeofenceId();
        String type = mGeofencePolyline.getType();

        mGeofencePolylineArrayList.add(mGeofencePolyline);
        Polyline line = mMap.addPolyline(new PolylineOptions().add(new LatLng(mGeofencePolyline.getLatitudePointOne(), mGeofencePolyline.getLongitudePointOne()),new LatLng(mGeofencePolyline.getLatitudePointTwo(), mGeofencePolyline.getLongitudePointTwo())).width(5).color(Color.RED));
        mPolylineArrayList.add(line);

        mGeofencePolyline = new GeofencePolyline();
        //mGeofencePolyline.setMainGeofenceId(id);
        mGeofencePolyline.setType(type);
        mGeofencePolyline.setLatitudePointOne(mMarkerArrayList.get(0).getPosition().latitude);
        mGeofencePolyline.setLongitudePointOne(mMarkerArrayList.get(0).getPosition().longitude);
        mGeofencePolyline.setLatitudePointTwo(mMarkerArrayList.get(mCurMarkerIndex).getPosition().latitude);
        mGeofencePolyline.setLongitudePointTwo(mMarkerArrayList.get(mCurMarkerIndex).getPosition().longitude);

        mGeofencePolylineArrayList.add(mGeofencePolyline);
        line = mMap.addPolyline(new PolylineOptions().add(new LatLng(mGeofencePolyline.getLatitudePointOne(), mGeofencePolyline.getLongitudePointOne()),new LatLng(mGeofencePolyline.getLatitudePointTwo(), mGeofencePolyline.getLongitudePointTwo())).width(5).color(Color.RED));
        mPolylineArrayList.add(line);
    }

    public void createPolygonalGeofence(View pView){
        Uri uri;
        ContentValues geofenceContentValues = new ContentValues();
        ContentValues geofencePolylineContentValues = new ContentValues();

        geofenceContentValues = mGeofence.getContentValues();

        uri = getContentResolver().insert(GeofencesContract.GEOFENCES_CONTENT_URI,geofenceContentValues);
        long geofenceId = ContentUris.parseId(uri);

        /*mGeofencePo.setMainGeofenceId(geofenceId);
        circularGeofenceContentValues = mGeofenceCircle.getContentValues();*//*
        uri = getContentResolver().insert(GeofencesContract.GEOFENCECIRCLES_CONTENT_URI,circularGeofenceContentValues);
*/
        for(int i=0; i<mGeofencePolylineArrayList.size(); i++){
            GeofencePolyline polyline = mGeofencePolylineArrayList.get(i);
            polyline.setMainGeofenceId(geofenceId);
            geofenceContentValues = polyline.getContentValues();
            uri = getContentResolver().insert(GeofencesContract.GEOFENCEPOLYLINES_CONTENT_URI, geofenceContentValues);
        }

        Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_LONG).show();
    }

    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6372.8 * c;
    }
}
