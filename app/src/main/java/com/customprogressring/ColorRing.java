package com.customprogressring;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by lannyxu on 2019/2/8.
 */
public class ColorRing extends View {

    private int crStartColor;
    private int crEndColor;
    private float crStrokeWidth;

    private Paint crPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private RectF pRectF;

    private int mMeasureHeight;
    private int mMeasureWidth;
    private int centerX;//圆心坐标X
    private int centerY;//圆心坐标Y

    private int color[] = new int[2];   //渐变颜色

    public ColorRing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray cr = context.obtainStyledAttributes(attrs, R.styleable.ColorRing);
        crStartColor = cr.getColor(R.styleable.ColorRing_cr_start_color, Color.GRAY);
        crEndColor = cr.getColor(R.styleable.ColorRing_cr_end_color, Color.BLACK);
        crStrokeWidth = cr.getDimension(R.styleable.ColorRing_cr_stroke_width, 8f);
        cr.recycle();

        color[0] = Color.parseColor("#77F8DA");
        color[1] = Color.parseColor("#52C8E7");

        crPaint.setStyle(Paint.Style.STROKE);
        crPaint.setStrokeCap(Paint.Cap.ROUND);
        crPaint.setStrokeWidth(crStrokeWidth);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMeasureWidth = getMeasuredWidth();
        mMeasureHeight = getMeasuredHeight();
        centerX = mMeasureWidth / 2;
        centerY = mMeasureHeight / 2;


        if (pRectF == null) {
            float halfStrokeWidth = crStrokeWidth / 2;//圆环宽度的一半
            pRectF = new RectF(halfStrokeWidth + getPaddingLeft(),
                    halfStrokeWidth + getPaddingTop(),
                    mMeasureWidth - halfStrokeWidth - getPaddingRight(),
                    mMeasureHeight - getPaddingBottom() - halfStrokeWidth);

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //渐变颜色
//        crPaint.setShader(new SweepGradient(centerX, centerY, color, null));
//        canvas.drawArc(pRectF, 270, -360, false, crPaint);

        crPaint.setShader(
                // Shader.TileMode.MIRROR代表镜像，x0代表渐变的起始X坐标，y0代表渐变的起始y坐标，x1代表渐变结束左边，y1..
                new LinearGradient(0, 0, mMeasureWidth, 0, color, null, Shader.TileMode.MIRROR));
        canvas.drawArc(pRectF, 180, 360, false, crPaint);//0时在始终3点的位置，

    }
}
