package com.geolocke.android.vendorsdk.beans;

/**
 * Created by Manasa on 08-09-2016.
 */
public class Circle extends Shape{
    private double mCentreLat;
    private double mCentreLng;
    private double mRadius;

    public Circle (double pCentreLat, double pCentreLng, double pRadius){
        super();
        mType = TYPE_CIRCLE;
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
}
