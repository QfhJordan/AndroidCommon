package com.qfh.common.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.router_annotations.Destinatign;
import com.qfh.common.R;

import org.json.JSONArray;

@Destinatign(url = "router://page-home", description = "Debug调试页面")
public class JavaTesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
