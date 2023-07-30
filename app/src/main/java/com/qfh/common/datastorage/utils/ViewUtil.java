package com.qfh.common.datastorage.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewUtil {
    public static void hideAllInputMethod(Context context) {
        InputMethodManager systemService = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (systemService.isActive()) {
            systemService.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideOneInputMethod(Context context, View v) {
        // 从系统服务中获取输入法管理器
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 关闭屏幕上的输入法软键盘
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
