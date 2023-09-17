package com.qfh.common.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.router_annotations.Destinatign
import com.qfh.common.R

@Destinatign(url = "router://kotlin-page-home", description = "Kotlin Debug调试页面")
class DebugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}