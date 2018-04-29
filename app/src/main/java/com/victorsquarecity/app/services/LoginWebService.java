package com.victorsquarecity.app.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.victorsquarecity.app.utils.VictorConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by bilaw on 6/6/2017.
 */

public class LoginWebService {

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_OLD_PASSWORD = "oldpassword";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_AGE = "age";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_IMAGE_URL = "imageURL";
    private static final String KEY_VERIFICATION_CODE = "verificationCode";

    private static final String HOST_URL = VictorConstants.HOST_URL;
    private static final String WS_LOGIN = "/login";
    private static final String WS_SEND_FORGOT_MAIL = "/forgotpassword";
    private static final String WS_REGISTER = "/register";
    private static final String WS_UPDATE_PROFILE = "/updateprofile";
    private static final String WS_UPDATE_PASSWORD = "/updatepassword";

    private static LoginWebService instance = new LoginWebService();
    private static Boolean isRunning = false;


    private static String resp;
    private static int REQUEST_TIMEOUT = 10;

    private LoginWebService() {
        // Disallow this
    }


    public static String loginUser(final Context context, final String username, final String password, final String email) {
        if (isRunning) {
            return null;
        }

        isRunning = true;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = HOST_URL + WS_LOGIN;
        // Request a string response from the provided URL.
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_EMAIL, email);
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

    public static String sendUserForgotPasswordMail(final Context context, final String email) {
        if (isRunning) {
            return null;
        }

        isRunning = true;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = HOST_URL + WS_SEND_FORGOT_MAIL+"/"+email;
        // Request a string response from the provided URL.
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, future, future)

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
             //   params.put(KEY_EMAIL, email);
                return null;
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



    public static String verifyCode(final Context context, final String email, final String password, final String verificationCode) {
        if (isRunning) {
            return null;
        }

        isRunning = true;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = HOST_URL + VictorConstants.WS_RESET_PASSWORD;
        // Request a string response from the provided URL.
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, future, future)

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                  params.put(KEY_EMAIL, email);
                params.put(KEY_VERIFICATION_CODE, verificationCode);
                params.put(KEY_PASSWORD,password );
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

    public static synchronized LoginWebService getInstance() {
        if (instance == null)
            instance = new LoginWebService();

        return instance;
    }


    public static String registerUser(final Context context, final String email, final String password, final String username, final String number, final String age, final String city, final String state) {
        if (isRunning) {
            return null;
        }

        isRunning = true;
        //HOST_URL = ;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = HOST_URL + WS_REGISTER;//"http://192.168.0.100:3030" + WS_REGISTER;

        // Request a string response from the provided URL.
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_EMAIL, email);
                params.put(KEY_NUMBER, number);
                params.put(KEY_AGE, age);
                params.put(KEY_CITY, city);
                params.put(KEY_STATE, state);
                params.put(KEY_IMAGE_URL, "http://res.cloudinary.com/dsmd1d21f/image/upload/v1503792841/profile_ltixdv.png");
                return params;
            }

        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        try {
            resp = future.get(REQUEST_TIMEOUT, TimeUnit.SECONDS);
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

    public static String updateUserProfile(final Context context, final String email, final String username, final String number, final String age, final String city, final String state) {
        if (isRunning) {
            return null;
        }

        isRunning = true;
        //HOST_URL = ;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = HOST_URL + WS_UPDATE_PROFILE;//"http://192.168.0.100:3030" + WS_REGISTER;

        // Request a string response from the provided URL.
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_EMAIL, email);
                params.put(KEY_NUMBER, number);
                params.put(KEY_AGE, age);
                params.put(KEY_CITY, city);
                params.put(KEY_STATE, state);
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

    public static String updateUserProfilePassword(final Context context, final String email, final String password, final String oldpassword) {
        if (isRunning) {
            return null;
        }

        isRunning = true;
        //HOST_URL = ;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = HOST_URL + WS_UPDATE_PASSWORD;//"http://192.168.0.100:3030" + WS_REGISTER;

        // Request a string response from the provided URL.
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_PASSWORD, password);
                params.put(KEY_OLD_PASSWORD, oldpassword);
                params.put(KEY_EMAIL, email);
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


}

