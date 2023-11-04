package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.activity.DetailActivity;
import com.example.activity.SettingActivity;
import com.example.entity.News;
import com.qfh.common.R;

import java.util.ArrayList;
import java.util.List;

import cn.feng.skin.manager.base.BaseFragment;
import cn.feng.skin.manager.entity.AttrFactory;
import cn.feng.skin.manager.entity.DynamicAttr;
import cn.feng.skin.manager.util.CommonBaseAdapter;
import cn.feng.skin.manager.util.CommonViewHolder;

public class ArticleListFragment extends BaseFragment {
    private TextView titleText;
    private Button settingBtn;
    private ListView newsList;
    private RelativeLayout titleBarLayout;
    private NewsAdapter adapter;
    private List<News> datas;

    public static ArticleListFragment newInstance() {
        return new ArticleListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article_list, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {

        titleBarLayout = (RelativeLayout) v.findViewById(R.id.title_bar_layout);
        newsList = (ListView) v.findViewById(R.id.news_list_view);
        adapter = new NewsAdapter(requireActivity(), datas);
        newsList.setAdapter(adapter);
        newsList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent();
            intent.setClass(requireActivity(), DetailActivity.class);
            startActivity(intent);
        });
        titleText = (TextView) v.findViewById(R.id.title_text);
        settingBtn = (Button) v.findViewById(R.id.title_bar_setting_btn);

        titleText.setText("Small Article");

        settingBtn.setOnClickListener(v1 -> {
            Intent intent = new Intent();
            intent.setClass(requireActivity(), SettingActivity.class);
            startActivity(intent);
        });
        // test for dynamicAddTitle()
        dynamicAddTitleView();
    }

    private void dynamicAddTitleView() {
        // add title view
        TextView textView = new TextView(requireActivity());
        textView.setText("Small Article (动态new)");
        RelativeLayout.LayoutParams param =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.
                        WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView.setLayoutParams(param);
        textView.setTextColor(getResources().getColor(R.color.color_title_bar_text));
        textView.setTextSize(20);
        titleBarLayout.addView(textView);

        List<DynamicAttr> mDynamicAttr = new ArrayList<>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TEXT_COLOR, R.color.color_title_bar_text));
        dynamicAddView(textView, mDynamicAttr);
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            News news = new News();
            news.content = "Always listen to your heart because even though it's on your left side, it's always right.";
            news.title = "Dear Diary " + i;
            datas.add(news);
        }
    }

    private class NewsAdapter extends CommonBaseAdapter<News> {

        public NewsAdapter(Context context, List<News> mDatas) {
            super(context, mDatas, new int[]{R.layout.item_news_title});
        }

        @Override
        public void convertItemView(CommonViewHolder holder, News item, int position) {
            holder.setText(R.id.item_news_title, item.title);
            holder.setText(R.id.item_news_synopsis, item.content);
        }
    }
}
