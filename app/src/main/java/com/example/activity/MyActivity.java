package com.example.activity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fragment.ArticleListFragment;
import com.qfh.common.R;

import java.io.File;

import cn.feng.skin.manager.base.BaseFragmentActivity;

/**
 * @author MagicBook
 */
public class MyActivity extends BaseFragmentActivity {

//    String SKIN_NAME = "BlackFantacy.skin";
//    String SKIN_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();

//    File skinFile = new File(SKIN_DIR);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
//        Log.e("MyActivity", "onCreate: "+SKIN_DIR);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = ArticleListFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
