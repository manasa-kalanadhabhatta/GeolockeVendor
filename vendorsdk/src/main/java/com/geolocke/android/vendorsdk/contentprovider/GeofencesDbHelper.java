package com.geolocke.android.vendorsdk.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Manasa on 31-08-2016.
 */
public class GeofencesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "geofences.db";
    private static final int DATABASE_VERSION = 1;

    public static final String GEOFENCES_TABLE_NAME = "geofences";
    public static final String GEOFENCECIRCLES_TABLE_NAME = "geofencecircles";
    public static final String GEOFENCEPOLYLINES_TABLE_NAME = "geofencepolylines";

    public static final String GEOFENCES_COL_ID =  "_id";
    public static final String GEOFENCES_COL_TYPE =  "type";

    public static final String GEOFENCECIRCLES_COL_ID =  "_id";
    public static final String GEOFENCECIRCLES_COL_MAIN_GEOFENCE_ID =  "geofence_id";
    public static final String GEOFENCECIRCES_COL_TYPE = "type";
    public static final String GEOFENCECIRCLES_COL_CENTRE_LATITUDE =  "centre_latitude";
    public static final String GEOFENCECIRCLES_COL_CENTRE_LONGITUDE =  "centre_longitude";
    public static final String GEOFENCECIRCLES_COL_RADIUS =  "radius";

    public static final String GEOFENCEPOLYLINES_COL_ID = "_id";
    public static final String GEOFENCEPOLYLINES_COL_MAIN_GEOFENCE_ID = "geofence_id";
    public static final String GEOFENCEPOLYLINES_COL_TYPE = "type";
    public static final String GEOFENCEPOLYLINES_COL_LATITUDE_POINT_ONE = "latitude_point_one";
    public static final String GEOFENCEPOLYLINES_COL_LONGITUDE_POINT_ONE = "longitude_point_one";
    public static final String GEOFENCEPOLYLINES_COL_LATITUDE_POINT_TWO = "latitude_point_two";
    public static final String GEOFENCEPOLYLINES_COL_LONGITUDE_POINT_TWO = "longitude_point_two";

    public static final String CREATE_TABLE_GEOFENCES = "create table "
            + GEOFENCES_TABLE_NAME  + "(" +
            GEOFENCES_COL_ID + " integer   primary key autoincrement, " +
            GEOFENCES_COL_TYPE + " text not null " +
            ");";

    public static final String CREATE_TABLE_GEOFENCECIRCLES = "create table "
            + GEOFENCECIRCLES_TABLE_NAME + "(" +
            GEOFENCECIRCLES_COL_ID + " integer   primary key autoincrement, " +
            GEOFENCECIRCLES_COL_MAIN_GEOFENCE_ID + " real not null, " +
            GEOFENCECIRCES_COL_TYPE + " text not null, " +
            GEOFENCECIRCLES_COL_CENTRE_LATITUDE + " real not null, " +
            GEOFENCECIRCLES_COL_CENTRE_LONGITUDE + " real not null, " +
            GEOFENCECIRCLES_COL_RADIUS + " real not null " +
            ");";

    public static final String CREATE_TABLE_GEOFENCEPOLYLINES = "create table "
            + GEOFENCEPOLYLINES_TABLE_NAME + "(" +
            GEOFENCEPOLYLINES_COL_ID + " integer   primary key autoincrement, " +
            GEOFENCEPOLYLINES_COL_MAIN_GEOFENCE_ID + " real not null, " +
            GEOFENCEPOLYLINES_COL_TYPE + " text not null, " +
            GEOFENCEPOLYLINES_COL_LATITUDE_POINT_ONE + " real not null, " +
            GEOFENCEPOLYLINES_COL_LONGITUDE_POINT_ONE + " real not null, " +
            GEOFENCEPOLYLINES_COL_LATITUDE_POINT_TWO + " real not null, " +
            GEOFENCEPOLYLINES_COL_LONGITUDE_POINT_TWO + " real not null " +
            ");";

    public GeofencesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_GEOFENCES);
        database.execSQL(CREATE_TABLE_GEOFENCECIRCLES);
        database.execSQL(CREATE_TABLE_GEOFENCEPOLYLINES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(GeofencesDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + GEOFENCES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GEOFENCECIRCLES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GEOFENCEPOLYLINES_TABLE_NAME);

        onCreate(db);
    }
}
