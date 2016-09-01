package com.geolocke.android.vendorsdk.beans;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;

/**
 * Created by Manasa on 31-08-2016.
 */
public class GeofencePolyline implements Parcelable {

    private long mMainGeofenceId;
    private String mType;
    private double mLatitudePointOne;
    private double mLongitudePointOne;
    private double mLatitudePointTwo;
    private double mLongitudePointTwo;

    public GeofencePolyline() {
    }

    public long getMainGeofenceId() {
        return mMainGeofenceId;
    }

    public void setMainGeofenceId(long pMainGeofenceId) {
        mMainGeofenceId = pMainGeofenceId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String pType) {
        if(pType == Geofence.TYPE_FENCE||pType== Geofence.TYPE_HOLE)
            mType = pType;
        else
            throw new IllegalArgumentException("Undefined Geofence Type");
    }

    public double getLatitudePointOne() {
        return mLatitudePointOne;
    }

    public void setLatitudePointOne(double pLatitudePointOne) {
        mLatitudePointOne = pLatitudePointOne;
    }

    public double getLongitudePointOne() {
        return mLongitudePointOne;
    }

    public void setLongitudePointOne(double pLongitudePointOne) {
        mLongitudePointOne = pLongitudePointOne;
    }

    public double getLatitudePointTwo() {
        return mLatitudePointTwo;
    }

    public void setLatitudePointTwo(double pLatitudePointTwo) {
        mLatitudePointTwo = pLatitudePointTwo;
    }

    public double getLongitudePointTwo() {
        return mLongitudePointTwo;
    }

    public void setLongitudePointTwo(double pLongitudePointTwo) {
        mLongitudePointTwo = pLongitudePointTwo;
    }

    protected GeofencePolyline(Parcel in) {
        mMainGeofenceId = in.readLong();
        mType = in.readString();
        mLatitudePointOne = in.readDouble();
        mLongitudePointOne = in.readDouble();
        mLatitudePointTwo = in.readDouble();
        mLongitudePointTwo = in.readDouble();
    }

    public static final Creator<GeofencePolyline> CREATOR = new Creator<GeofencePolyline>() {
        @Override
        public GeofencePolyline createFromParcel(Parcel in) {
            return new GeofencePolyline(in);
        }

        @Override
        public GeofencePolyline[] newArray(int size) {
            return new GeofencePolyline[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mMainGeofenceId);
        dest.writeString(mType);
        dest.writeDouble(mLatitudePointOne);
        dest.writeDouble(mLongitudePointOne);
        dest.writeDouble(mLatitudePointTwo);
        dest.writeDouble(mLongitudePointTwo);
    }

    // TODO: 10-08-2016 override equals()

    //DbHelper Methods

    public static GeofencePolyline fromCursor(Cursor pCursor){
        GeofencePolyline geofencePolyline = new GeofencePolyline();
        geofencePolyline.setMainGeofenceId(pCursor.getLong(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_MAIN_GEOFENCE_ID)));
        geofencePolyline.setType(pCursor.getString(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_TYPE)));
        geofencePolyline.setLatitudePointOne(pCursor.getDouble(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_LATITUDE_POINT_ONE)));
        geofencePolyline.setLongitudePointOne(pCursor.getDouble(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_LONGITUDE_POINT_ONE)));
        geofencePolyline.setLatitudePointTwo(pCursor.getDouble(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_LATITUDE_POINT_TWO)));
        geofencePolyline.setLongitudePointTwo(pCursor.getDouble(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_LONGITUDE_POINT_TWO)));
        return geofencePolyline;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_MAIN_GEOFENCE_ID, this.getMainGeofenceId());
        contentValues.put(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_TYPE, this.getType());
        contentValues.put(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_LATITUDE_POINT_ONE, this.getLatitudePointOne());
        contentValues.put(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_LONGITUDE_POINT_ONE, this.getLongitudePointOne());
        contentValues.put(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_LATITUDE_POINT_TWO, this.getLatitudePointTwo());
        contentValues.put(GeofencesDbHelper.GEOFENCEPOLYLINES_COL_LONGITUDE_POINT_TWO, this.getLongitudePointTwo());
        return contentValues;
    }

}
