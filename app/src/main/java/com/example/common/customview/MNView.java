package com.example.common.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.qfh.common.R;

public class MNView extends View {
    private Rect mRect;
    private Paint mPaint;
    private String text;
    private int mWidth;
    private int mHeight;

    public MNView(Context context) {
        this(context, null);
    }

    public MNView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MNView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MNView);
        text = ta.getString(R.styleable.MNView_mn_text);
        ta.recycle();

        mPaint = new Paint();
        mRect = new Rect();
        mPaint.setTextSize(30);
        mPaint.getTextBounds(text, 0, text.length(), mRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(widthMeasureSpec);
        if (modeWidth == MeasureSpec.EXACTLY) {
            mWidth = sizeWidth;
        } else {
            mWidth = mRect.width() + getPaddingLeft() + getPaddingRight();
            Log.e("TAG", "onMeasure: mWidth" + mWidth);
        }
        if (modeHeight == MeasureSpec.EXACTLY) {
            mHeight = sizeHeight;
        } else {
            mHeight = mRect.height() + getPaddingTop() + getPaddingBottom();
            Log.e("TAG", "onMeasure: " + mHeight);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, getPaddingLeft(), getPaddingTop() + mRect.height(), mPaint);

    }
}
