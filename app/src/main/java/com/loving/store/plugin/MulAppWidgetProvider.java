package com.loving.store.plugin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.loving.store.MainActivity;
import com.loving.store.R;
import com.loving.store.service.MapService;
import com.loving.store.service.RomoteService;
import com.loving.store.utils.Utils;

/**
 * Implementation of App Widget functionality.
 */
public class MulAppWidgetProvider extends AppWidgetProvider {

    public static final String CHANGE_IMAGE = "com.example.joy.action.CHANGE_IMAGE";

    private RemoteViews mRemoteViews;
    private ComponentName mComponentName;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e("aaaaaaaaaaaaaaaa", "1111111111111");

        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.mul_app_widget_provider);
        mRemoteViews.setImageViewResource(R.id.iv_test, R.mipmap.a1);
        mRemoteViews.setTextViewText(R.id.btn_test, Utils.getSystemDate("HH:mm"));
        Intent skipIntent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 200, skipIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_test, pi);

        mComponentName = new ComponentName(context, MulAppWidgetProvider.class);
        appWidgetManager.updateAppWidget(mComponentName, mRemoteViews);

        Intent intent = new Intent(context, MapService.class);
        PendingIntent refreshIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC, 0, 10000, refreshIntent);
        context.startService(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e("aaaaaaaaaaaaaaaa", "22222222222222");
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.mul_app_widget_provider);
        mRemoteViews.setTextViewText(R.id.btn_test, Utils.getSystemDate("HH:mm"));
        Intent skipIntent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 200, skipIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_test, pi);

        Intent mapService = new Intent(context, MapService.class);
        context.startService(mapService);
        Intent romoteService = new Intent(context, RomoteService.class);
        context.startService(romoteService);
        Utils.saveData();
    }
}

