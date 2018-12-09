package com.loving.store.service;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.loving.store.IMyAidlInterface;
import com.loving.store.MainActivity;
import com.loving.store.R;
import com.loving.store.utils.Utils;

import java.io.File;

public class MapService extends Service {
  private   LocationClient mLocClient;
    private MyLocationListener myListener = new MyLocationListener();
    private  ScreenStatusReceiver mScreenStatusReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mScreenStatusReceiver == null) {
            mScreenStatusReceiver = new ScreenStatusReceiver();
            IntentFilter screenStatus = new IntentFilter();
            screenStatus.addAction(Intent.ACTION_SCREEN_OFF);

            screenStatus.addAction(Intent.ACTION_USER_PRESENT);//解锁

            registerReceiver(mScreenStatusReceiver, screenStatus);
        }
        if (binder == null)
            binder = new MyBinder();
        if (conn == null)
            conn = new MyConn();
//        Toast.makeText(MapService.this, "本地服务活了", Toast.LENGTH_SHORT).show();

        boolean isServiceRunning = ServiceAliveUtils.isServiceAlice(getApplicationContext());
        if (!isServiceRunning) {
            //开启远程服务
            MapService.this.startService(new Intent(MapService.this, RomoteService.class));
        }
        //绑定远程服务
        MapService.this.bindService(new Intent(MapService.this, RomoteService.class), conn, Context.BIND_IMPORTANT);
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.mul_app_widget_provider);
        mRemoteViews.setTextViewText(R.id.btn_test, Utils.getSystemDate("HH:mm"));
        useJobServiceForKeepAlive();
        return START_STICKY;
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e("ddddddddddddddddddddd", "111111111");
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            Log.e("ddddddddddddddddddddd", "222222222");
            Intent intent = new Intent("com.loving.store.mapChange");
            intent.putExtra("location", location);
            sendBroadcast(intent);
//            mCurrentLat = location.getLatitude();
//            mCurrentLon = location.getLongitude();
//            mCurrentAccracy = location.getRadius();
            if (file == null) {
                initFile();
            }
            Utils.writeInFileByfb(file, "MapService：" + Utils.getSystemDate() + "\r\n");
        }
    }

    File file;

    private void initFile() {
        file = new File("/mnt/sdcard/loving", "log.txt");
        try {
            //判断文件是否存在
            if (file.exists()) {
                //文件如果存在删除这个文件
//                file.delete();
            } else {
                //创建一个新的文件
                file = new File("/mnt/sdcard/loving");
                //先创建文件夹，mkdirds可直接创建多级文件夹
                file.mkdirs();
                //创建这个文件
                file = new File("/mnt/sdcard/loving/log.txt");
                file.createNewFile();
            }
        } catch (Exception e) {
            Log.e("ddddddddddddddddddddde", e.toString());
            e.printStackTrace();
        }
    }

    class ScreenStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                // 当屏幕关闭时，启动一个像素的Activity
                Intent activity = new Intent(context, OnePxActivity.class);
                activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(activity);
            } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
                // 用户解锁，关闭Activity
                // 这里发个广播是什么鬼，其实看下面OnePxAcitivity里面的代码就知道了，发这个广播就是为了finish掉OnePxActivity
                Intent broadcast = new Intent("FinishOnePxActivity");
                // broadcast.setFlags(32);Intent.FLAG_INCLUDE_STOPPED_PACKAGES
                context.sendBroadcast(broadcast);//发送对应的广播
            }else if (action.equals(Intent.ACTION_USER_PRESENT)){
                Intent remote = new Intent(MapService.this, RomoteService.class);
                startService(remote);
            }
        }
    }

    MyBinder binder;
    MyConn conn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return MapService.class.getSimpleName();
        }
    }

    public boolean isConnect;

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("yangqing", "绑定上了远程服务");
            isConnect = true;
//            Toast.makeText(MapService.this, "绑定上了远程服务", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnect = false;
            Log.i("yangqing", "远程服务被干掉了");

//            Toast.makeText(MapService.this, "远程服务挂了", Toast.LENGTH_SHORT).show();

            //开启远程服务
            MapService.this.startService(new Intent(MapService.this, RomoteService.class));

            //绑定远程服务
            MapService.this.bindService(new Intent(MapService.this, RomoteService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    @Override
    public void onDestroy() {
        if (mScreenStatusReceiver != null) {
            unregisterReceiver(mScreenStatusReceiver);
        }
        //开启远程服务
        MapService.this.startService(new Intent(MapService.this, RomoteService.class));

        //绑定远程服务
        MapService.this.bindService(new Intent(MapService.this, RomoteService.class), conn, Context.BIND_IMPORTANT);
        super.onDestroy();
    }
    /**
     * 使用JobScheduler进行保活
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void useJobServiceForKeepAlive() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler == null) {
            return;
        }
        jobScheduler.cancelAll();
        JobInfo.Builder builder =
                new JobInfo.Builder(1024, new ComponentName(getPackageName(), ScheduleService.class.getName()));
        //Android 7.0+ 增加了一项针对 JobScheduler 的新限制，最小间隔只能是下面设定的数字
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(JobInfo.getMinPeriodMillis(), JobInfo.getMinFlexMillis());
        }else {
            //周期设置为了2s
            builder.setPeriodic(1000 * 2);
        }
        builder.setPersisted(true);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        int schedule = jobScheduler.schedule(builder.build());
        if (schedule <= 0) {

        }
    }
}
