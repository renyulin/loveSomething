package com.loving.store.service;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceAliveUtils {

    public static boolean isServiceAlice(Context context) {
        boolean isServiceRunning = false;
        ActivityManager manager =
                (ActivityManager) context. getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return true;
        }
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("demo.lgm.com.keepalivedemo.service.DownloadService".equals(service.service.getClassName())) {
                isServiceRunning = true;
            }
        }
        return isServiceRunning;
    }
}
