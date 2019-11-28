package com.example.customviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class SimpleView1 extends View {
    //画笔
    private Paint paint;
    //图片drawable
    private Drawable drawable;
    //View的宽度
    private int width;
    //View的高度
    private int height;

    public SimpleView1(Context context) {
        this(context,null);
    }

    public SimpleView1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //根据属性初始化
        initAttrs(attrs);
        paint=new Paint();
        paint.setAntiAlias(true);//抗锯齿



    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = null;
        //获取定义的属性
        array = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleView);
        try {
            if (attrs != null) {

                //根据图片id获取drawable对象
                drawable = array.getDrawable(R.styleable.SimpleView_src);
                //测量Drawable对象的宽高
                measureDrawable();
            }
        }finally {
            if(array!=null){
                array.recycle();
            }
        }

    }

    private void measureDrawable() {
        if(drawable==null){
            throw new RuntimeException("drawable不能为空！");

        }
        width=drawable.getIntrinsicWidth();
        height=drawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       //设置View的宽和高为图片的宽和高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width,height);
        //获取宽度的模式与大小
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        //获取高度的模式与大小
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        //设置View的宽高
        setMeasuredDimension(measureWidth(widthMode,width),measureHeight(heightMode,height));


    }

    private int measureHeight(int heightMode, int height) {
        switch (heightMode){
            case MeasureSpec.UNSPECIFIED:
                case MeasureSpec.AT_MOST:
                    break;
                    case MeasureSpec.EXACTLY:
                        this.height=height;
                        break;
        }
        return this.height;

    }

    private int measureWidth(int widthMode, int width) {
        switch (widthMode){
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
                case MeasureSpec.EXACTLY:
                    this.width=width;
                    break;

        }
        return this.width;

    }

private Bitmap mBitmap;
    @Override
    protected void onDraw(Canvas canvas) {
        if(mBitmap==null){
            mBitmap= Bitmap.createScaledBitmap(ImageUtils.drawableToBitmap(drawable),
                    getMeasuredWidth(),getMeasuredHeight(),true);

        }
        canvas.drawBitmap(mBitmap,getLeft(),getTop(),paint);
    }
}
