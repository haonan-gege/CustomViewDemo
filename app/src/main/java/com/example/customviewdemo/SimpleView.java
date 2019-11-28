package com.example.customviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class SimpleView extends View {
    //画笔
    private Paint paint;
    //图片drawable
    private Drawable drawable;
    //View的宽度
    private int width;
    //View的高度
    private int height;

    public SimpleView(Context context) {
        this(context,null);
    }

    public SimpleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleView(Context context, AttributeSet attrs, int defStyleAttr) {
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


    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(drawable==null){
            return;
        }
        canvas.drawBitmap(ImageUtils.drawableToBitmap(drawable),getLeft(),getTop(),paint);
    }
}
