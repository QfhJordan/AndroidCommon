package com.example.common.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import com.qfh.common.R

/**
 *  author  : QfhJordan
 *  github-link : https://github.com/QfhJordan/AndroidCommon
 *  date    : 2023/07/01
 *  email   : 1741965145@qq.com
 *  describe: Dialog基类
 */
open class BaseDialog : AppCompatDialog {
    private var builder: Builder
    val TAG = BaseDialog::class.java.simpleName

    companion object {
        private var baseDialog: BaseDialog? = null
    }

    constructor(builder: Builder) : this(builder, R.style.BaseDialogTheme)
    constructor(builder: Builder, theme: Int) : super(builder.context, theme) {
        this.builder = builder
    }

    fun dismissDialog() {
        baseDialog?.dismiss()
        baseDialog = null
    }

    override fun show() {
        if (baseDialog?.isShowing == false) {
            Log.e("BaseDialog", "show: ")
            super.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        builder.view?.let { setContentView(it) }

        setCanceledOnTouchOutside(builder.cancelTouch)
        val window = window
        if (window != null) {
            val params = window.attributes
            if (builder.width > 0) {
                Log.e(TAG, "builder.width: ")
                params.width = builder.width
            } else if (builder.height > 0) {
                Log.e(TAG, "builder.height: ")

                params.height = builder.height
            }
            params.gravity = builder.gravity
            Log.e(TAG, "builder w: " + params.width)
            Log.e(TAG, "builder h: " + params.height)
            window.attributes = params
        }
    }

    class Builder {
        var context: Context
        var cancelTouch = false
        var width = 0
        var height: Int = 0
        var gravity = 0
        var view: View? = null
        var resStyle = -1

        constructor(context: Context) : super() {
            this.context = context
        }

        fun setView(layoutId: Int): Builder {
            view = LayoutInflater.from(context).inflate(layoutId, null)
            val width1 = view?.width
            Log.e("width1", "setView: $width1")
            return this
        }

        fun setWidth(width: Int): Builder {
            this.width = width
            return this
        }

        fun setHeight(height: Int): Builder {
            this.height = height
            return this
        }

        fun setStyle(resStyle: Int): Builder {
            this.resStyle = resStyle
            return this
        }

        fun setView(view: View): Builder {
            this.view = view
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setCancelTouch(cancelTouch: Boolean): Builder {
            this.cancelTouch = cancelTouch
            return this
        }

        fun addViewOnClick(viewRes: Int, listener: View.OnClickListener): Builder {
//            view?.findViewById<View>(viewRes)?.setOnClickListener(listener)
            return this
        }

        fun <T : View> findViewById(id: Int): T? {
            return view?.findViewById(id)
        }

        fun build(): BaseDialog {
            if (baseDialog == null) {
                baseDialog = if (resStyle != -1) {
                    BaseDialog(this, resStyle)
                } else {
                    BaseDialog(this)
                }
            }
            return baseDialog as BaseDialog
        }
    }
}