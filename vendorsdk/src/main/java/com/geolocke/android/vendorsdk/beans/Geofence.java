package com.geolocke.android.vendorsdk.beans;

import android.content.ContentValues;

import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;

import java.util.ArrayList;

/**
 * Created by Manasa on 08-09-2016.
 */
public class Geofence {
    private long mGeofenceId;
    private String mType;
    private Shape mFence;
    private ArrayList<Shape> mHoleArrayList;

    public Geofence(Shape pFence){
        mGeofenceId = System.currentTimeMillis();
        pFence.setGeofenceId(mGeofenceId);
        pFence.setType(Shape.TYPE_FENCE);
        mType = pFence.getShapeType();
        mFence = pFence;
        mHoleArrayList = new ArrayList<Shape>();
    }

    public Geofence addHole(Shape pHole){
        if(pHole.isInside(mFence)) {
            pHole.setGeofenceId(mGeofenceId);
            pHole.setType(Shape.TYPE_HOLE);
            mHoleArrayList.add(pHole);
        }
        // TODO: 08-09-2016 else throw exception?
        return this;
    }

    public long getGeofenceId() {
        return mGeofenceId;
    }

    public String getType() {
        return mType;
    }

    public Shape getFence() {
        return mFence;
    }

    public ArrayList<Shape> getHoleArrayList() {
        return mHoleArrayList;
    }

    public boolean isComplex(){
        if(mHoleArrayList.isEmpty())
            return false;
        return true;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GeofencesDbHelper.GEOFENCES_COL_FENCE_TYPE, this.getType());
        contentValues.put(GeofencesDbHelper.GEOFENCES_COL_IS_COMPLEX, this.isComplex());
        return contentValues;
    }
}
