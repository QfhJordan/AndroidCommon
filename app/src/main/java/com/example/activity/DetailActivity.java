package com.example.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.qfh.common.R;

import cn.feng.skin.manager.base.BaseActivity;
import cn.feng.skin.manager.util.ResourceUtils;

public class DetailActivity  extends BaseActivity {
    private TextView titleText;
    private TextView detailText;
    private String article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initData();
        initView();
    }

    private void initView() {
        titleText = (TextView) findViewById(R.id.title_text);
        detailText = (TextView) findViewById(R.id.detail_text);

        titleText.setText("生命中的美好都是免费的");
        detailText.setText(article);
    }

    private void initData() {
        article = ResourceUtils.geFileFromAssets(this, "article.txt");
    }
}
