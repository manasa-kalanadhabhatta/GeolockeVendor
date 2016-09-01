package com.geolocke.android.vendorsdk.interfaces;


import com.geolocke.android.vendorsdk.GeolockeVendorHandle;

public interface GeolockeConnectionListener {
    public void onGeolockeConnected(GeolockeVendorHandle pGeolockeVendorHandle);
    public void onGeolockeDisconnected();
}
