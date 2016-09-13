package com.geolocke.android.vendorsdk.beans;

import java.util.ArrayList;

/**
 * Created by Manasa on 08-09-2016.
 */
public class Polygon extends Shape{
    private ArrayList<Polyline> mEdgeArrayList;

    public Polygon(ArrayList<Polyline> pEdgeArrayList)throws IllegalArgumentException{
        super();
        if(pEdgeArrayList.size()>=3) {
            mType = TYPE_POLYGON;
            mEdgeArrayList = pEdgeArrayList;
        } else{
            throw new IllegalArgumentException("Polygon must have at least three edges.");
        }
    }

    public ArrayList<Polyline> getEdgeArrayList() {
        return mEdgeArrayList;
    }
}
