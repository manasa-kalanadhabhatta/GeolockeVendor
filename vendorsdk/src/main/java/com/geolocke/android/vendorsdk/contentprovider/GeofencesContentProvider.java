package com.geolocke.android.vendorsdk.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import static com.geolocke.android.vendorsdk.contentprovider.GeofencesContract.AUTHORITY;

/**
 * Created by Manasa on 31-08-2016.
 */
public class GeofencesContentProvider extends ContentProvider {

    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    //main geofence
    public static final String GEOFENCES_PATH = "geofences";
    public static final int GEOFENCES_PATH_TOKEN = 100;
    public static final String GEOFENCES_PATH_FOR_ID = "geofences/*";
    public static final int GEOFENCES_PATH_FOR_ID_TOKEN = 200;

    //geofence circles
    public static final String CIRCLES_PATH = "circles";
    public static final int CIRCLES_PATH_TOKEN = 300;
    public static final String CIRCLES_PATH_FOR_ID = "circles/*";
    public static final int CIRCLES_PATH_FOR_ID_TOKEN = 400;

    //geofence polylines
    public static final String POLYLINES_PATH = "polylines";
    public static final int POLYLINES_PATH_TOKEN = 500;
    public static final String POLYLINES_PATH_FOR_ID = "polylines/*";
    public static final int POLYLINES_PATH_FOR_ID_TOKEN = 600;

    //geofence polygons
    public static final String POLYGONS_PATH = "polygons";
    public static final int POLYGONS_PATH_TOKEN = 700;
    public static final String POLYGONS_PATH_FOR_ID = "polygons/*";
    public static final int POLYGONS_PATH_FOR_ID_TOKEN = 800;

    // Uri Matcher for the content provider
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = AUTHORITY;
        matcher.addURI(authority, GEOFENCES_PATH, GEOFENCES_PATH_TOKEN);
        matcher.addURI(authority, GEOFENCES_PATH_FOR_ID, GEOFENCES_PATH_FOR_ID_TOKEN);

        matcher.addURI(authority, CIRCLES_PATH, CIRCLES_PATH_TOKEN);
        matcher.addURI(authority, CIRCLES_PATH_FOR_ID, CIRCLES_PATH_FOR_ID_TOKEN);

        matcher.addURI(authority, POLYLINES_PATH, POLYLINES_PATH_TOKEN);
        matcher.addURI(authority, POLYLINES_PATH_FOR_ID, POLYLINES_PATH_FOR_ID_TOKEN);

        matcher.addURI(authority, POLYGONS_PATH, POLYGONS_PATH_TOKEN);
        matcher.addURI(authority, POLYGONS_PATH_FOR_ID, POLYGONS_PATH_FOR_ID_TOKEN);

