package com.example.mylibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.router_annotations.Destinatign

@Destinatign(url = "router://reading", description = "Reading页面")
class ReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}