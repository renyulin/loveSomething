package com.loving.store.base;

import android.app.Application;

public class MApplication extends Application {
    public static MApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
