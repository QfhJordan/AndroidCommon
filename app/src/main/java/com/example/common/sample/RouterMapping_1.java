package com.example.common.sample;

import java.util.HashMap;
import java.util.Map;

public class RouterMapping_1 {

    public static Map<String, String> get() {

        Map<String,String> mapping = new HashMap<>();

        mapping.put("router://page-home", "com.example.common.activity.JavaTesActivity");
        mapping.put("router://kotlin-page-home", "com.example.common.activity.DebugActivity");
        return mapping;
    }
}
