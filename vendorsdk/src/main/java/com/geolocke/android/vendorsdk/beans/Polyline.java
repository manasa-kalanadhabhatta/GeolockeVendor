package com.geolocke.android.vendorsdk.beans;

import android.content.ContentValues;

import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;

/**
 * Created by Manasa on 08-09-2016.
 */
public class Polyline {
    double mPointOneLat;
    double mPointOneLng;
    double mPointTwoLat;
    double mPointTwoLng;

    public Polyline(double pPointOneLat, double pPointOneLng, double pPointTwoLat, double pPointTwoLng) {
        mPointOneLat = pPointOneLat;
        mPointOneLng = pPointOneLng;
        mPointTwoLat = pPointTwoLat;
        mPointTwoLng = pPointTwoLng;
    }

    public double getPointOneLat() {
        return mPointOneLat;
    }

    public double getPointOneLng() {
        return mPointOneLng;
    }

    public double getPointTwoLat() {
        return mPointTwoLat;
    }

    public double getPointTwoLng() {
        return mPointTwoLng;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GeofencesDbHelper.POLYLINES_COL_LATITUDE_POINT_ONE, this.getPointOneLat());
        contentValues.put(GeofencesDbHelper.POLYLINES_COL_LONGITUDE_POINT_ONE, this.getPointOneLng());
        contentValues.put(GeofencesDbHelper.POLYLINES_COL_LATITUDE_POINT_TWO, this.getPointTwoLat());
        contentValues.put(GeofencesDbHelper.POLYLINES_COL_LONGITUDE_POINT_TWO, this.getPointTwoLng());
        return contentValues;
    }
}
