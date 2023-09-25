package com.example.common.dialog.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.qfh.common.R
import com.example.common.dialog.BaseDialog

object DialogUtils {
    private var baseDialog: BaseDialog? = null
    fun showTipsDialog(context: Context, content: String, listener: View.OnClickListener) {
        Log.e("DialogUtils", "showTipsDialog: ", )
        val builder = BaseDialog.Builder(context)
        baseDialog = builder.setView(R.layout.dialog_common_tips)
            .addViewOnClick(R.id.btn_positive) {
                baseDialog?.dismissDialog()
                listener.onClick(it)
            }
            .build()
        builder.findViewById<Button>(R.id.btn_negative)?.visibility = View.VISIBLE
        val txtContent = builder.findViewById<TextView>(R.id.txt_content)
        txtContent?.text = content
        baseDialog?.show()
    }

    fun showTipsTwoDialog(
        context: Context?,
        content: String?,
        negative: String?,
        positive: String?,
        listener: View.OnClickListener
    ) {
        val builder = BaseDialog.Builder(
            context!!
        )
        baseDialog =
            builder.setView(R.layout.dialog_common_tips).addViewOnClick(R.id.btn_positive) { v ->
                baseDialog?.dismissDialog()
                listener.onClick(v)
            }
                .addViewOnClick(R.id.btn_negative) { baseDialog?.dismissDialog() }
                .build()
        val txtContent = builder.findViewById<AppCompatTextView>(R.id.txt_content)!!
        val btnNegative = builder.findViewById<AppCompatTextView>(R.id.btn_negative)!!
        val btnPositive = builder.findViewById<AppCompatTextView>(R.id.btn_positive)!!
        txtContent.text = content
        btnNegative.text = negative
        btnPositive.text = positive
        baseDialog?.show()
    }
}
