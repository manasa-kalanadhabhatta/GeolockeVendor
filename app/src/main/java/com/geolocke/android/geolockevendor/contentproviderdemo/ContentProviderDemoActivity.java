package com.geolocke.android.geolockevendor.contentproviderdemo;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.geolocke.android.geolockevendor.R;
import com.geolocke.android.vendorsdk.beans.Geofence;
import com.geolocke.android.vendorsdk.contentprovider.GeofencesContract;
import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;

public class ContentProviderDemoActivity extends AppCompatActivity {

    ListView mListView;
    CursorAdapter mCursorAdapter;
    String[] mProjection;
    String mSelectionClause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_demo);
        mListView = (ListView)findViewById(R.id.listView);
        mSelectionClause = null;
    }

    public void loadGeofences(View pView){
        String[] geofencesListColumns = {
                GeofencesDbHelper.GEOFENCES_COL_ID,
                GeofencesDbHelper.GEOFENCES_COL_GEOFENCE_NAME,
                GeofencesDbHelper.GEOFENCES_COL_FENCE_TYPE,
                GeofencesDbHelper.GEOFENCES_COL_IS_COMPLEX
        };

        mProjection = geofencesListColumns;
        Cursor cursor = getContentResolver().query(GeofencesContract.GEOFENCES_CONTENT_URI,mProjection,mSelectionClause,null,null);

        int[] geofenceListItems = {R.id.idView, R.id.nameView, R.id.fenceTypeView, R.id.isComplexView};

        mCursorAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.fragment_geofences_list_item,cursor,geofencesListColumns,geofenceListItems,0);
        mListView.setAdapter(mCursorAdapter);
    }

    public void loadCircles(View pView){
        String[] circlesListColumns = {
                GeofencesDbHelper.CIRCLES_COL_ID,
                GeofencesDbHelper.CIRCLES_COL_CENTRE_LATITUDE,
                GeofencesDbHelper.CIRCLES_COL_CENTRE_LONGITUDE,
                GeofencesDbHelper.CIRCLES_COL_RADIUS,
                GeofencesDbHelper.CIRCLES_COL_CIRCLE_TYPE,
                GeofencesDbHelper.CIRCLES_COL_GEOFENCE_ID
        };

        mProjection = circlesListColumns;
        Cursor cursor = getContentResolver().query(GeofencesContract.CIRCLES_CONTENT_URI,mProjection,mSelectionClause,null,null);

        int[] circlesListItems = {R.id.circlesColId, R.id.circlesColCentreLat, R.id.circlesColCentreLng, R.id.circlesColRadius, R.id.circlesColCircleType, R.id.circlesColGeofenceId};

        mCursorAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.fragment_circles_list_item,cursor,circlesListColumns,circlesListItems,0);
        mListView.setAdapter(mCursorAdapter);
    }

    public void loadPolygons(View pView){
        String[] polygonsListColumns = {
                GeofencesDbHelper.POLYGONS_COL_ID,
                GeofencesDbHelper.POLYGONS_COL_POLYGON_TYPE,
                GeofencesDbHelper.POLYGONS_COL_GEOFENCE_ID
        };

        mProjection = polygonsListColumns;
        Cursor cursor = getContentResolver().query(GeofencesContract.POLYGONS_CONTENT_URI,mProjection,mSelectionClause,null,null);

        int[] polygonsListItems = {R.id.polygonsColId, R.id.polygonsColPolygonType, R.id.polygonsColGeofenceId};

        mCursorAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.fragment_polygons_list_item,cursor,polygonsListColumns,polygonsListItems,0);
        mListView.setAdapter(mCursorAdapter);
    }

    public void loadPolylines(View pView){
        String[] polylinesListColumns = {
                GeofencesDbHelper.POLYLINES_COL_ID,
                GeofencesDbHelper.POLYLINES_COL_LATITUDE_POINT_ONE,
                GeofencesDbHelper.POLYLINES_COL_LONGITUDE_POINT_ONE,
                GeofencesDbHelper.POLYLINES_COL_LATITUDE_POINT_TWO,
                GeofencesDbHelper.POLYLINES_COL_LONGITUDE_POINT_TWO,
                GeofencesDbHelper.POLYLINES_COL_POLYGON_ID
        };

        mProjection = polylinesListColumns;
        Cursor cursor = getContentResolver().query(GeofencesContract.POLYLINES_CONTENT_URI,mProjection,mSelectionClause,null,null);

        int[] polylinesListItems = {R.id.polylinesColId, R.id.polylinesColLatPointOne, R.id.polylinesColLngPointOne, R.id.polylinesColLatPointTwo, R.id.polylinesColLngPointTwo, R.id.polylinesColPolygonId};

        mCursorAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.fragment_polylines_list_item,cursor,polylinesListColumns,polylinesListItems,0);
        mListView.setAdapter(mCursorAdapter);
    }
}
