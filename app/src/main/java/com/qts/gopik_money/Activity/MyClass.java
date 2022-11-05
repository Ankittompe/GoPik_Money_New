package com.qts.gopik_money.Activity;

import android.location.Location;
import android.location.LocationListener;

public class MyClass implements LocationListener {
    double currentLatitude;
    double currentLongitude;

    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

    }
}
