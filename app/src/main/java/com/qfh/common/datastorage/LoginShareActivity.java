package com.qfh.common.datastorage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qfh.common.R;
import com.qfh.common.datastorage.utils.ViewUtil;

import java.util.Random;

public class LoginShareActivity extends AppCompatActivity implements View.OnClickListener {
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
    private SharedPreferences mShared; // 声明一个共享参数对象
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_share);
        rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);

        rg_login.setOnCheckedChangeListener(new RadioListener());
        ck_remember.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isRemember = isChecked;
        });
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 11));
        btn_forget.setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        mShared = getSharedPreferences("share_login", MODE_PRIVATE);
        String phone = mShared.getString("phone", "");
        String password = mShared.getString("password", "");
        et_phone.setText(phone);
        et_password.setText(password);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    // 用户密码已改为新密码，故更新密码变量
                    mPassword = result.getData().getStringExtra("new_password");
                }
            }
        });
        rb_password.setOnClickListener(v -> et_password.setText(""));
        rb_verifycode.setOnClickListener(v -> et_password.setText(""));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        et_password.setText("");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        if (v.getId() == R.id.btn_forget) {
            if (phone.length() < 11) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rb_password.isChecked()) {
                Intent intent = new Intent(this, LoginForgetActivity.class);
                intent.putExtra("phone", phone);
                launcher.launch(intent);
            } else if (rb_verifycode.isChecked()) {
                mVerifyCode = String.format("%06d", new Random().nextInt(999999));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
                builder.setPositiveButton("好的", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        } else if (v.getId() == R.id.btn_login) {
            if (phone.length() < 11) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rb_password.isChecked()) {
                if (!et_password.getText().toString().equals(mPassword)) {
                    Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                } else {
                    loginSuccess();
                }
            } else if (rb_verifycode.isChecked()) { // 验证码方式校验
                if (!et_password.getText().toString().equals(mVerifyCode)) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                } else { // 验证码校验通过
                    loginSuccess(); // 提示用户登录成功
                }
            }
        }
    }

    private void loginSuccess() {
        String desc = String.format("您的手机号码是%s，恭喜你通过登录验证，点击“确定”按钮返回上个页面", et_phone.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定返回", (dialog, which) -> LoginShareActivity.this.finish());
        builder.setNegativeButton("我再看看", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // 如果勾选了“记住密码”，就把手机号码和密码都保存到共享参数中
        if (isRemember) {
            SharedPreferences.Editor edit = mShared.edit();
            edit.putString("phone", et_phone.getText().toString());
            edit.putString("password", et_password.getText().toString());
            edit.apply();
        }
    }

    private class RadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_password) {
                tv_password.setText("登录密码：");
                et_password.setHint("请输入密码");
                btn_forget.setText("忘记密码");
                ck_remember.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rb_verifycode) {
                tv_password.setText("　验证码：");
                et_password.setHint("请输入验证码");
                btn_forget.setText("获取验证码");
                ck_remember.setVisibility(View.GONE);
            }
        }
    }

    private class HideTextWatcher implements TextWatcher {
        private EditText mView; // 声明一个编辑框对象
        private int mMaxLength; // 声明一个最大长度变量

        public HideTextWatcher(EditText mView, int mMaxLength) {
            super();
            this.mView = mView;
            this.mMaxLength = mMaxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (str.length() == 11 && mMaxLength == 11 || (str.length() == 6 && mMaxLength == 6)) {
                ViewUtil.hideOneInputMethod(LoginShareActivity.this, mView);
            }
        }
    }
}
