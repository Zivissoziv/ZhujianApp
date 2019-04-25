package com.example.ziv.zhujiandemo.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作SharedPreferences的工具类
 * 主要功能是把类转化成String保存在SP中
 * 提供读写功能
 */

public class SpUtils {

    private static final String DEFAULT_SP_NAME = "default_sp";
    private static final String key = "record";

    public static<T> List<T> getObject(Context context,Class<T> cls) {
        List<T> datalist=new ArrayList<T>();
        String json = getString(context, key, null);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                datalist.add(gson.fromJson(jsonElement, cls));
            }

            return datalist;
        } catch (Exception e) {
            return null;
        }
    }

    public static<RouteType> void putObject(Context context, List<RouteType> datalist) {
//        if (null == datalist || datalist.size() <= 0)
//            return;
        Gson gson = new Gson();
        String json = gson.toJson(datalist);
        putString(context, key, json);
    }

    private static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }


}
