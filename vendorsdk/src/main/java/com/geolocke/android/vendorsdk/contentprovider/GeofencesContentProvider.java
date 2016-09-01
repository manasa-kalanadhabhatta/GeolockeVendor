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
    public static final String GEOFENCECIRCLES_PATH = "geofencecircles";
    public static final int GEOFENCECIRCLES_PATH_TOKEN = 300;
    public static final String GEOFENCECIRCLES_PATH_FOR_ID = "geofencecircles/*";
    public static final int GEOFENCECIRCLES_PATH_FOR_ID_TOKEN = 400;

    //geofence polylines
    public static final String GEOFENCEPOLYLINES_PATH = "geofencepolylines";
    public static final int GEOFENCEPOLYLINES_PATH_TOKEN = 500;
    public static final String GEOFENCEPOLYLINES_PATH_FOR_ID = "geofencepolylines/*";
    public static final int GEOFENCEPOLYLINES_PATH_FOR_ID_TOKEN = 600;

    // Uri Matcher for the content provider
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = AUTHORITY;
        matcher.addURI(authority, GEOFENCES_PATH, GEOFENCES_PATH_TOKEN);
        matcher.addURI(authority, GEOFENCES_PATH_FOR_ID, GEOFENCES_PATH_FOR_ID_TOKEN);

        matcher.addURI(authority, GEOFENCECIRCLES_PATH, GEOFENCECIRCLES_PATH_TOKEN);
        matcher.addURI(authority, GEOFENCECIRCLES_PATH_FOR_ID, GEOFENCECIRCLES_PATH_FOR_ID_TOKEN);

        matcher.addURI(authority, GEOFENCEPOLYLINES_PATH, GEOFENCEPOLYLINES_PATH_TOKEN);
        matcher.addURI(authority, GEOFENCEPOLYLINES_PATH_FOR_ID, GEOFENCEPOLYLINES_PATH_FOR_ID_TOKEN);

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
            case GEOFENCECIRCLES_PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.GEOFENCECIRCLES_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case GEOFENCECIRCLES_PATH_FOR_ID_TOKEN: {
                int geofenceId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.GEOFENCECIRCLES_TABLE_NAME);
                builder.appendWhere(GeofencesDbHelper.GEOFENCECIRCLES_COL_ID + "=" + geofenceId);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case GEOFENCEPOLYLINES_PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.GEOFENCEPOLYLINES_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case GEOFENCEPOLYLINES_PATH_FOR_ID_TOKEN: {
                int geofenceId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeofencesDbHelper.GEOFENCEPOLYLINES_TABLE_NAME);
                builder.appendWhere(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_ID + "=" + geofenceId);
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
            case GEOFENCECIRCLES_PATH_TOKEN:
                return GeofencesContract.GEOFENCECIRCLES_CONTENT_TYPE_DIR;
            case GEOFENCECIRCLES_PATH_FOR_ID_TOKEN:
                return GeofencesContract.GEOFENCECIRCLES_CONTENT_ITEM_TYPE;
            case GEOFENCEPOLYLINES_PATH_TOKEN:
                return GeofencesContract.GEOFENCEPOLYLINES_CONTENT_TYPE_DIR;
            case GEOFENCEPOLYLINES_PATH_FOR_ID_TOKEN:
                return GeofencesContract.GEOFENCEPOLYLINES_CONTENT_ITEM_TYPE;
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
                return GeofencesContract.GEOFENCES_CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case GEOFENCECIRCLES_PATH_TOKEN: {
                long id = db.insert(GeofencesDbHelper.GEOFENCECIRCLES_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return GeofencesContract.GEOFENCECIRCLES_CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case GEOFENCEPOLYLINES_PATH_TOKEN: {
                long id = db.insert(GeofencesDbHelper.GEOFENCEPOLYLINES_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return GeofencesContract.GEOFENCEPOLYLINES_CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
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
            case (GEOFENCECIRCLES_PATH_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.GEOFENCECIRCLES_COL_ID + "=" + uri.getLastPathSegment();
                rowsDeleted = db.delete(GeofencesDbHelper.GEOFENCECIRCLES_TABLE_NAME, selection, selectionArgs);
                break;
            case (GEOFENCECIRCLES_PATH_FOR_ID_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.GEOFENCECIRCLES_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    geofenceIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(GeofencesDbHelper.GEOFENCECIRCLES_TABLE_NAME, geofenceIdWhereClause, selectionArgs);
                break;
            case (GEOFENCEPOLYLINES_PATH_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.GEOFENCEPOLYLINES_COL_ID + "=" + uri.getLastPathSegment();
                rowsDeleted = db.delete(GeofencesDbHelper.GEOFENCEPOLYLINES_TABLE_NAME, selection, selectionArgs);
                break;
            case (GEOFENCEPOLYLINES_PATH_FOR_ID_TOKEN):
                geofenceIdWhereClause = GeofencesDbHelper.GEOFENCEPOLYLINES_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    geofenceIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(GeofencesDbHelper.GEOFENCEPOLYLINES_TABLE_NAME, geofenceIdWhereClause, selectionArgs);
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

