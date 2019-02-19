package com.customprogressring;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

/**
 * 滑动卡片
 * Created by lannyxu on 2019/2/11.
 */
public class SwipeCards extends ViewGroup {


    private static final String TAG = "SwipeCards";
    private int mCenterX;
    private int mCenterY;
    private ViewDragHelper mViewDragHelper;
    private static final int MAX_DEGREE = 60;
    private static final float MAX_ALPHA_RANGE = 0.5f;
    private int mCardGap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());


    public SwipeCards(Context context) {
        this(context, null);
    }

    public SwipeCards(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int left = mCenterX - ((View) child).getMeasuredWidth() / 2;
            int top = mCenterY - child.getMeasuredHeight() / 2 + mCardGap * (getChildCount() - i);
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);
        }
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            Log.d(TAG, "onViewPositionChanged: " + left);
            int diffX = left + changedView.getWidth() / 2 - mCenterX;
            float ratio = diffX * 1.0f / getWidth();
            float degree = MAX_DEGREE * ratio;
            changedView.setRotation(degree);
            float alpha = 1 - Math.abs(ratio) * MAX_ALPHA_RANGE;
            changedView.setAlpha(alpha);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            final int left = releasedChild.getLeft();
            final int right = releasedChild.getRight();
            if (left > mCenterX) {
                animateToRight(releasedChild);
            } else if (right < mCenterX) {
                animateToLeft(releasedChild);
            } else {
                animateToCenter(releasedChild);
            }
        }
    };

    private void animateToCenter(View releasedChild) {

        int finalLeft = mCenterX - releasedChild.getWidth() / 2;
        int indexOfChild = indexOfChild(releasedChild);
        int finalTop = mCenterY - releasedChild.getHeight() / 2 + mCardGap * (getChildCount() - indexOfChild);
        mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
        invalidate();
    }

    private void animateToLeft(View releasedChild) {

        int finalLeft = -getWidth();
        int finalTop = 0;
        mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
        invalidate();
    }

    private void animateToRight(View releasedChild) {

        int finalLeft = getWidth() + releasedChild.getHeight();
        int finalTop = releasedChild.getTop();
        mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(false)) {
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
