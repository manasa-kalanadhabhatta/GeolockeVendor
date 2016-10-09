package com.geolocke.android.vendorsdk.beans;

import android.content.ContentValues;

import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;

import java.util.ArrayList;

/**
 * Created by Manasa on 08-09-2016.
 */
public class Polygon extends Shape{
    private ArrayList<Polyline> mEdgeArrayList;

    public Polygon(ArrayList<Polyline> pEdgeArrayList)throws IllegalArgumentException{
        super();
        if(pEdgeArrayList.size()>=3) {
            mShapeType = SHAPE_TYPE_POLYGON;
            mEdgeArrayList = pEdgeArrayList;
        } else{
            throw new IllegalArgumentException("Polygon must have at least three edges.");
        }
    }

    public ArrayList<Polyline> getEdgeArrayList() {
        return mEdgeArrayList;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GeofencesDbHelper.POLYGONS_COL_POLYGON_TYPE, this.getType());
        //contentValues.put(GeofencesDbHelper.POLYGONS_COL_GEOFENCE_ID, this.getGeofenceId());
        return contentValues;
    }
}
