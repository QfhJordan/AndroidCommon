package com.qfh.common.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.qfh.common.R
import com.qfh.common.customview.MNView
import com.qfh.common.datastorage.DatabaseActivity
import com.qfh.common.datastorage.LoginShareActivity
import com.qfh.common.datastorage.SQLiteReadActivity
import com.qfh.common.datastorage.SQLiteWriteActivity
import com.qfh.common.datastorage.ShareReadActivity
import com.qfh.common.datastorage.ShareWriteActivity
import com.qfh.common.dialog.custom.AlertDialog
import com.qfh.common.dialog.utils.DialogUtils

class DebugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun saveData(view: View) {
        startActivity(Intent(this, ShareWriteActivity::class.java))
    }

    fun getData(view: View) {
        startActivity(Intent(this, ShareReadActivity::class.java))
    }

    fun loginShareActivity(view: View) {
        startActivity(Intent(this, LoginShareActivity::class.java))
    }

    fun databaseActivity(view: View) {
        startActivity(Intent(this, DatabaseActivity::class.java))
    }

    fun sQLiteWriteActivity(view: View) {
        startActivity(Intent(this, SQLiteWriteActivity::class.java))
        finish()
    }

    fun sQLiteReadActivity(view: View) {
        startActivity(Intent(this, SQLiteReadActivity::class.java))
        finish()
    }
}