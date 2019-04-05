package com.soft.zkrn.weilin_application;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class slideswitch extends View {
    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);  //锯齿形
    boolean isOn = false; //一开始关闭
    float curX = 0;
    float centerY; //y固定
    float viewWidth;
    float radius;
    float lineStart; //直线段开始的位置（横坐标，即
    float lineEnd; //直线段结束的位置（纵坐标
    float lineWidth;
    final int SCALE = 4; // 控件长度为滑动的圆的半径的倍数

    OnStateChangedListener onStateChangedListener;
    public slideswitch(Context context, AttributeSet attributeSet,int style){
        super(context,attributeSet,style);
    }
    public slideswitch(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public slideswitch(Context context){
        super(context);
    }
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        curX = event.getX();
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            if(curX > viewWidth / 2)
            {
                curX = lineEnd;
                if(false == isOn)
                {
                    //只有状态发生改变才调用回调函数， 下同
                    if(null != onStateChangedListener)
                    {
                        onStateChangedListener.onStateChangedListener(true);
                    }
                    isOn = true;
                }
            }
            else
            {
                curX = lineStart;
                if(true == isOn)
                {
                    if(null != onStateChangedListener)
                    {
                        onStateChangedListener.onStateChangedListener(false);
                    }
                    isOn = false;
                }
            }
        }
        //通过刷新调用onDraw
        this.postInvalidate();
        return true;
    }

    //限制宽高
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*保持宽是高的SCALE / 2倍， 即圆的直径*/
        this.setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredWidth() * 2 / SCALE);
        viewWidth = this.getMeasuredWidth();
        radius = viewWidth / SCALE;
        lineWidth = radius * 2f; //直线宽度等于滑块直径
        curX = radius;
        centerY = this.getMeasuredWidth() / SCALE; //centerY为高度的一半
        lineStart = radius;
        lineEnd = (SCALE - 1) * radius;
    }

    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        /*限制滑动范围*/
        curX = curX > lineEnd?lineEnd:curX;
        curX = curX < lineStart?lineStart:curX;

        /*划线*/
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        /*左边部分的线，绿色*/
        paint.setColor(Color.BLUE);
        canvas.drawLine(lineStart, centerY, curX, centerY, paint);
        /*右边部分的线，灰色*/
        paint.setColor(Color.GRAY);
        canvas.drawLine(curX, centerY, lineEnd, centerY, paint);

        /*画圆*/
        /*画最左和最右的圆，直径为直线段宽度， 即在直线段两边分别再加上一个半圆*/
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(lineEnd, centerY, lineWidth / 2, paint);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(lineStart, centerY, lineWidth / 2,paint);
        /*圆形滑块*/
        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(curX, centerY, radius , paint);

    }




    //内部接口
    public interface OnStateChangedListener{
        public void onStateChangedListener(boolean state);
    }
    //设置开关的状态
    public void setOnStateChangedListener(OnStateChangedListener o)
    {
        this.onStateChangedListener = o;
    }
}
