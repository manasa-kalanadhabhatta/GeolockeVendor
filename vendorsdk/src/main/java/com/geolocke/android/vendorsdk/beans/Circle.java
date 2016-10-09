package com.geolocke.android.vendorsdk.beans;

import android.content.ContentValues;

import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;

/**
 * Created by Manasa on 08-09-2016.
 */
public class Circle extends Shape{
    private double mCentreLat;
    private double mCentreLng;
    private double mRadius;

    public Circle (double pCentreLat, double pCentreLng, double pRadius){
        super();
        mShapeType = SHAPE_TYPE_CIRCLE;
        mCentreLat = pCentreLat;
        mCentreLng = pCentreLng;
        mRadius = pRadius;
    }

    public double getCentreLat() {
        return mCentreLat;
    }

    public double getCentreLng() {
        return mCentreLng;
    }

    public double getRadius() {
        return mRadius;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GeofencesDbHelper.CIRCLES_COL_CENTRE_LATITUDE, this.getCentreLat());
        contentValues.put(GeofencesDbHelper.CIRCLES_COL_CENTRE_LONGITUDE, this.getCentreLng());
        contentValues.put(GeofencesDbHelper.CIRCLES_COL_RADIUS, this.getRadius());
        contentValues.put(GeofencesDbHelper.CIRCLES_COL_CIRCLE_TYPE, this.getType());
        return contentValues;
    }
}
