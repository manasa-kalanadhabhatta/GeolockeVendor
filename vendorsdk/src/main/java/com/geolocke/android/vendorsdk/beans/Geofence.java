package com.geolocke.android.vendorsdk.beans;

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
        mType = pFence.getType();
        mFence = pFence;
        mHoleArrayList = new ArrayList<Shape>();
    }

    public Geofence addHole(Shape pHole){
        if(pHole.isInside(mFence))
            mHoleArrayList.add(pHole);
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
}
