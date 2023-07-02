package com.qfh.common.dialog.custom;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * author  : QfhJordan
 * github-link : https://github.com/QfhJordan/AndroidAlertDialog
 * date    : 2023/07/01
 * email   : 1741965145@qq.com
 * describe: Dialog展示View界面的一个辅助类
 */
public class DialogViewHelper {
    private View mContentView;
    private SparseArray<WeakReference<View>> mViews;

    public View getContentView() {
        return mContentView;
    }

    public DialogViewHelper(Context context, int viewLayoutResId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(viewLayoutResId, null);
    }

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    public void setContentView(View view) {
        this.mContentView = view;
    }

    public void setText(int viewId, String text) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(text);
        }
    }

    private <T extends View> T getView(int viewId) {

        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if (viewWeakReference != null) {
            view = viewWeakReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }
}
