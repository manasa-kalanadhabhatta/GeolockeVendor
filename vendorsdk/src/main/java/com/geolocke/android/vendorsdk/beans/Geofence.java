package com.geolocke.android.vendorsdk.beans;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.geolocke.android.vendorsdk.contentprovider.GeofencesDbHelper;

/**
 * Created by Manasa on 31-08-2016.
 */
public class Geofence implements Parcelable {

    public static String TYPE_CIRCULAR = "circular";
    public static String TYPE_POLYGONAL = "polygonal";
    public static String TYPE_COMPLEX = "complex";

    public static String TYPE_FENCE = "fence";
    public static String TYPE_HOLE = "hole";

    private String mType;

    public Geofence() {

    }

    public String getType() {
        return mType;
    }

    public void setType(String pType) {
        if(pType == Geofence.TYPE_CIRCULAR||pType== Geofence.TYPE_POLYGONAL||pType== Geofence.TYPE_COMPLEX)
            mType = pType;
        else
            throw new IllegalArgumentException("Undefined Geofence Type");
    }

    @Override
    public String toString() {
        return "Geofence{" +
                "mType='" + mType + '\'' +
                '}';
    }

    protected Geofence(Parcel in) {
        mType = in.readString();
    }

    public static final Creator<Geofence> CREATOR = new Creator<Geofence>() {
        @Override
        public Geofence createFromParcel(Parcel in) {
            return new Geofence(in);
        }

        @Override
        public Geofence[] newArray(int size) {
            return new Geofence[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mType);
    }

    // DbHelper Methods

    public static Geofence fromCursor(Cursor pCursor){
        Geofence geofence = new Geofence();
        geofence.setType(pCursor.getString(pCursor.getColumnIndex(GeofencesDbHelper.GEOFENCES_COL_TYPE)));
        return geofence;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GeofencesDbHelper.GEOFENCES_COL_TYPE, this.getType());
        return contentValues;
    }

}
