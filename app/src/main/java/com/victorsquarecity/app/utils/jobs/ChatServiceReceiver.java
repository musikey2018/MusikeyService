package com.victorsquarecity.app.utils.jobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by bilaw on 6/19/2017.
 */

public class ChatServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ChatJobSchedular.scheduleJob(context);
    }
}