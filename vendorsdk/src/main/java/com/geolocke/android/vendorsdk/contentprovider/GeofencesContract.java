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
    public static final String CIRCLES_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.circles.circle";
    public static final String CIRCLES_CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.circles.circle";

    public static final Uri CIRCLES_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/circles");

    //geofence polylines
    public static final String POLYLINES_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.polylines.polyline";
    public static final String POLYLINES_CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.polylines.polyline";

    public static final Uri POLYLINES_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/polylines");

    //geofence polygons
    public static final String POLYGONS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.polygons.polygon";
    public static final String POLYGONS_CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.polygons.polygon";

    public static final Uri POLYGONS_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/polygons");

}