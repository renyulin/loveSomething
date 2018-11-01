package com.loving.store.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "love_something", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //date 具体日期 upTime 上传日期 reason 收入/支出原因 status 0本地 1上传 2删除
        //path 本地路径 urlPath线上路径 consumeTime 消耗掉的时间 payId收入/支出id pathId图片id
        db.execSQL("create table income(incomeId vachare(36) primary key,date datetime," +
                "upTime datetime,reason vachare(2000),consumeTime integer,status integer)");
        db.execSQL("create table expenditure(incomeId vachare(36) primary key,date datetime," +
                "upTime datetime,reason vachare(2000),consumeTime integer,status integer)");
        db.execSQL("create table img(imgId vachare(36) primary key,path vachare(200),urlPath varchare(200))");
        db.execSQL("create table link(linkId integer primary key autoincrement ,payId vachare(36),pathId vachare(36))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//ALTER TABLE oo ADD talknumber varchar(20) default 0
    }
}
