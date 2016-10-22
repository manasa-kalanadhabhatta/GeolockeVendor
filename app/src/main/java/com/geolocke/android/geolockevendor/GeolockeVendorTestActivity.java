package com.geolocke.android.geolockevendor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.geolocke.android.geolockevendor.circulargeofence.CircularGeofenceTestActivity;
import com.geolocke.android.geolockevendor.contentproviderdemo.ContentProviderDemoActivity;
import com.geolocke.android.geolockevendor.polygonalgeofence.PolygonalGeofenceTestActivity;

public class GeolockeVendorTestActivity extends AppCompatActivity {
    Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocke_vendor_test);
    }

    public void selectCircularGeofenceTest(View pView){
        mIntent = new Intent(this,CircularGeofenceTestActivity.class);
        startActivity(mIntent);
    }

    public void selectPolygonalGeofenceTest(View pView){
        mIntent = new Intent(this,PolygonalGeofenceTestActivity.class);
        startActivity(mIntent);
    }

    public void selectContentProviderDemo(View pView){
        mIntent = new Intent(this,ContentProviderDemoActivity.class);
        startActivity(mIntent);
    }
}
