package com.qfh.common.datastorage.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    //获取当前日期时间
    public static String getNowDataTime(String formatStr) {
        String format = formatStr;
        if (TextUtils.isEmpty(formatStr)) {
            format = "yyyyMMddHHmmss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    // 获取当前的时间
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    // 获取当前的时间（精确到毫秒）
    public static String getNowTimeDetail() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        return simpleDateFormat.format(new Date());
    }

    public static String formatDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
