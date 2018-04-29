package com.victorsquarecity.app.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by app 5/29/17.
 */
public class ChatWebService {

    private static ChatWebService instance;
    private RequestQueue mRequestQueue;
    private Context mCtx;

    private static final String HOST_URL = "http://zstudiolabs.com";
    private static final String WS_GET_LOCATIONS = "/labs/cs402/buildings.json";
    private static final String apikey="AIzaSyDf5Pb_LqwneYxInGgDIqF9qeDzStceUCI";
    private static String url_userid = "userid";
    private static String url_username = "username";
    private static String url_friendid = "friendid";
    private static String url_type = "cafe";
    private static String resp;
    private  static int REQUEST_TIMEOUT = 10;

//    http://zstudiolabs.com/labs/cs402/buildings.json

    private static Boolean isRunning = false;

    private ChatWebService(Context ctx)
    {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
        // Disallow this
    }



    public static void setUrl_type(String url_type) {
        try {
            ChatWebService.url_type =  URLEncoder.encode(url_type, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String getChatMessages(Context context, final String myUserName, final String myUserId, final String mFriendID)
    {
        if( isRunning )
        {
            return null;
        }

        isRunning = true;

        String url = HOST_URL ;
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response from the provided URL.
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, future, future)

//        String url = HOST_URL + WS_GET_LOCATIONS;
//        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
//                url_latitude +
//                "," +
//                url_longitude +
//                "&radius=5000&type=" +
//                url_type +
//                "&keyword=" +
//                url_keyword +
//                "&key=" +
//                apikey;
//
//        Log.d("billy",url);
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("billy",response);
//                        LocationsFactory.createLocationsFromJSON(response);
//                        isRunning = false;
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // Error handler
//                Log.d("billy error",error.toString());
//            }
//        });
        {
            @Override
            protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put(url_username, myUserName);
            params.put(url_userid, myUserId);
            params.put(url_friendid, mFriendID);
            return params;
        }

        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        try {
            resp = future.get(REQUEST_TIMEOUT, TimeUnit.SECONDS);// this will block (forever)
            isRunning = false;
        } catch (InterruptedException e) {
            // exception handling
        } catch (ExecutionException e) {
            // exception handling
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        isRunning = false;
        return resp;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public static ChatWebService getInstance(Context ctx) {
        if (instance == null)
            instance = new ChatWebService(ctx);

        return instance;
    }
}

