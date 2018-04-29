package com.victorsquarecity.app.utils;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.RuntimeExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by app on 5/29/17.
 */
public class LocationsFactory {

    public static final String JSON_LOCATION_NAME = "name";
    public static final String JSON_LOCATION_DESCRIPTION = "description";
    public static final String JSON_LOCATION_RATINGS = "rating";
    public static final String JSON_LOCATION_TYPES = "types";
    public static final String JSON_LOCATION_ADDRESS = "vicinity";
    public static final String JSON_LOCATION_GPS = "location";
    public static final String JSON_LOCATION_IMAGE_REF = "photo_reference";
    public static final String JSON_LOCATION_ICON = "icon";

    public static final String TAG = "LocationsFactory";

    public static ArrayList createLocationsFromJSON(String locationsJSON) {
        Log.d(TAG, "createLocationsFromJSON() started" + locationsJSON);
        ArrayList<LocationModel> locationsArrayList = new ArrayList<>();


        try {
            // JSONObject result = new JSONObject(locationsJSON.toString());
            // Log.d(TAG, "/location result " + result);
            JSONArray jsonLocationsArray = new JSONArray(locationsJSON);

            for (int index = 0; index < jsonLocationsArray.length(); index++) {
                JSONObject locationJSON = jsonLocationsArray.getJSONObject(index);

                Log.d(TAG, "createLocationsFromJSON() /location locationJson " + locationsJSON);

                LocationModel newLocation = createLocationFromJSON(locationJSON);

                Log.d(TAG, "createLocationsFromJSON() /location LocationModel " + newLocation.toString());
                locationsArrayList.add(newLocation);
            }

            LocationsController.addLocation(locationsArrayList);
        } catch (Exception ex) {
            Log.e("createLocat() /loca ex ", ex.getMessage());
        }
        return locationsArrayList;
    }


    public static LocationModel createLocationFromJSON(JSONObject locationJSON) throws JSONException {

        String locationDescription = null, locationName = null, locationratings = null;
        if (locationJSON.has(JSON_LOCATION_NAME)) {
            locationName = locationJSON.optString(JSON_LOCATION_NAME);
        }

        if (locationJSON.has(JSON_LOCATION_DESCRIPTION)) {
            locationDescription = locationJSON.optString(JSON_LOCATION_DESCRIPTION);
        }

        if (locationJSON.has(JSON_LOCATION_RATINGS)) {
            locationratings = locationJSON.optString(JSON_LOCATION_RATINGS);
        }

//        String locationTypes =null;
//        if (locationJSON.has(JSON_LOCATION_TYPES) ) {
//            locationTypes = (String) locationJSON.optJSONObject(JSON_LOCATION_TYPES).toString();
//        }

        String locationVicinity = null;
        if (locationJSON.has(JSON_LOCATION_ADDRESS)) {
            locationVicinity = locationJSON.optString(JSON_LOCATION_ADDRESS);
        }

        LatLng latLng = null;
        if (locationJSON.has("geometry")) {
            JSONObject locationGeometry = locationJSON.optJSONObject("geometry");
            if (locationGeometry.has(JSON_LOCATION_GPS)) {
                JSONObject locationGPS = locationGeometry.optJSONObject(JSON_LOCATION_GPS);
                double latitude = 0.0, longitude = 0.0;
                if (locationGPS.has("lat"))
                    latitude = locationGPS.optDouble("lat");
                if (locationGPS.has("lng"))
                    longitude = locationGPS.optDouble("lng");
                latLng = new LatLng(latitude, longitude);
            }
        }

        String icon = null;
        if(locationJSON.has("icon")){
            icon = locationJSON.optString("icon");
        }

        String price_level = null;
        if(locationJSON.has("price_level")){
            price_level = locationJSON.optString("price_level");
        }
        List<String> placesPhotos = new ArrayList<>();
        JSONArray photos = null;
        if(locationJSON.has("photos")){

            photos = locationJSON.getJSONArray("photos");

            String height_param = VictorConstants.MAX_HEIGHT_PARAM;
            String width_param = VictorConstants.MAX_WIDTH_PARAM;
            String google_photo_url = VictorConstants.GOOGLE_PHOTO_URL;
            String photoreference_param = VictorConstants.PHOTO_REFERENCE_PARAM;
            String search_api_key = VictorConstants.API_KEY;
            String key_param = VictorConstants.KEY_PARAM;

            for (int i = 0; i < photos.length(); i++) {

                JSONObject htmlAttributions = photos.getJSONObject(i);
                String height = null;
                if(htmlAttributions.has("height")){
                    height = htmlAttributions.optString("height");
                }

                Log.d(TAG,"height "+height);

                String width = null;
                if(htmlAttributions.has("width")){
                    width = htmlAttributions.optString("width");
                }
                Log.d(TAG,"width "+width);


                String photoReference = null;
                if(htmlAttributions.has("photo_reference")){
                    photoReference = htmlAttributions.optString("photo_reference");
                }

                Log.d(TAG,"height "+width);

                String photoUrl = google_photo_url + height_param + height + width_param + width + photoreference_param + photoReference + key_param+  search_api_key;
                placesPhotos.add(photoUrl);
            }

        }

        String id = null;
        if(locationJSON.has("id")){
            id = locationJSON.optString("id");
        }

        String reference = null;
        if(locationJSON.has("reference")){
            reference = locationJSON.optString("reference");
        }


        LocationModel place = new LocationModel();
        place.setLocationName(locationName);
        place.setLocationDescription(locationDescription);
        place.setLocationRatings(locationratings);
        place.setLocationAddress(locationVicinity);
        place.setLocationCoordinates(latLng);
        place.setIcon(icon);
        place.setPriceRange(price_level);
        place.setPlacePhotos(placesPhotos);
        place.setId(id);
        place.setReference(reference);
        return place;
    }
}
