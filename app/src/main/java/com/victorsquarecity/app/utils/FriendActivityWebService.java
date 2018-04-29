package com.victorsquarecity.app.utils;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.victorsquarecity.app.utils.VictorConstants.REQUEST_TIMEOUT;

/**
 * Created by app 5/29/17.
 */
public class FriendActivityWebService {

    private static FriendActivityWebService instance;
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private static final String TAG = "FriendWebService";
    private static ArrayList<UserModel> searchuserList = new ArrayList<>();

    

    private static Boolean isRunning = false;
    private static String resp;
    private static int REQUEST_TIMEOUT = 10;

    private FriendActivityWebService(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static void searchusers(Context context,String useremail) {


        Log.d(TAG, "onCreateView()");
        if (isRunning) {
            return;
        }

        isRunning = true;
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            String user_email = URLEncoder.encode(useremail, "utf-8");
            String WS_SEARCH_USER ="/friendlist/";
            String url = VictorConstants.HOST_URL + WS_SEARCH_USER +
                    //"email=" +
                    useremail;

            Log.d("/friendlist url", url);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("/friendlist Response ", "creating searched view "+response);
                            searchuserList = createUserListFromJSON(response);
                            Log.d(TAG, "billy creating searched view /friendlist Response array size"+searchuserList.size());
                            isRunning = false;

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Error handler
                    Log.d("/friendlist volleyerror", error.toString());
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
            isRunning = false;
            //Thread.sleep(3000);


        } catch (Exception ex) {

        }
//        String response = searchusersbyemail(context, useremail);
//        Log.d("/friendlist Response ", response);
//        searchuserList = createUserListFromJSON(response);
        Log.d(TAG, "billy /friendlist Response array size"+searchuserList.size());
        //return searchuserList;

    }

