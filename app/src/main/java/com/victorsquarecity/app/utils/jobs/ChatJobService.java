package com.victorsquarecity.app.utils.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.victorsquarecity.app.messages.ChatService;

/**
 * Created by bilaw on 6/19/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ChatJobService extends JobService {
    private static final String TAG = "ChatSyncService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), ChatService.class);
        getApplicationContext().startService(service);
        ChatJobSchedular.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}