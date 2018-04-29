package com.victorsquarecity.app.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by app on 5/29/15.
 */
public class LocationsController {

    static ArrayList<LocationModel> locationsArray = new ArrayList<>();

    private static Location location;

    public static void addLocation(LocationModel newLocation) {
        locationsArray.add(newLocation);
//        AddLocationEvent newLocationEvent = new AddLocationEvent();
//        newLocationEvent.setLocationModel(newLocation);
//        // Broadcast that a pin was added
        BusProvider.getInstance().post(new AddLocationEvent());
    }

    public static void addLocation(ArrayList<LocationModel> newLocationsArray) {
        locationsArray.addAll(newLocationsArray);

        // Broadcast that a pin was added
        BusProvider.getInstance().post(new AddLocationEvent());
    }

    public static ArrayList<LocationModel> getLocations() {
        return locationsArray;
    }

    public static Boolean removeLocation(LocationModel locationToRemove) {
        // For loop to search array and remove this item
        return locationsArray.remove(locationToRemove);

        // Broadcast that the pin was removed
    }

    public static void startLocationReporting(Context context) {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("TAG", "location: " + location);
                LocationsController.location = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public static Location getCurrentLocation() {
        return location;
    }


    private static final int TEN_MINUTES = 1000 * 60 * 10;

    /**
     * Determines whether one Location reading is better than the current Location fix
     *
     * @param location            The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TEN_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TEN_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
