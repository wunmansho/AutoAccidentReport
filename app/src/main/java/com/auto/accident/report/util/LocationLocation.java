package com.auto.accident.report.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.auto.accident.report.objects.ApplicationContextProvider;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnActivityUpdatedListener;
import io.nlopez.smartlocation.OnGeofencingTransitionListener;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.geofencing.model.GeofenceModel;
import io.nlopez.smartlocation.geofencing.utils.TransitionGeofence;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;

/**
 * Created by myron on 11/7/2018.
 */

public class LocationLocation  extends AppCompatActivity implements
        OnLocationUpdatedListener, OnActivityUpdatedListener, OnGeofencingTransitionListener {
    private static final String TAG = "LocationLocation";
    private static final int LOCATION_PERMISSION_ID = 1001;
    private LocationGooglePlayServicesProvider provider;
    private Location loc = new Location("service Provider");
    private String locationString;
    private String activityString;
    private String geofenceString;
    private String Country, CountryCD, City, State, StateCD, County, Street, Building, Zip;
    private double Lat, Lon;
    private boolean hasLat, hasLon;
    private static final Context context = ApplicationContextProvider.getContext();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_ID && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocation();
        }
    }

    private void showLast() {
        Location lastLocation = SmartLocation.with(context).location().getLastLocation();
        if (lastLocation != null) {
            locationString = String.format("[From Cache] Latitude %.6f, Longitude %.6f",
                    lastLocation.getLatitude(),
                    lastLocation.getLongitude());
                    
        }

        DetectedActivity detectedActivity = SmartLocation.with(context).activity().getLastActivity();
        if (detectedActivity != null) {
            activityString =  String.format("[From Cache] Activity %s with %d%% confidence",
                    getNameFromType(detectedActivity),
                    detectedActivity.getConfidence());    
           
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (provider != null) {
            provider.onActivityResult(requestCode, resultCode, data);

            int x = 1;
        }
    }

    // End OnCreate
    private void stopLocation() {
        SmartLocation.with(context).location().stop();
        // locationText.setText("Location stopped!");

        SmartLocation.with(context).activity().stop();
        //  activityText.setText("Activity Recognition stopped!");

        SmartLocation.with(context).geofencing().stop();
        // geofenceText.setText("Geofencing stopped!");
    }

    private void startLocation() {

        provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);

        SmartLocation smartLocation = new SmartLocation.Builder(context).logging(true).build();

        smartLocation.location(provider).start(this);
        smartLocation.activity().start(this);

        // Create some geofences
        GeofenceModel mestalla = new GeofenceModel.Builder("1").setTransition(Geofence.GEOFENCE_TRANSITION_ENTER).setLatitude(39.47453120000001).setLongitude(-0.358065799999963).setRadius(500).build();
        smartLocation.geofencing().add(mestalla).start(this);
    }

    @Override
    public void onLocationUpdated(Location location) {
        showLocation(location);
    }

    @Override
    public void onActivityUpdated(DetectedActivity detectedActivity) {
        showActivity(detectedActivity);
    }

    @Override
    public void onGeofenceTransition(TransitionGeofence geofence) {
        showGeofence(geofence.getGeofenceModel().toGeofence(), geofence.getTransitionType());
    }

    private void showLocation(Location location) {
        if (location != null) {
            final String text = String.format("Latitude %.6f, Longitude %.6f",
                    location.getLatitude(),
                    location.getLongitude());
            locationString = text;

            // We are going to get the entity_address for the current position
            SmartLocation.with(context).geocoding().reverse(location, (original, results) -> {
                if (results.size() > 0) {
                    Address result = results.get(0);
                    StringBuilder builder = new StringBuilder(text);
                    builder.append("\n[Reverse Geocoding] ");
                    List<String> addressElements = new ArrayList<>();
                    for (int i = 0; i <= result.getMaxAddressLineIndex(); i++) {
                        addressElements.add(result.getAddressLine(i));
                    }
                    builder.append(TextUtils.join(", ", addressElements));
                    locationString = builder.toString();
                    //    private String Country, CountryCD, City, State, StateCD, County, Street, Building, Zip;
                    //    private float Lat, Lon;
                    //    private boolean hasLat, hasLon;

                    Country = result.getCountryName();
                    CountryCD = result.getCountryCode();
                    State = result.getAdminArea();
                    // StateCD =
                    County = result.getSubAdminArea();
                    Street = result.getThoroughfare();
                    Building = result.getSubThoroughfare();
                    Zip = result.getPostalCode();
                    hasLat = result.hasLatitude();
                    hasLon = result.hasLongitude();
                    Lat = result.getLatitude();
                    Lon = result.getLongitude();
                    City = result.getLocality();
                 //   ccp.setCountryForNameCode(CountryCD);
                 //   tieAID_ADDRESS.setText("2 W Independent Dr");
                    //   tieAID_ADDRESS.setText(Building + " " + Street);
                 //   tieAID_COUNTY.setText(County);
                //    tieAID_STATE.setText(State);
                  //  tieAID_CITY.setText(City);
                  //  tieAID_ZIP.setText("32202");
                  //  tieAID_LAT.setText("30.332184");
                  //  tieAID_LON.setText("-81.655647");
                    //  tieAID_ZIP.setText(Zip);
                    //   tieAID_LAT.setText(Double.toString(Lat));
                    //   tieAID_LON.setText(Double.toString(Lon));
                    stopLocation();
                }
            });
        } else {
          locationString = "Null location";
        }
    }

    private void showActivity(DetectedActivity detectedActivity) {
        if (detectedActivity != null) {
            activityString =
                    String.format("Activity %s with %d%% confidence",
                            getNameFromType(detectedActivity),
                            detectedActivity.getConfidence());
        } else {
            activityString = "Null activity";
        }
    }

    private void showGeofence(Geofence geofence, int transitionType) {
        if (geofence != null) {
            geofenceString = "Transition " + getTransitionNameFromType(transitionType) + " for Geofence with id = " + geofence.getRequestId();
        } else {
            geofenceString = "Null geofence";
        }
    }

    private String getNameFromType(DetectedActivity activityType) {
        switch (activityType.getType()) {
            case DetectedActivity.IN_VEHICLE:
                return "in_vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "on_bicycle";
            case DetectedActivity.ON_FOOT:
                return "on_foot";
            case DetectedActivity.STILL:
                return "still";
            case DetectedActivity.TILTING:
                return "tilting";
            default:
                return "unknown";
        }
    }

    private String getTransitionNameFromType(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "enter";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "exit";
            default:
                return "dwell";
        }
    }




}
