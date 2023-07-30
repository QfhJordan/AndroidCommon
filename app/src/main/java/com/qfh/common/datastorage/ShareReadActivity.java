package com.qfh.common.datastorage;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qfh.common.R;

import java.util.Map;

public class ShareReadActivity extends AppCompatActivity {
    private TextView tv_share; // 声明一个文本视图对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_read);
        tv_share = findViewById(R.id.tv_share);
        readSharedPreferences(); // 从共享参数中读取信息
    }

    // 从共享参数中读取信息
    @SuppressLint("DefaultLocale")
    private void readSharedPreferences() {
        // 从share.xml中获取共享参数对象
        SharedPreferences share = getSharedPreferences("share", MODE_PRIVATE);
        String desc = "共享参数中保存的信息如下：";
        // 获取共享参数保存的所有映射配对信息
        Map<String, Object> mapParam = (Map<String, Object>) share.getAll();
        // 遍历该映射对象，并将配对信息形成描述文字
        for (Map.Entry<String, Object> stringObjectEntry : mapParam.entrySet()) {
            String key = stringObjectEntry.getKey();
            Object value = stringObjectEntry.getValue();
            if (value instanceof String) {
                desc = String.format("%s\n %s的取值为%s", desc, key, share.getString(key, ""));
            } else if (value instanceof Integer) {
                desc = String.format("%s\n %s的取值为%d", desc, key, share.getInt(key, 0));
            } else if (value instanceof Float) {
                desc = String.format("%s\n %s的取值为%f", desc, key, share.getFloat(key, 0.0f));
            } else if (value instanceof Boolean) {
                desc = String.format("%s\n %s的取值为%b", desc, key, share.getBoolean(key, false));
            } else if (value instanceof Long) {
                desc = String.format("%s\n %s的取值为%d", desc, key, share.getLong(key, 0));
            } else { // 如果配对值的类型为未知类型
                desc = String.format("%s\n参数%s的取值为未知类型", desc, key);
            }
        }
        if (mapParam.size() <= 0) {
            desc = "共享参数中保存的信息为空";
        }
        tv_share.setText(desc);
    }
}