    public static String searchusersbyemail(final Context context, final String useremail) {
        if (isRunning) {
            return null;
        }

        isRunning = true;
        try {
        RequestQueue queue = Volley.newRequestQueue(context);
        //String user_email = URLEncoder.encode(useremail, "utf-8");
        String WS_SEARCH_USER ="/friendlist/";
        String url = VictorConstants.HOST_URL + WS_SEARCH_USER +useremail;
            Log.d(TAG, "billy /friendlist req url: "+url);
        // Request a string response from the provided URL.
        RequestFuture<String> future = RequestFuture.newFuture();
        //StringRequest stringRequest = new StringRequest(Request.Method.GET, url, future, future);
            StringRequest stringRequest = new StringRequest(url,future,future);
// Add the request to the RequestQueue.
        queue.add(stringRequest);

            isRunning = false;
            //resp = future.get(2, TimeUnit.SECONDS);// this will block (forever)}
            return future.get(2, TimeUnit.SECONDS);// this will block (forever)
            //Log.d(TAG, "billy /friendlist Response array"+resp.toString());

            //return resp;

        } catch (InterruptedException e) {
            // exception handling
        } catch (ExecutionException e) {
            // exception handling
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

//        isRunning = false;
//        Log.d(TAG, "billy /friendlist Response array"+resp);
//        searchuserList = createUserListFromJSON(resp);
//        Log.d(TAG, "billy /friendlist Response array"+resp+" size"+searchuserList.size());
        return null;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /*public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }*/

    public static FriendActivityWebService getInstance(Context ctx) {
        if (instance == null)
            instance = new FriendActivityWebService(ctx);

        return instance;
    }

    public static ArrayList createUserListFromJSON(String usersJSON) {
        Log.d(TAG, "createUserListFromJSON() started" + usersJSON);
        ArrayList<UserModel> usersArrayList = new ArrayList<>();


        try {
            JSONObject response = new JSONObject(usersJSON);
            JSONArray jsonUsersArray = response.getJSONArray("responseData");
            Log.d("billyfriendlist",jsonUsersArray.toString());

            for (int index = 0; index < jsonUsersArray.length(); index++) {
                JSONObject userJSON = jsonUsersArray.getJSONObject(index);

                Log.d(TAG, "createUserListFromJSON() /friendlist usersJSON " + usersJSON);

                UserModel newUser = createUserFromJSON(userJSON);

                Log.d(TAG, "createLocationsFromJSON() /friendlist UserModel " + newUser.toString());
                usersArrayList.add(newUser);
            }


        } catch (Exception ex) {
            Log.e("/friendlist ex ", ex.getMessage());
        }
        Log.d(TAG, "billy /friendlist array size"+usersArrayList.size());
        return usersArrayList;
    }



    public static UserModel createUserFromJSON(JSONObject userJSON) throws JSONException {

        String address = null, email = null, mobilenumber = null, username = null, city= null, imageurl =null, profileid =null, state =null, age =null;

        if (userJSON.has(UserModelJSON.IMAGE_URL)) {
            Log.d("",userJSON.optString(UserModelJSON.IMAGE_URL));
            imageurl =userJSON.optString(UserModelJSON.IMAGE_URL);
        }

        if (userJSON.has(UserModelJSON.PROFILE_ID)) {
            Log.d("",userJSON.optString(UserModelJSON.PROFILE_ID));
            profileid =userJSON.optString(UserModelJSON.PROFILE_ID);
        }

        if (userJSON.has(UserModelJSON.ADDRESS)) {
            Log.d("",userJSON.optString(UserModelJSON.ADDRESS));
            address = userJSON.optString(UserModelJSON.ADDRESS);
        }

        if (userJSON.has(UserModelJSON.AGE)) {
            Log.d("",userJSON.optString(UserModelJSON.AGE));
            age = userJSON.optString(UserModelJSON.AGE);
        }

        if (userJSON.has(UserModelJSON.USERNAME)) {
            Log.d("",userJSON.optString(UserModelJSON.USERNAME));
            username = userJSON.optString(UserModelJSON.USERNAME);
        }

        if (userJSON.has(UserModelJSON.EMAIL)) {
            Log.d("",userJSON.optString(UserModelJSON.EMAIL));
            email =userJSON.optString(UserModelJSON.EMAIL);
        }

        if (userJSON.has(UserModelJSON.MOBILE_NUMBER)) {
            Log.d("",userJSON.optString(UserModelJSON.MOBILE_NUMBER));
            mobilenumber =userJSON.optString(UserModelJSON.MOBILE_NUMBER);
        }

        if (userJSON.has(UserModelJSON.IMAGE_URL)) {
            Log.d("",userJSON.optString(UserModelJSON.IMAGE_URL));
            imageurl =userJSON.optString(UserModelJSON.IMAGE_URL);
        }

        if (userJSON.has(UserModelJSON.PROFILE_ID)) {
            Log.d("",userJSON.optString(UserModelJSON.PROFILE_ID));
            profileid =userJSON.optString(UserModelJSON.PROFILE_ID);
        }

        if (userJSON.has(UserModelJSON.CITY)) {
            Log.d("",userJSON.optString(UserModelJSON.CITY));
            city =userJSON.optString(UserModelJSON.CITY);
        }

        if (userJSON.has(UserModelJSON.STATE)) {
            Log.d("",userJSON.optString(UserModelJSON.STATE));
            state =userJSON.optString(UserModelJSON.STATE);
        }


        UserModel newUser = new UserModel();
        newUser.setmAddress(address);
        newUser.setmCity(city);
        newUser.setmState(state);
        newUser.setmEmail(email);
        newUser.setmImageUrl(imageurl);
        newUser.setmAge(age);
        newUser.setmMobileNumber(mobilenumber);
        newUser.setmProfileID(profileid);
        newUser.setmUserName(username);
        return newUser;
    }

    public static ArrayList<UserModel> getSearchuserList() {
        return searchuserList;
    }
}

