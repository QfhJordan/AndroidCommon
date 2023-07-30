package com.qfh.common.datastorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qfh.common.R;
import com.qfh.common.datastorage.utils.DateUtil;
import com.qfh.common.datastorage.utils.ToastUtil;

/**
 * @author MagicBook
 */
public class ShareWriteActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {
    private SharedPreferences mShared;
    private EditText et_name; // 声明一个编辑框对象
    private EditText et_age; // 声明一个编辑框对象
    private EditText et_height; // 声明一个编辑框对象
    private EditText et_weight; // 声明一个编辑框对象
    private boolean isMarried = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_write);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        CheckBox ck_married = findViewById(R.id.ck_married);
        ck_married.setOnCheckedChangeListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        mShared = getSharedPreferences("share", MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            String name = et_name.getText().toString();
            String age = et_age.getText().toString();
            String height = et_height.getText().toString();
            String weight = et_weight.getText().toString();
            if (TextUtils.isEmpty(name)) {
                ToastUtil.show(this, "请先填写姓名");
            } else if (TextUtils.isEmpty(age)) {
                ToastUtil.show(this, "请先填写年龄");
            } else if (TextUtils.isEmpty(height)) {
                ToastUtil.show(this, "请先填写身高");
            } else if (TextUtils.isEmpty(weight)) {
                ToastUtil.show(this, "请先填写体重");
            }
            SharedPreferences.Editor edit = mShared.edit();
            edit.putString("name", name);
            edit.putInt("age", Integer.parseInt(age));
            edit.putLong("height", Long.parseLong(height));
            edit.putFloat("weight", Float.parseFloat(weight));
            edit.putBoolean("married", isMarried);
            edit.putString("update_time", DateUtil.getNowDataTime("yyyy-MM-dd HH:mm:ss"));
            edit.apply();
            ToastUtil.show(this,"数据已写入共享参数");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isMarried = isChecked;
    }
}
