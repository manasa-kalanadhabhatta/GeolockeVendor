package com.geolocke.android.vendorsdk.beans;

/**
 * Created by Manasa on 08-09-2016.
 */
public abstract class Shape {
    public static final String SHAPE_TYPE_CIRCLE = "circle";
    public static final String SHAPE_TYPE_POLYGON = "polygon";

    public static final String TYPE_FENCE = "fence";
    public static final String TYPE_HOLE = "hole";

    protected long mShapeId;
    protected String mShapeType;
    protected long mGeofenceId;
    protected String mType;

    protected Shape(){
        mShapeId = System.currentTimeMillis();
    }

    public String getShapeType() {
        return mShapeType;
    }

    public long getGeofenceId() {
        return mGeofenceId;
    }

    public void setGeofenceId(long pGeofenceId) {
        mGeofenceId = pGeofenceId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String pType) {
        mType = pType;
    }

    public boolean isInside(Shape pShape){
        // TODO: 08-09-2016 define isInside()
        return true;
    }
}
