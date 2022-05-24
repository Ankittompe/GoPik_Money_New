package com.qts.gopik_loan.Activity;

import android.location.Location;
import android.location.LocationListener;
import android.util.Log;

public class MyClass implements LocationListener {
    double currentLatitude, currentLongitude;

    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        Log.e("ghf", "hsd"+currentLongitude);
    }
}
