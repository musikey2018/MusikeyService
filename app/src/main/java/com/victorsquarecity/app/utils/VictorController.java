package com.victorsquarecity.app.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.victorsquarecity.app.utils.pojo.LocationReview;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by mujahidmasood on 29.08.17.
 */

public class VictorController<T> {

    private static final String TAG = "VictorController";
    private static VictorController instance = new VictorController();

    private VictorController() {
    }


    public String sendVolleyRequest(Context context, final String url,final String placeid) {

        Log.d(TAG, "Entering sendVolleyRequest");

        Log.d(TAG, "Request Url " + url);

        String response;
        RequestQueue queue = Volley.newRequestQueue(context);
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> requestParameters = new HashMap<>();
                requestParameters.put(VictorConstants.KEY_PARAM, VictorConstants.API_KEY);
                requestParameters.put(VictorConstants.PLACE_ID_PARAM, placeid);
                Log.d(TAG, "Request Parameters " + requestParameters);
                return requestParameters;
            }

        };

        queue.add(request);

        Log.d(TAG,"Request sent "+request);
        try {
            response = future.get(VictorConstants.REQUEST_TIMEOUT, TimeUnit.SECONDS);
            Log.d(TAG, "Response from Request " + response);

        } catch (Exception e) {
            Log.e(TAG, "Exception while volley request " + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        HelperFactory.setRetryPolicy(request);

        Log.d(TAG, "Exiting sendVolleyRequest");
        return response;
    }


    public static VictorController getVictorController() {
        return instance;
    }

    public List<LocationReview> getLocationReview(String result) {

        List<LocationReview> locationReviews = new ArrayList<>();
        Log.d(TAG, "Entering getLocationReviw");
        try {

            JSONObject json_response = new JSONObject(result);
            String response_code = (String) json_response.get(ResponseModelJSON.RESPONSE);
            if (VictorConstants.TRUE.equalsIgnoreCase(response_code)) {
                JSONArray reviews = json_response.getJSONArray("reviews");
                for (int i = 0; i < reviews.length(); i++) {

                    JSONObject review = reviews.getJSONObject(0);

                    String author_name = null;
                    if (review.has("author_name")) {
                        author_name = review.optString("author_name");
                    }

                    String author_url = null;
                    if (review.has("author_url")) {
                        author_url = review.optString("author_url");
                    }

                    String language = null;
                    if (review.has("language")) {
                        language = review.optString("language");
                    }

                    String profile_photo_url = null;
                    if (review.has("profile_photo_url")) {
                        profile_photo_url = review.optString("profile_photo_url");
                    }

                    String rating = null;
                    if (review.has("rating")) {
                        rating = review.optString("rating");
                    }

                    String relative_time_description = null;
                    if (review.has("relative_time_description")) {
                        relative_time_description = review.optString("relative_time_description");
                    }

                    String text = null;
                    if (review.has("text")) {
                        text = review.optString("text");
                    }

                    String time = null;
                    if (review.has("time")) {
                        time = review.optString("time");
                    }

                    LocationReview locationReview = new LocationReview();
                    locationReview.setAuthor_name(author_name);
                    locationReview.setLanguage(language);
                    locationReview.setAuthor_url(author_url);
                    locationReview.setText(text);
                    locationReview.setTime(time);
                    locationReview.setRating(rating);
                    locationReview.setProfile_photo_url(profile_photo_url);
                    locationReview.setRelative_time_description(relative_time_description);
                    locationReviews.add(locationReview);

                }
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        Log.d(TAG, "Exiting getLocationReviw");
        return locationReviews;
    }


}
