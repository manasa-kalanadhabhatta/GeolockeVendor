package com.geolocke.android.geolockevendor;

import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.geolocke.android.vendorsdk.beans.Geofence;
import com.geolocke.android.vendorsdk.beans.Polygon;
import com.geolocke.android.vendorsdk.beans.Polyline;
import com.geolocke.android.vendorsdk.contentprovider.GeofencesContract;
import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class PolygonalGeofenceTestActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mFragment;

    private ArrayList<Marker> mMarkerArrayList;
    private int mCurMarkerIndex;
    private int mPreviousIndex;

    private ArrayList<Polyline> mPolylineArrayList;
    private LatLng mPointOne;
    private LatLng mPointTwo;

    private Geofence mGeofence;
    private Polygon mPolygon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygonal_geofence_test);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragment = new ChoosePolygonStartPointFragment();
        mFragmentTransaction.add(R.id.fragmentLayout, mFragment).commit();

        mMarkerArrayList = new ArrayList<>();
        mPolylineArrayList = new ArrayList<>();
        mPreviousIndex = -1;
        mCurMarkerIndex = -1;
    }

    @Override
    public void onMapReady(GoogleMap pGoogleMap) {
        mMap = pGoogleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng pLatLng) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(pLatLng).draggable(true));
                mMarkerArrayList.add(marker);
                mCurMarkerIndex = mMarkerArrayList.indexOf(marker);
                Log.d("Info:",mCurMarkerIndex+"");
            }
        });
    }

    public void getFirstPoint(View pView){
        if(mCurMarkerIndex == mPreviousIndex+1) {
            Marker pointMarker = mMarkerArrayList.get(mCurMarkerIndex);
            mPointOne = pointMarker.getPosition();
            pointMarker.setDraggable(false);

            mPreviousIndex = mCurMarkerIndex;
            Log.d("Info:",mPreviousIndex+"");

            Fragment fragment = new ChoosePolygonNextPointFragment();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();
        }else{
            Toast.makeText(getApplicationContext(),"Select one point and try again.", Toast.LENGTH_LONG).show();
            mMarkerArrayList = new ArrayList<>();
            mCurMarkerIndex = -1;
        }
    }

    public void getNextPoint(View pView){
        if(mCurMarkerIndex == mPreviousIndex+1) {
            Marker pointMarker = mMarkerArrayList.get(mCurMarkerIndex);
            mPointTwo = pointMarker.getPosition();
            pointMarker.setDraggable(false);

            mPreviousIndex = mCurMarkerIndex;

            Polyline polyline = new Polyline(mPointOne.latitude, mPointOne.longitude, mPointTwo.latitude, mPointTwo.longitude);
            mPolylineArrayList.add(polyline);

            mMap.addPolyline(new PolylineOptions().add(mPointOne, mPointTwo).width(5).color(Color.RED).geodesic(true));

            Fragment fragment = new ChoosePolygonNextPointFragment();
            Log.i("Changed:", "fragment_choose_next");
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();

            mPointOne = new LatLng(mPointTwo.latitude, mPointTwo.longitude);
        } else {
            Toast.makeText(getApplicationContext(),"Select one point and try again.", Toast.LENGTH_LONG).show();
            for(int i=mMarkerArrayList.size()-1; i>mPreviousIndex; i--){
                mMarkerArrayList.remove(i);
            }
            mCurMarkerIndex=mMarkerArrayList.size()-1;
        }
    }

    public void getLastPoint(View pView){
        Marker pointMarker = mMarkerArrayList.get(mCurMarkerIndex);
        mPointTwo = pointMarker.getPosition();
        pointMarker.setDraggable(false);

        Polyline polyline = new Polyline(mPointOne.latitude, mPointOne.longitude, mPointTwo.latitude, mPointTwo.longitude);
        mPolylineArrayList.add(polyline);

        mMap.addPolyline(new PolylineOptions().add(mPointOne, mPointTwo).width(5).color(Color.RED).geodesic(true));

        mPointOne = mMarkerArrayList.get(0).getPosition();
        polyline = new Polyline(mPointOne.latitude, mPointOne.longitude, mPointTwo.latitude, mPointTwo.longitude);
        mPolylineArrayList.add(polyline);
        mMap.addPolyline(new PolylineOptions().add(mPointOne, mPointTwo).width(5).color(Color.RED).geodesic(true));

        Fragment fragment = new ConfirmPolygonFragment();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();
        Log.i("Changed:","confirm_polygon");
    }

    public void createPolygonalGeofence(View pView){
        try{
            mPolygon = new Polygon(mPolylineArrayList);
            mGeofence = new Geofence(mPolygon);

            Fragment fragment = new GeofenceOptionsFragment();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentLayout,fragment).commit();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void saveGeofence(View pView){
        Uri uri;
        uri = getContentResolver().insert(GeofencesContract.GEOFENCES_CONTENT_URI, mGeofence.getContentValues());
        Log.i("info A:", uri.getLastPathSegment());
        ContentValues contentValues = mPolygon.getContentValues();
        contentValues.put(GeofencesDbHelper.POLYGONS_COL_GEOFENCE_ID, uri.getLastPathSegment());
        uri = getContentResolver().insert(GeofencesContract.POLYGONS_CONTENT_URI, contentValues);
        Log.i("info B:", uri.getLastPathSegment());
        long id = Long.valueOf(uri.getLastPathSegment());
        for(int i= 0; i<mPolylineArrayList.size(); i++){
            contentValues = mPolylineArrayList.get(i).getContentValues();
            contentValues.put(GeofencesDbHelper.POLYLINES_COL_POLYGON_ID, id);
            uri = getContentResolver().insert(GeofencesContract.POLYLINES_CONTENT_URI, contentValues);
            Log.i("info C"+i, uri.getLastPathSegment());
        }

        Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
    }
}
