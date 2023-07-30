package com.qfh.common.datastorage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.qfh.common.R;
import com.qfh.common.datastorage.bean.UserInfo;

public class ContentWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ContentWriteActivity";
    private EditText et_name; // 声明一个编辑框对象
    private EditText et_age; // 声明一个编辑框对象
    private EditText et_height; // 声明一个编辑框对象
    private EditText et_weight; // 声明一个编辑框对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_write);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        findViewById(R.id.btn_add_user).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_user) {
            UserInfo user = new UserInfo();
            user.name = et_name.getText().toString();
            user.age = Integer.parseInt(et_age.getText().toString());
            user.height = Integer.parseInt(et_height.getText().toString());
            user.weight = Float.parseFloat(et_weight.getText().toString());
            addUser(user); // 添加一条用户记录
        }
    }

    private void addUser(UserInfo user) {

    }
}
