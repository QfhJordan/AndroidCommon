package com.qfh.common.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MNViewGroup extends ViewGroup {
    private int mWidth;
    private int mHeight;

    public MNViewGroup(Context context) {
        super(context);
    }

    public MNViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MNViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(measureWidthAndHeight(widthMeasureSpec),
                    measureWidthAndHeight(heightMeasureSpec));
        } else {
            int childViewsWidth = 0;
            int childViewsHeight = 0;
            int childViewsMarginLeft = 0;
            int childViewsMarginRight = 0;
            int childViewsMarginTop = 0;
            int childViewsMarginBottom = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                childViewsWidth = Math.max(childViewsWidth, childView.getMeasuredWidth() + layoutParams.leftMargin +
                        layoutParams.rightMargin);
                childViewsHeight += childView.getMeasuredHeight();
                childViewsMarginTop += layoutParams.topMargin;
                childViewsMarginBottom += layoutParams.bottomMargin;
//                childViewsMarginLeft = Math.max(childViewsMarginLeft, layoutParams.leftMargin);
//                childViewsMarginRight = Math.max(childViewsMarginRight, layoutParams.rightMargin);

            }
            mWidth = childViewsWidth;
            mHeight = childViewsHeight + childViewsMarginTop + childViewsMarginBottom;
            setMeasuredDimension(measureWidthAndHeight(widthMeasureSpec, mWidth),
                    measureWidthAndHeight(heightMeasureSpec, mHeight));
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    private int measureWidthAndHeight(int measureSpec, int widthAndHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = widthAndHeight;
        }
        return result;
    }

    private int measureWidthAndHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top, right, bottom;
        int countTop = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            left = layoutParams.leftMargin;
            top = countTop + layoutParams.topMargin;
            right = left + childAt.getMeasuredWidth();
            bottom = top + childAt.getMeasuredHeight();
            childAt.layout(left, top, right, bottom);
            countTop += ((bottom - top)  + layoutParams.topMargin + layoutParams.bottomMargin);

        }
    }
}