        return matcher;
    }

    private GeofencesDbHelper mGeofencesDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mGeofencesDbHelper = new GeofencesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mGeofencesDbHelper.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case GEOFENCES_PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.GEOFENCES_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case GEOFENCES_PATH_FOR_ID_TOKEN: {
                int geofenceId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.GEOFENCES_TABLE_NAME);
                builder.appendWhere(GeofencesDbHelper.GEOFENCES_COL_ID + "=" + geofenceId);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            case CIRCLES_PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.CIRCLES_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case CIRCLES_PATH_FOR_ID_TOKEN: {
                int geofenceId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.CIRCLES_TABLE_NAME);
                builder.appendWhere(GeofencesDbHelper.CIRCLES_COL_ID + "=" + geofenceId);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case POLYLINES_PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.POLYLINES_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case POLYLINES_PATH_FOR_ID_TOKEN: {
                int geofenceId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.POLYLINES_TABLE_NAME);
                builder.appendWhere(GeofencesDbHelper.POLYLINES_COL_ID + "=" + geofenceId);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case POLYGONS_PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.POLYGONS_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case POLYGONS_PATH_FOR_ID_TOKEN: {
                int geofenceId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.POLYGONS_TABLE_NAME);
                builder.appendWhere(GeofencesDbHelper.POLYGONS_COL_ID + "=" + geofenceId);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case GEOFENCES_PATH_TOKEN:
                return GeofencesContract.GEOFENCES_CONTENT_TYPE_DIR;
            case GEOFENCES_PATH_FOR_ID_TOKEN:
                return GeofencesContract.GEOFENCES_CONTENT_ITEM_TYPE;
            case CIRCLES_PATH_TOKEN:
                return GeofencesContract.CIRCLES_CONTENT_TYPE_DIR;
            case CIRCLES_PATH_FOR_ID_TOKEN:
                return GeofencesContract.CIRCLES_CONTENT_ITEM_TYPE;
            case POLYLINES_PATH_TOKEN:
                return GeofencesContract.POLYLINES_CONTENT_TYPE_DIR;
            case POLYLINES_PATH_FOR_ID_TOKEN:
                return GeofencesContract.POLYLINES_CONTENT_ITEM_TYPE;
            case POLYGONS_PATH_TOKEN:
                return GeofencesContract.POLYGONS_CONTENT_TYPE_DIR;
            case POLYGONS_PATH_FOR_ID_TOKEN:
                return GeofencesContract.POLYGONS_CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mGeofencesDbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        switch (token) {
            case GEOFENCES_PATH_TOKEN: {
                long id = db.insert(GeofencesDbHelper.GEOFENCES_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                Log.i("info C:", GeofencesContract.GEOFENCES_CONTENT_URI.toString());
                return GeofencesContract.GEOFENCES_CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case CIRCLES_PATH_TOKEN: {
                long id = db.insert(GeofencesDbHelper.CIRCLES_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return GeofencesContract.CIRCLES_CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case POLYLINES_PATH_TOKEN: {
                long id = db.insert(GeofencesDbHelper.POLYLINES_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return GeofencesContract.POLYLINES_CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case POLYGONS_PATH_TOKEN: {
                long id = db.insert(GeofencesDbHelper.POLYGONS_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return GeofencesContract.POLYGONS_CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }

            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mGeofencesDbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        int rowsDeleted = -1;
        String geofenceIdWhereClause;
        switch (token) {
            case (GEOFENCES_PATH_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.GEOFENCES_COL_ID + "=" + uri.getLastPathSegment();
                rowsDeleted = db.delete(GeofencesDbHelper.GEOFENCES_TABLE_NAME, selection, selectionArgs);
                break;
            case (GEOFENCES_PATH_FOR_ID_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.GEOFENCES_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    geofenceIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(GeofencesDbHelper.GEOFENCES_TABLE_NAME, geofenceIdWhereClause, selectionArgs);
                break;
            case (CIRCLES_PATH_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.CIRCLES_COL_ID + "=" + uri.getLastPathSegment();
                rowsDeleted = db.delete(GeofencesDbHelper.CIRCLES_TABLE_NAME, selection, selectionArgs);
                break;
            case (CIRCLES_PATH_FOR_ID_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.CIRCLES_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    geofenceIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(GeofencesDbHelper.CIRCLES_TABLE_NAME, geofenceIdWhereClause, selectionArgs);
                break;
            case (POLYLINES_PATH_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.POLYLINES_COL_ID + "=" + uri.getLastPathSegment();
                rowsDeleted = db.delete(GeofencesDbHelper.POLYLINES_TABLE_NAME, selection, selectionArgs);
                break;
            case (POLYLINES_PATH_FOR_ID_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.POLYLINES_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    geofenceIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(GeofencesDbHelper.POLYLINES_TABLE_NAME, geofenceIdWhereClause, selectionArgs);
                break;
            case (POLYGONS_PATH_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.POLYGONS_COL_ID + "=" + uri.getLastPathSegment();
                rowsDeleted = db.delete(GeofencesDbHelper.POLYGONS_TABLE_NAME, selection, selectionArgs);
                break;
            case (POLYGONS_PATH_FOR_ID_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.POLYGONS_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    geofenceIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(GeofencesDbHelper.POLYGONS_TABLE_NAME, geofenceIdWhereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

