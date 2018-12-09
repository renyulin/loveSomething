package com.loving.store.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static String translateDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(date);//获取当前时间
        return formatter.format(curDate);
    }

    public static String getSystemDate() {
        return getSystemDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getSystemDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    public static long changeDateToTime(String date) {
        Calendar c = Calendar.getInstance();
        if (TextUtils.isEmpty(date)) {
            return 0;
        }
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTimeInMillis();
    }

    public static void loadImg(Context context, String path, String urlPath, ImageView img) {
        File file = new File(path);
        if (file.exists())
            Glide.with(context).load(path).into(img);
        else if (!TextUtils.isEmpty(urlPath))
            Glide.with(context).load(urlPath).into(img);
    }

    public static void writeInFileByfb(File f, String txt) {

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsoluteFile(), true);  //true表示可以追加新内容
            //fw=new FileWriter(f.getAbsoluteFile()); //表示不追加
            bw = new BufferedWriter(fw);
            bw.write(txt);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File initFile() {
        File file = new File("/mnt/sdcard/loving", "UIShow.txt");
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
                file = new File("/mnt/sdcard/loving/UIShow.txt");
                file.createNewFile();
            }
        } catch (Exception e) {
            Log.e("ddddddddddddddddddddde", e.toString());
            e.printStackTrace();
        }
        return file;
    }

    public static void saveData() {
        writeInFileByfb(initFile(), "MulAppWidgetProvider：" + getSystemDate() + "\r\n");
    }
}
