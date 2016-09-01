package com.geolocke.android.vendorsdk.beans;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;

/**
 * Created by Manasa on 31-08-2016.
 */
public class GeofenceCircle implements Parcelable {

    private long mMainGeofenceId;
    private String mType;
    private double mCentreLatitude;
    private double mCentreLongitude;
    private double mRadius;

    public GeofenceCircle() {
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

    public double getCentreLatitude() {
        return mCentreLatitude;
    }

    public void setCentreLatitude(double pCentreLatitude) {
        mCentreLatitude = pCentreLatitude;
    }

    public double getCentreLongitude() {
        return mCentreLongitude;
    }

    public void setCentreLongitude(double pCentreLongitude) {
        mCentreLongitude = pCentreLongitude;
    }

    public double getRadius() {
        return mRadius;
    }

    public void setRadius(double pRadius) {
        mRadius = pRadius;
    }

    @Override
    public String toString() {
        return "GeofenceCircle{" +
                "mMainGeofenceId=" + mMainGeofenceId +
                ", mType='" + mType + '\'' +
                ", mCentreLatitude=" + mCentreLatitude +
                ", mCentreLongitude=" + mCentreLongitude +
                ", mRadius=" + mRadius +
                '}';
    }

    protected GeofenceCircle(Parcel in) {
        mMainGeofenceId = in.readLong();
        mType = in.readString();
        mCentreLatitude = in.readDouble();
        mCentreLongitude = in.readDouble();
        mRadius = in.readDouble();
    }

    public static final Creator<GeofenceCircle> CREATOR = new Creator<GeofenceCircle>() {
        @Override
        public GeofenceCircle createFromParcel(Parcel in) {
            return new GeofenceCircle(in);
        }

        @Override
        public GeofenceCircle[] newArray(int size) {
            return new GeofenceCircle[size];
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
        dest.writeDouble(mCentreLatitude);
        dest.writeDouble(mCentreLongitude);
        dest.writeDouble(mRadius);
    }

    // TODO: 08-08-2016 override equals()

    // DbHelper Methods

    public static GeofenceCircle fromCursor(Cursor pCursor){
        GeofenceCircle geofenceCircle = new GeofenceCircle();
        geofenceCircle.setMainGeofenceId(pCursor.getLong(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCECIRCLES_COL_MAIN_GEOFENCE_ID)));
        geofenceCircle.setType(pCursor.getString(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCECIRCES_COL_TYPE)));
        geofenceCircle.setCentreLatitude(pCursor.getDouble(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCECIRCLES_COL_CENTRE_LATITUDE)));
        geofenceCircle.setCentreLongitude(pCursor.getDouble(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCECIRCLES_COL_CENTRE_LONGITUDE)));
        geofenceCircle.setRadius(pCursor.getDouble(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCECIRCLES_COL_RADIUS)));
        return geofenceCircle;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GeofencesDbHelper.GEOFENCECIRCLES_COL_MAIN_GEOFENCE_ID,this.getMainGeofenceId());
        contentValues.put(GeofencesDbHelper.GEOFENCECIRCES_COL_TYPE,this.getType());
        contentValues.put(GeofencesDbHelper.GEOFENCECIRCLES_COL_CENTRE_LATITUDE, this.getCentreLatitude());
        contentValues.put(GeofencesDbHelper.GEOFENCECIRCLES_COL_CENTRE_LONGITUDE, this.getCentreLongitude());
        contentValues.put(GeofencesDbHelper.GEOFENCECIRCLES_COL_RADIUS, this.getRadius());
        return contentValues;
    }


}
