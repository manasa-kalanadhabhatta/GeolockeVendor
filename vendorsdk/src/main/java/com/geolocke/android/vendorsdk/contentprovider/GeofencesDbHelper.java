package com.geolocke.android.vendorsdk.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Manasa on 31-08-2016.
 */
public class GeofencesDbHelper extends SQLiteOpenHelper {

    //definitions for database
    private static final String DATABASE_NAME = "geofences.db";
    private static final int DATABASE_VERSION = 1;

    //definitions for table names
    public static final String GEOFENCES_TABLE_NAME = "geofences";
    public static final String CIRCLES_TABLE_NAME = "circles";
    public static final String POLYLINES_TABLE_NAME = "polylines";
    public static final String POLYGONS_TABLE_NAME = "polygons";

    //definitions for 'geofences' table
    public static final String GEOFENCES_COL_ID =  "_id";
    public static final String GEOFENCES_COL_GEOFENCE_NAME = "geofence_name";
    public static final String GEOFENCES_COL_FENCE_TYPE =  "fence_type";
    public static final String GEOFENCES_COL_IS_COMPLEX = "is_complex";

    //definitions for 'circles' table
    public static final String CIRCLES_COL_ID =  "_id";
    public static final String CIRCLES_COL_CENTRE_LATITUDE =  "centre_latitude";
    public static final String CIRCLES_COL_CENTRE_LONGITUDE =  "centre_longitude";
    public static final String CIRCLES_COL_RADIUS =  "radius";
    public static final String CIRCLES_COL_CIRCLE_TYPE = "circle_type";
    public static final String CIRCLES_COL_GEOFENCE_ID =  "geofence_id";

    //definitions for 'polylines' table
    public static final String POLYLINES_COL_ID = "_id";
    //public static final String GEOFENCEPOLYLINES_COL_MAIN_GEOFENCE_ID = "geofence_id";
    public static final String POLYLINES_COL_LATITUDE_POINT_ONE = "latitude_point_one";
    public static final String POLYLINES_COL_LONGITUDE_POINT_ONE = "longitude_point_one";
    public static final String POLYLINES_COL_LATITUDE_POINT_TWO = "latitude_point_two";
    public static final String POLYLINES_COL_LONGITUDE_POINT_TWO = "longitude_point_two";
    //public static final String POLYLINES_COL_TYPE = "type";
    public static final String POLYLINES_COL_POLYGON_ID = "polygon_id";

    //definitions for 'polygons' table
    public static final String POLYGONS_COL_ID = "_id";
    public static final String POLYGONS_COL_POLYGON_TYPE = "polygon_type";
    public static final String POLYGONS_COL_GEOFENCE_ID = "geofence_id";

    public static final String CREATE_TABLE_GEOFENCES = "create table "
            + GEOFENCES_TABLE_NAME  + "(" +
            GEOFENCES_COL_ID + " integer   primary key autoincrement, " +
            GEOFENCES_COL_GEOFENCE_NAME + " text not null, " +
            GEOFENCES_COL_FENCE_TYPE + " text not null, " +
            GEOFENCES_COL_IS_COMPLEX + " text not null " +
            ");";

    public static final String CREATE_TABLE_CIRCLES = "create table "
            + CIRCLES_TABLE_NAME + "(" +
            CIRCLES_COL_ID + " integer   primary key autoincrement, " +
            CIRCLES_COL_CENTRE_LATITUDE + " real not null, " +
            CIRCLES_COL_CENTRE_LONGITUDE + " real not null, " +
            CIRCLES_COL_RADIUS + " real not null, " +
            CIRCLES_COL_CIRCLE_TYPE + " text not null, " +
            CIRCLES_COL_GEOFENCE_ID + " real not null, " +
            "foreign key (" + CIRCLES_COL_GEOFENCE_ID + ") " +
            "references " + GEOFENCES_TABLE_NAME + "(" + GEOFENCES_COL_ID + ") " +
            "on delete cascade " +
            ");";

    public static final String CREATE_TABLE_POLYLINES = "create table "
            + POLYLINES_TABLE_NAME + "(" +
            POLYLINES_COL_ID + " integer   primary key autoincrement, " +
            POLYLINES_COL_LATITUDE_POINT_ONE + " real not null, " +
            POLYLINES_COL_LONGITUDE_POINT_ONE + " real not null, " +
            POLYLINES_COL_LATITUDE_POINT_TWO + " real not null, " +
            POLYLINES_COL_LONGITUDE_POINT_TWO + " real not null, " +
            POLYLINES_COL_POLYGON_ID + " real not null, " +
            "foreign key (" + POLYLINES_COL_POLYGON_ID + ") " +
            "references " + POLYGONS_TABLE_NAME + "(" + POLYGONS_COL_ID + ") " +
            "on delete cascade " +
            ");";

    public static final String CREATE_TABLE_POLYGONS = "create table "
            + POLYGONS_TABLE_NAME + "(" +
            POLYGONS_COL_ID + " integer   primary key autoincrement, " +
            POLYGONS_COL_POLYGON_TYPE +  " text not null, " +
            POLYGONS_COL_GEOFENCE_ID +  " real not null, " +
            "foreign key (" + POLYGONS_COL_GEOFENCE_ID + ") " +
            "references " + GEOFENCES_TABLE_NAME + "(" + GEOFENCES_COL_ID + ") " +
            "on delete cascade " +
            ");";

    public GeofencesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("PRAGMA foreign_keys=ON;");
        database.execSQL(CREATE_TABLE_GEOFENCES);
        database.execSQL(CREATE_TABLE_CIRCLES);
        database.execSQL(CREATE_TABLE_POLYLINES);
        database.execSQL(CREATE_TABLE_POLYGONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(GeofencesDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + GEOFENCES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CIRCLES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + POLYLINES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + POLYGONS_TABLE_NAME);

        onCreate(db);
    }
}
