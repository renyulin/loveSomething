package com.loving.store.service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScheduleService extends JobService {
    private static final String TAG = ScheduleService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {

        boolean isServiceRunning = ServiceAliveUtils.isServiceAlice(getApplicationContext());
        if (!isServiceRunning) {
            Intent i = new Intent(this, MapService.class);
            startService(i);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobFinished(params, false);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}