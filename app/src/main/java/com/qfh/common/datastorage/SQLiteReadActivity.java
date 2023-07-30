package com.qfh.common.datastorage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qfh.common.R;
import com.qfh.common.datastorage.bean.UserInfo;
import com.qfh.common.datastorage.database.UserDBHelper;
import com.qfh.common.datastorage.utils.ToastUtil;

import java.util.List;

public class SQLiteReadActivity extends AppCompatActivity implements View.OnClickListener {
    private UserDBHelper mHelper; // 声明一个用户数据库帮助器的对象
    private TextView tv_sqlite; // 声明一个文本视图对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_read);
        tv_sqlite = findViewById(R.id.tv_sqlite);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_delete) {
            mHelper.closeLink(); // 关闭数据库连接
            mHelper.openWriteLink(); // 打开数据库帮助器的写连接
            mHelper.deleteAll(); // 删除所有记录
            mHelper.closeLink(); // 关闭数据库连接
            mHelper.openReadLink(); // 打开数据库帮助器的读连接
            readSQLite(); // 读取数据库中保存的所有用户记录
            ToastUtil.show(this, "已删除所有记录");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = UserDBHelper.getInstance(this, 1);
        mHelper.openReadLink();
        readSQLite();
    }

    private void readSQLite() {
        if (mHelper == null){
            ToastUtil.show(this,"数据库连接为空");
        }
        // 执行数据库帮助器的查询操作
        List<UserInfo> userList = mHelper.query("1=1");
        String desc = String.format("数据库查询到%d条记录，详情如下：", userList.size());
        for (int i = 0; i < userList.size(); i++) {
            UserInfo info = userList.get(i);
            desc = String.format("%s\n第%d条记录信息如下：", desc, i + 1);
            desc = String.format("%s\n　姓名为%s", desc, info.name);
            desc = String.format("%s\n　年龄为%d", desc, info.age);
            desc = String.format("%s\n　身高为%d", desc, info.height);
            desc = String.format("%s\n　体重为%f", desc, info.weight);
            desc = String.format("%s\n　婚否为%b", desc, info.married);
            desc = String.format("%s\n　更新时间为%s", desc, info.update_time);
        }
        if (userList.size() <= 0) {
            desc = "数据库查询到的记录为空";
        }
        tv_sqlite.setText(desc);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }
}
