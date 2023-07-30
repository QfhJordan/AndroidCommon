package com.qfh.common.datastorage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qfh.common.R;
import com.qfh.common.datastorage.database.UserDBHelper;

public class LoginSQLiteActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private RadioGroup rg_login; // 声明一个单选组对象
    private RadioButton rb_password; // 声明一个单选按钮对象
    private RadioButton rb_verifycode; // 声明一个单选按钮对象
    private EditText et_phone; // 声明一个编辑框对象
    private TextView tv_password; // 声明一个文本视图对象
    private EditText et_password; // 声明一个编辑框对象
    private Button btn_forget; // 声明一个按钮控件对象
    private CheckBox ck_remember; // 声明一个复选框对象

    private int mRequestCode = 0; // 跳转页面时的请求代码
    private boolean isRemember = false; // 是否记住密码
    private String mPassword = "111111"; // 默认密码
    private String mVerifyCode; // 验证码
    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sqlite);
        rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        // 给rg_login设置单选监听器
        rg_login.setOnCheckedChangeListener(new RadioListener());
        // 给ck_remember设置勾选监听器
        ck_remember.setOnCheckedChangeListener((buttonView, isChecked) -> isRemember = isChecked);
        // 给et_phone添加文本变更监听器
//        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
//         给et_password添加文本变更监听器
//        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));
        btn_forget.setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        // 给密码编辑框注册一个焦点变化监听器，一旦焦点发生变化，就触发监听器的onFocusChange方法
        et_password.setOnFocusChangeListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
    // 定义登录方式的单选监听器
    private class RadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_password) { // 选择了密码登录
                tv_password.setText("登录密码：");
                et_password.setHint("请输入密码");
                btn_forget.setText("忘记密码");
                ck_remember.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rb_verifycode) { // 选择了验证码登录
                tv_password.setText("　验证码：");
                et_password.setHint("请输入验证码");
                btn_forget.setText("获取验证码");
                ck_remember.setVisibility(View.GONE);
            }
        }
    }
}
