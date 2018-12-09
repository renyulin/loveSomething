package com.loving.store.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.loving.store.utils.Utils;

import java.io.File;

public class OnePxActivity extends Activity {
    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 收到广播
            OnePxActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        // 设置窗口位置在左上角
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = 1;
        params.height = 1;
        window.setAttributes(params);

        // 动态注册广播，这个广播是在屏幕亮的时候，发送广播，来关闭当前的Activity
        registerReceiver(receiver, new IntentFilter("FinishOnePxActivity"));
        initBroadCast();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        unregisterReceiver(mapChangeReceiver);
        super.onDestroy();
    }

    MapChangeReceiver mapChangeReceiver;

    private void initBroadCast() {
        mapChangeReceiver = new MapChangeReceiver();
        IntentFilter filter = new IntentFilter("com.loving.store.mapChange");
        registerReceiver(mapChangeReceiver, filter);
    }

    class MapChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("ddddddddddddddddddddd", "5555555555");
            BDLocation bdLocation = intent.getParcelableExtra("location");
            if (bdLocation == null) {
                return;
            }
            Log.e("ddddddddddddddddddddd", "666666666");
            if (file == null) {
                initFile();
            }
            Utils.writeInFileByfb(file, "OnePxActivity：" + Utils.getSystemDate() + "\r\n");
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
            e.printStackTrace();
        }
    }
}
