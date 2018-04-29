package com.victorsquarecity.app.services;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.victorsquarecity.app.utils.LocationHelper;
import com.victorsquarecity.app.utils.LocationsFactory;
import com.victorsquarecity.app.utils.VictorConstants;

import java.net.URLEncoder;

/**
 * Created by app 5/29/17.
 */
public class LocationWebService {

    private static LocationWebService instance;
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private static final String TAG = "LocationWebService";

    private static Boolean isRunning = false;

    private LocationWebService(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }


    public static void refreshLocations(String keyword, String url_type, Context context) {

        Location location = new LocationHelper(context).getLocation();

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Log.d(TAG, "onCreateView() lat:" + latitude + ", lng :" + longitude);
        if (isRunning) {
            return;
        }

        isRunning = true;
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            String url_keyword = URLEncoder.encode(keyword, "utf-8");
            String url_latitude = Double.toString(latitude);
            String url_longitude = Double.toString(longitude);
            String apikey = VictorConstants.API_KEY;
            String url = VictorConstants.HOST_URL + VictorConstants.WS_GET_LOCATIONS + "?location=" +
                    url_latitude +
                    "," +
                    url_longitude +
                    "&radius=5000&type=" +
                    url_type +
                    "&keyword=" +
                    url_keyword +
                    "&key=" +
                    apikey;

            Log.d(TAG,"refreshLocations() location url"+ url);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG,"refreshLocations() Response "+ response);
                            LocationsFactory.createLocationsFromJSON(response);
                            isRunning = false;
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Error handler
                    Log.d(TAG,"refreshLocations() volleyerror"+ error.getMessage());
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
            isRunning = false;


        } catch (Exception ex) {

        }

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static LocationWebService getInstance(Context ctx) {
        if (instance == null)
            instance = new LocationWebService(ctx);

        return instance;
    }
}

