package com.qfh.common.dialog.custom;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * author  : QfhJordan
 * github-link : https://github.com/QfhJordan/AndroidCommon
 * date    : 2023/07/01
 * email   : 1741965145@qq.com
 * describe: 模仿Android源码中Builder中的AlertController
 */
class AlertController {
    private AlertDialog mAlertDialog;
    private Window mWindow;

    public AlertController(AlertDialog alertDialog, Window window) {
        this.mAlertDialog = alertDialog;
        this.mWindow = window;
    }

    public AlertDialog getAlertDialog() {
        return mAlertDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public static class AlertParams {
        public Context mContext;
        public int mThemeResId;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public View mView;
        public int mViewLayoutResId;
        public SparseArray<String> mTextArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> mListenerArray = new SparseArray<>();
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        public void apply(AlertController mAlert) {
            DialogViewHelper dialogViewHelper = null;
            if (mViewLayoutResId != 0) {
                dialogViewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }
            if (mView != null) {
                dialogViewHelper = new DialogViewHelper();
                dialogViewHelper.setContentView(mView);
            }
            if (dialogViewHelper == null) {
                throw new IllegalArgumentException("对象为空");
            }
            //设置文本
            for (int i = 0; i < mTextArray.size(); i++) {
                dialogViewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }
            for (int i = 0; i < mListenerArray.size(); i++) {
                dialogViewHelper.setOnClickListener(mListenerArray.keyAt(i), mListenerArray.valueAt(i));
            }
            mAlert.getAlertDialog().setContentView(dialogViewHelper.getContentView());
            Window window = mAlert.getWindow();
            window.setGravity(mGravity);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = mWidth;
            attributes.height = mHeight;
            window.setAttributes(attributes);
        }
    }
}
