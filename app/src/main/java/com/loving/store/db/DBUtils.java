package com.loving.store.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动关于点赞的操作
 */
public class DBUtils {
    private Context context;
    private DBHelper help;

    public DBUtils(Context context) {
        this.context = context;
        help = new DBHelper(context);
    }

    public void addLike(String programId, String isLike) {
        SQLiteDatabase write = help.getWritableDatabase();
        write.execSQL(" insert into actlike(programId, isLike) values(?,?) ", new Object[]{programId, isLike});
        write.close();
    }

    public void addLike(Map<String, Object> map) {
        this.addLike((String) map.get("programId"), (String) map.get("isLike"));
    }

    public List<Map<String, Object>> allLike() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        SQLiteDatabase read = help.getReadableDatabase();
        Cursor cursor = read.rawQuery(" select * from actlike ", null);
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            String programId = cursor.getString(cursor.getColumnIndex("programId"));
            int isLike = cursor.getInt(cursor.getColumnIndex("isLike"));
            map.put("programId", programId);
            map.put("isLike", isLike);
            mapList.add(map);
        }
        read.close();
        return mapList;
    }

    //sql = "select * from TableName where "+条件+" order by "+排序+" limit "+要显示多少条记录+" offset "+跳过多少条记录;
    public List<Map<String, Object>> getIncome(int page, int count) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        SQLiteDatabase read = help.getReadableDatabase();
        Cursor cursor = read.rawQuery(" select * from income order by id desc limit ? offset ?",
                new String[]{count + "", page * count + ""});
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String reason = cursor.getString(cursor.getColumnIndex("reason"));
            map.put("date", date);
            map.put("reason", reason);
            mapList.add(map);
        }
        read.close();
        return mapList;
    }

    public int getCount() {
        SQLiteDatabase read = help.getReadableDatabase();
        Cursor cursor = read.rawQuery(" select count (*) from actlike ", null);
//        read.rawQuery("select p.*,a.* from actlike as p left join  aaa as a on p.id=a.id  where a.id='asdsfsf' ", null);
//        cursor.moveToFirst();
        int count = cursor.getCount();
        read.close();
        return count;
    }

    public void deleteAll() {
        SQLiteDatabase write = help.getWritableDatabase();
        write.execSQL(" delete from actlike ");
        write.close();
    }

    public void searchAll() {
        SQLiteDatabase read = help.getReadableDatabase();
//        read.rawQuery("")
    }
}
