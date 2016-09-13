package com.geolocke.android.geolockevendor;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.geolocke.android.vendorsdk.beans.Geofence;
import com.geolocke.android.vendorsdk.beans.Polygon;
import com.geolocke.android.vendorsdk.beans.Polyline;
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

    public void getFirstPoint(View pView){
        Marker pointMarker = mMarkerArrayList.get(mCurMarkerIndex);
        mPointOne = pointMarker.getPosition();
        pointMarker.setDraggable(false);

        Fragment fragment = new ChoosePolygonNextPointFragment();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();
    }

    public void getNextPoint(View pView){
        Marker pointMarker = mMarkerArrayList.get(mCurMarkerIndex);
        mPointTwo = pointMarker.getPosition();
        pointMarker.setDraggable(false);

        Polyline polyline = new Polyline(mPointOne.latitude, mPointOne.longitude, mPointTwo.latitude, mPointTwo.longitude);
        mPolylineArrayList.add(polyline);

        mMap.addPolyline(new PolylineOptions().add(mPointOne, mPointTwo).width(5).color(Color.RED).geodesic(true));

        Fragment fragment = new ChoosePolygonNextPointFragment();
        Log.i("Changed:","fragment_choose_next");
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentLayout, fragment).commit();

        mPointOne = new LatLng(mPointTwo.latitude, mPointTwo.longitude);
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
        Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_SHORT).show();
        // TODO: 09-09-2016 save geofence
    }
}
