package com.geolocke.android.vendorsdk.beans;

/**
 * Created by Manasa on 08-09-2016.
 */
public abstract class Shape {
    public static final String TYPE_CIRCLE = "circle";
    public static final String TYPE_POLYGON = "polygon";

    protected long mShapeId;
    protected String mType;

    protected Shape(){
        mShapeId = System.currentTimeMillis();
    }

    public long getShapeId() {
        return mShapeId;
    }

    public String getType() {
        return mType;
    }

    public boolean isInside(Shape pShape){
        // TODO: 08-09-2016 define isInside()
        return true;
    }
}
