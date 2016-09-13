package com.geolocke.android.vendorsdk.beans;

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
}
