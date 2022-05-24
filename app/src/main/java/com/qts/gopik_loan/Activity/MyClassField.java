package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.qts.gopik_loan.R;

public class MyClassField implements LocationListener {
        double currentLatitude, currentLongitude;

    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        Log.e("ghf", "hsd"+currentLongitude);

    }
}