package com.loving.store.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.loving.store.IMyAidlInterface;
import com.loving.store.plugin.MulAppWidgetProvider;

public class RomoteService extends Service {
    MyConn conn;
    MyBinder binder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (binder == null)
            binder = new MyBinder();
        if (conn == null)
            conn = new MyConn();
//        Toast.makeText(this, "远程服务活了", Toast.LENGTH_SHORT).show();
        boolean isServiceRunning = ServiceAliveUtils.isServiceAlice(getApplicationContext());
        if (!isServiceRunning) {
            //开启本地服务
            RomoteService.this.startService(new Intent(RomoteService.this, MapService.class));
        }
        //绑定本地服务
        RomoteService.this.bindService(new Intent(RomoteService.this, MapService.class), conn, Context.BIND_IMPORTANT);
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        //发送唤醒广播来促使挂掉的UI进程重新启动起来
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent();
        alarmIntent.setAction(MulAppWidgetProvider.CHANGE_IMAGE);
        PendingIntent operation = PendingIntent.getBroadcast(this, WAKE_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, operation);
        return START_STICKY;
    }

    /**
     * 定时唤醒的时间间隔，5分钟
     */
    private final static int ALARM_INTERVAL = 5 * 60 * 1000;
    private final static int WAKE_REQUEST_CODE = 6666;

    private final static int GRAY_SERVICE_ID = -1001;


    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }


    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return RomoteService.class.getSimpleName();
        }
    }

    public boolean isConnect;

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnect = true;
            Log.i("yangqing", "绑定本地服务成功");
//            Toast.makeText(RomoteService.this, "绑定本地服务成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnect = false;
            Log.i("yangqing", "本地服务被干掉了");

//            Toast.makeText(RomoteService.this, "本地服务挂了", Toast.LENGTH_SHORT).show();

            //开启本地服务
            RomoteService.this.startService(new Intent(RomoteService.this, MapService.class));

            //绑定本地服务
            RomoteService.this.bindService(new Intent(RomoteService.this, MapService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    @Override
    public void onDestroy() {
        //开启本地服务
        RomoteService.this.startService(new Intent(RomoteService.this, MapService.class));

        //绑定本地服务
        RomoteService.this.bindService(new Intent(RomoteService.this, MapService.class), conn, Context.BIND_IMPORTANT);
        super.onDestroy();
    }
}
