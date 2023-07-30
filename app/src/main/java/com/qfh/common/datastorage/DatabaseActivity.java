package com.qfh.common.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qfh.common.R;

public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_database; // 声明一个文本视图对象
    private String mDatabaseName; // 包含完整路径的数据库名称

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        tv_database = findViewById(R.id.tv_database);
        findViewById(R.id.btn_database_create).setOnClickListener(this);
        findViewById(R.id.btn_database_delete).setOnClickListener(this);
        mDatabaseName = getFilesDir() + "/test.db";
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_database_create) {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(mDatabaseName, Context.MODE_PRIVATE, null);
            String desc = String.format("数据库%s创建%s", sqLiteDatabase.getPath(), (sqLiteDatabase != null) ? "成功" : "失败");
            tv_database.setText(desc);
        } else if (v.getId() == R.id.btn_database_delete) {
            boolean result = deleteDatabase(mDatabaseName); // 删除数据库
            String desc = String.format("数据库%s删除%s", mDatabaseName, result ? "成功" : "失败");
            tv_database.setText(desc);
        }
    }
}
