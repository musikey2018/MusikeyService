package com.victorsquarecity.app.services;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.victorsquarecity.app.utils.HelperFactory;
import com.victorsquarecity.app.utils.VictorConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by mujahidmasood on 19.08.17.
 */

public class ImageService {


    private static final String TAG = "ImageService";
    private static ImageService instance;
    private static Context mCtx;
    private static boolean isRunning = false;
    static String resp = null;


    private ImageService(Context ctx) {
        mCtx = ctx;
    }


    public static ImageService getInstance(Context ctx) {
        if (instance == null)
            instance = new ImageService(ctx);

        return instance;
    }

    @Nullable
    public static String download(final String downloadUrl) {
        Log.d(TAG, "download() downloadUrl" + downloadUrl);

        if (isRunning) {
            return null;
        }

        isRunning = true;

        RequestQueue queue = Volley.newRequestQueue(mCtx);

        final String url = VictorConstants.HOST_URL + VictorConstants.WS_DOWNLOAD;
        Log.d(TAG, "download() DownloadWS URL " + url);

        final RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("url", downloadUrl);
                Log.d(TAG, "download() DownloadWS Params" + params.toString());
                return params;
            }

        };

        HelperFactory.setRetryPolicy(request);
        queue.add(request);
        try {
            resp = future.get(VictorConstants.REQUEST_TIMEOUT, TimeUnit.SECONDS);
            Log.d(TAG, "download() DownloadWS response " + resp);
            isRunning = false;
        } catch (Exception e) {
            Log.e(TAG, "download() DownloadWS error " + e.getMessage());
            throw new RuntimeException(e);
        }
        isRunning = false;


        return resp;
    }


}
