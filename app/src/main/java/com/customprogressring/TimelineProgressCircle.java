package com.customprogressring;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lannyxu on 2019/2/12.
 */
public class TimelineProgressCircle extends View {

    //外院颜色
    private int outCircleColor;
    //外圆半径
    private float outCircleRadius;
    //内圆颜色
    private int innerCircleColor;
    //外圆半径
    private float innerCircleRadius;
    //外圆弧颜色
    private int arcColor;
    //外圆弧宽度
    private float arcStrokeWidth;

    private float centerX;
    private float centerY;

    private float mMeasureWidth;
    private float mMeasureHeight;
    //外圆画笔
    private Paint outCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    //内院圆画笔
    private Paint innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    //外圆弧画笔
    private Paint arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);


    private RectF arcRectF;

    public TimelineProgressCircle(Context context) {
        this(context, null);
    }

    public TimelineProgressCircle(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimelineProgressCircle);
        outCircleColor = ta.getColor(R.styleable.TimelineProgressCircle_tl_out_cir_color, Color.WHITE);
        outCircleRadius = ta.getDimension(R.styleable.TimelineProgressCircle_tl_out_cir_radius, 7f);
        innerCircleColor = ta.getColor(R.styleable.TimelineProgressCircle_tl_inner_cir_color, Color.RED);
        innerCircleRadius = ta.getDimension(R.styleable.TimelineProgressCircle_tl_inner_cir_radius, 5f);
        arcColor = ta.getColor(R.styleable.TimelineProgressCircle_tl_arc_color, Color.RED);
        arcStrokeWidth = ta.getDimension(R.styleable.TimelineProgressCircle_tl_arc_stroke_width, 1f);
        ta.recycle();

        outCirclePaint.setColor(outCircleColor);
        innerCirclePaint.setColor(innerCircleColor);

        arcPaint.setColor(arcColor);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStrokeWidth(arcStrokeWidth);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        mMeasureWidth = getMeasuredWidth();
        mMeasureHeight = getMeasuredHeight();
        centerX = mMeasureWidth / 2;
        centerY = mMeasureHeight / 2;

        if (arcRectF == null) {
            float halfStrokeWidth = arcStrokeWidth / 2;//圆环宽度的一半
            arcRectF = new RectF(halfStrokeWidth + getPaddingLeft(),
                    halfStrokeWidth + getPaddingTop(),
                    mMeasureWidth - halfStrokeWidth - getPaddingRight(),
                    mMeasureHeight - getPaddingBottom() - halfStrokeWidth);

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //花外边一个大圆
        canvas.drawCircle(centerX, centerY, outCircleRadius, outCirclePaint);
        //花里边一个大圆
        canvas.drawCircle(centerX, centerY, innerCircleRadius, innerCirclePaint);
        //花外边一个圆弧
        canvas.drawArc(arcRectF, 0f, 360f, false, arcPaint);
    }
}
