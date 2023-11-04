package com.example;

import android.app.Application;

import cn.feng.skin.manager.loader.SkinManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Must call init first
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
    }

}
