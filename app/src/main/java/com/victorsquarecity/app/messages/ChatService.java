package com.victorsquarecity.app.messages;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.victorsquarecity.app.utils.HelperFactory;

import java.util.concurrent.ExecutionException;

/**
 * Created by bilaw on 6/13/2017.
 */

public class ChatService  extends Service {

    private static final String TAG = "ChatService";
    private final IBinder mBinder = new MyChatBinder();
    private  final String myID = "app";
    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    public class MyChatBinder extends Binder {
        ChatService getService() {
            return ChatService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // String myID = "app";
        int i=0;

       AsyncTask msgs= new DoBackgroundTask();
        try {
            String result= (String)(msgs.execute(myID).get());
            Log.d(TAG,"results are"+result);
        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();

        }

       return START_NOT_STICKY;
    }

    private class DoBackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            final String userName = params[0];
            final String userID = params[1];
            final String friendUserID = params[2];
            Log.d(TAG, "myUserID ["+userID +"], userName["+userName+"], friendID["+friendUserID+"]");



            new Handler().postDelayed(new Runnable() {
                public void run() {
                    boolean isSuccess = HelperFactory.getChatMessages(getApplicationContext(), userName,userID,friendUserID);
                    Log.i(TAG, "task done");
                }
            }, 10000);
            Log.i(TAG, "task done");
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
        }
    }
}