package com.victorsquarecity.app.utils;

/**
 * Created by mujahidmasood on 19.08.17.
 */

public interface VictorConstants {

    String HOST_URL = "https://victorcity.herokuapp.com";
    //String HOST_URL = "http://10.42.0.19:3000";
    String GOOGLE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?";
    String MAX_WIDTH_PARAM = "maxwidth";
    String MAX_HEIGHT_PARAM = "maxheight";
    String PHOTO_REFERENCE_PARAM = "photoreference";
    String KEY_PARAM = "key";
    String PLACE_ID_PARAM = "placeid";
    String TRUE = "true";
    String FALSE = "false";


    String PLACE_REVIEWS_REQUEST = "https://maps.googleapis.com/maps/api/place/details/output?";

    //REST SERVICES
    String WS_DOWNLOAD = "/download";
    String WS_GET_LOCATIONS = "/location";
    String WS_UPLOAD = "/uploadImage";
    String WS_RESET_PASSWORD = "/resetpassword";
    String SQL_DB_NAME = "victor-db";

    String WS_GET_USER_BY_EMAIL = "/users";
    int REQUEST_TIMEOUT = 10;
    String RESTURANT = "restaurant";
    String API_KEY = "AIzaSyDf5Pb_LqwneYxInGgDIqF9qeDzStceUCI";
    String PREFS_NAME = "VictorAppSharedPref";
    String APP_FILE = "Victor";
}
