package com.qfh.common.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.qfh.common.R
import com.qfh.common.dialog.custom.AlertDialog
import com.qfh.common.dialog.utils.DialogUtils

class DebugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun test(view: View) {
        AlertDialog.Builder(this).setContentView(R.layout.dialog_common_tips)
            .setOnClickListener(R.id.btn_positive) {
                Toast.makeText(this, "ni hao", Toast.LENGTH_LONG).show()
            }.fullWidth()
            .fromBottom()
            .show()

//        DialogUtils.showTipsDialog(this,"!231"
//        ) { Log.e("MainActivity", "onClick: ",) }
//        val dialog = BaseDialog.Builder(this).build()
//        dialog.show()
    }

}