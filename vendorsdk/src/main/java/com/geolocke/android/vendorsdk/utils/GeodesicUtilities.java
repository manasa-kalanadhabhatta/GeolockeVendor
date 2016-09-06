package com.geolocke.android.vendorsdk.utils;

/**
 * Created by Manasa on 06-09-2016.
 */
public class GeodesicUtilities {

    public static double getHaversineDistance(double pSourceLatitude, double pSourceLongitude, double pDestinationLatitude, double pDestinationLongitude) {
        double latitudeDifference = Math.toRadians(pDestinationLatitude - pSourceLatitude);
        double longitudeDifference = Math.toRadians(pDestinationLongitude - pSourceLongitude);
        pSourceLatitude = Math.toRadians(pSourceLatitude);
        pDestinationLatitude = Math.toRadians(pDestinationLatitude);

        double a = Math.pow(Math.sin(latitudeDifference / 2),2) + Math.pow(Math.sin(longitudeDifference / 2),2) * Math.cos(pSourceLatitude) * Math.cos(pDestinationLatitude);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6372.8 * c;
    }
}
