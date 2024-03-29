package com.geolocke.android.geolockevendor.circulargeofence;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.geolocke.android.geolockevendor.R;
import com.geolocke.android.vendorsdk.beans.Circle;
import com.geolocke.android.vendorsdk.beans.Geofence;
import com.geolocke.android.vendorsdk.contentprovider.GeofencesContract;
import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;
import com.geolocke.android.vendorsdk.utils.GeodesicUtilities;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Manasa on 09-09-2016.
 */
public class CircularGeofenceTestActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mFragment;

    private ArrayList<Marker> mMarkerArrayList;
    private int mCurMarkerIndex;

    private LatLng mCentre;
    private double mRadius;

    private Geofence mGeofence;
    private Circle mCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_geofence_test);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragment = new ChooseCircleCentreFragment();
        mFragmentTransaction.add(R.id.fragmentLayout, mFragment).commit();

        mMarkerArrayList = new ArrayList<>();
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
            }
        });
    }

    public void getCentreLatLng (View pView){
        if(mCurMarkerIndex == 0) {
            Marker centreMarker = mMarkerArrayList.get(mCurMarkerIndex);
            mCentre = centreMarker.getPosition();
            centreMarker.setDraggable(false);

            Fragment fragment = new ChooseCircleBoundaryFragment();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();
        } else{
            Toast.makeText(getApplicationContext(),"Select one point and try again.", Toast.LENGTH_LONG).show();
            mMarkerArrayList = new ArrayList<>();
            mCurMarkerIndex = -1;
        }
    }

    public void getBoundaryLatLng (View pView){
        if(mCurMarkerIndex==1) {
            Marker boundaryMarker = mMarkerArrayList.get(mCurMarkerIndex);
            boundaryMarker.setDraggable(false);
            mRadius = 1000 * GeodesicUtilities.getHaversineDistance(mCentre.latitude, mCentre.longitude, boundaryMarker.getPosition().latitude, boundaryMarker.getPosition().longitude);

            Fragment fragment = new ConfirmCircleFragment();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();

            mMap.addCircle(new CircleOptions().center(new LatLng(mCentre.latitude, mCentre.longitude)).radius(mRadius).strokeWidth(4));
        } else{
            Toast.makeText(getApplicationContext(),"Select one point and try again.", Toast.LENGTH_LONG).show();
            for(int i=mMarkerArrayList.size()-1; i>0; i--){
                mMarkerArrayList.remove(i);
            }
            mCurMarkerIndex=0;
        }

    }

    public void createCircularGeofence (View pView){
        EditText editText = (EditText) findViewById(R.id.circleNameTextBox);
        String name = editText.getText().toString();

        mCircle = new Circle(mCentre.latitude, mCentre.longitude, mRadius);
        mGeofence = new Geofence(mCircle, name);

        Fragment fragment = new CircularGeofenceOptionsFragment();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout,fragment).commit();
    }

    public void saveGeofence(View pView){
        Uri uri;
        uri = getContentResolver().insert(GeofencesContract.GEOFENCES_CONTENT_URI, mGeofence.getContentValues());
        Log.i("info A:", uri.getLastPathSegment());
        ContentValues contentValues = mCircle.getContentValues();
        contentValues.put(GeofencesDbHelper.CIRCLES_COL_GEOFENCE_ID, uri.getLastPathSegment());
        uri = getContentResolver().insert(GeofencesContract.CIRCLES_CONTENT_URI, contentValues);
        Log.i("info B:", uri.getLastPathSegment());
        Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
    }
}
