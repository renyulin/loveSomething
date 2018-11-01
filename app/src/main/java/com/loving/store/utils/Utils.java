package com.loving.store.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
}
