package com.auto.accident.report.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;

public class GetLatLong extends Activity implements OnLocationUpdatedListener {

    private static final int LOCATION_PERMISSION_ID = 1001;
   private LocationGooglePlayServicesProvider provider;
   private String Lat;
   private String Long;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
         getLoc();
    }

    private void getLoc() {
        if (ContextCompat.checkSelfPermission(GetLatLong.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GetLatLong.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_ID);
            return;
        }
        startLocation();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_ID && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocation();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (provider != null) {
            provider.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startLocation() {

        provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);

        SmartLocation smartLocation = new SmartLocation.Builder(this).logging(true).build();

        smartLocation.location(provider).start(this);
      }

    private void stopLocation() {
        SmartLocation.with(this).location().stop();
     }

    private void showLocation(Location location) {
        if (location != null) {
            Lat =  Double.toString(location.getLatitude());
            Long = Double.toString(location.getLongitude());
         } else {
           Lat = "0";
           Long = "0";
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Lat", Lat);
        returnIntent.putExtra("Long", Long);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void onLocationUpdated(Location location) {
        showLocation(location);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        SmartLocation.with(this).location().stop();

    }
}
