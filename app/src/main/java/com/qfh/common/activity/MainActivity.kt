package com.qfh.common.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.qfh.common.R
import com.qfh.common.dialog.utils.DialogUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun test(view: View) {
        DialogUtils.showTipsDialog(this,"!231"
        ) { Log.e("MainActivity", "onClick: ",) }
//        val dialog = BaseDialog.Builder(this).build()
//        dialog.show()
    }

}