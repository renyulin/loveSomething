package com.loving.store.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.loving.store.R;
import com.loving.store.base.BaseActivity;
import com.loving.store.service.MapService;
import com.loving.store.service.RomoteService;
import com.loving.store.utils.Utils;

import java.io.File;

public class MapActivity extends BaseActivity implements SensorEventListener {
    // 定位相关
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    MapView mMapView;
    BaiduMap mBaiduMap;

    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化

        initFile();
//        mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        mLocClient.setLocOption(option);
//        mLocClient.start();
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

    private void initService() {
        Intent intent = new Intent(this, MapService.class);
        startService(intent);
        Intent romote = new Intent(this, RomoteService.class);
        startService(romote);
    }

    MapChangeReceiver receiver;
    IntentFilter filter;

    private void initBroadCast() {
        receiver = new MapChangeReceiver();
        filter = new IntentFilter("com.loving.store.mapChange");
        registerReceiver(receiver, filter);
    }

    class MapChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("ddddddddddddddddddddd", "3333333333");
            BDLocation bdLocation = intent.getParcelableExtra("location");
            if (bdLocation == null || mMapView == null) {
                return;
            }
            Log.e("ddddddddddddddddddddd", "44444444444");
            if (file == null) {
                initFile();
            }
            Utils.writeInFileByfb(file, "MapActivity：" + Utils.getSystemDate() + "\r\n");
            mCurrentLat = bdLocation.getLatitude();
            mCurrentLon = bdLocation.getLongitude();
            mCurrentAccracy = bdLocation.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

//    /**
//     * 定位SDK监听函数
//     */
//    public class MyLocationListener implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            Log.e("ddddddddddddddddddddd", "111111111");
//            // map view 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null) {
//                return;
//            }
//            Log.e("ddddddddddddddddddddd", "222222222");
//            mCurrentLat = location.getLatitude();
//            mCurrentLon = location.getLongitude();
//            mCurrentAccracy = location.getRadius();
//            locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(mCurrentDirection).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }
//        }
//    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        initBroadCast();
        initService();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
//        mLocClient.stop();
        // 关闭定位图层

        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onError(Object errorMsg, String code) {

    }

    @Override
    public void onSuccess(Object response, String code) {

    }
}
