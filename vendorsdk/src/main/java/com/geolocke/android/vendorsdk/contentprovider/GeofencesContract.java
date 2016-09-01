package com.geolocke.android.vendorsdk.contentprovider;

import android.net.Uri;

/**
 * Created by Manasa on 31-08-2016.
 */
public class GeofencesContract {

    public static final String AUTHORITY = "com.geolocke.android.vendorsdk.geofences.provider";

    //main geofence
    public static final String GEOFENCES_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.geofences.geofence";
    public static final String GEOFENCES_CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.geofences.geofence";

    public static final Uri GEOFENCES_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/geofences");

    //geofence circles
    public static final String GEOFENCECIRCLES_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.geofences.geofencecircle";
    public static final String GEOFENCECIRCLES_CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.geofences.geofencecircle";

    public static final Uri GEOFENCECIRCLES_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/geofencecircles");

    //geofence polylines
    public static final String GEOFENCEPOLYLINES_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.geofences.geofencepolyline";
    public static final String GEOFENCEPOLYLINES_CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.geofences.geofencepolyline";

    public static final Uri GEOFENCEPOLYLINES_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/geofencepolylines");

}