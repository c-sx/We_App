package com.soft.zkrn.weilin_application;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.RelativeLayout;

//继承RelativeLayout类
public class BorderView extends RelativeLayout {
    //默认边框宽度
    public static final float DEFAULT_STOKE_WIDTH=1.0f;

    //画笔
    private Paint paint;
    //边框颜色color

    private int paintcolor;

    //边框粗细
    private float BorderStrokeWidth;
    //与底边边线左边断开距离
    private int BorderBottonLeftBreakSize;
    //与底边边线右边断开距离
    private int BorderBottonRightBreakSize;
    //是否需要上边框
    private boolean IsNeedTopborder;
    //是否需要左右边框
    private boolean IsNeedLeftAndRightBorder;
    //是否需要下边框
    private boolean IsNeedBottomBorder;
    //Android提供的记述屏幕的有关信息的一种结构，诸如其尺寸，密度和字体缩放的一般信息
    private DisplayMetrics displayMetrics;

    //自定义控件
    public BorderView(Context context,  AttributeSet attrs ){
        this(context,attrs,0);
    }
    public BorderView(Context context){
        this(context,null);
    }
    public BorderView(Context context,AttributeSet attrs,int styleAttr){
        super(context,attrs,styleAttr);

        displayMetrics=context.getResources().getDisplayMetrics();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BorderView);
        //画颜色
       paintcolor = array.getColor(R.styleable.BorderView_borderColor, Color.GRAY);
       //定义边界的宽度，先定义为默认值
        BorderStrokeWidth = array.getFloat(R.styleable.BorderView_borderStrokeWidth, DEFAULT_STOKE_WIDTH);
       //画边界底边边线与左边断开距离
        BorderBottonLeftBreakSize = array.getDimensionPixelSize(R.styleable.BorderView_borderBottomLeftBreakSize, 0);
        //将dip转换为sp
        BorderBottonLeftBreakSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BorderBottonLeftBreakSize, displayMetrics);
        //画边界底边边线与右边断开距离
        BorderBottonRightBreakSize = array.getDimensionPixelSize(R.styleable.BorderView_borderBottomRightBreakSize, 0);
        //将dip转换为sp
        BorderBottonRightBreakSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BorderBottonRightBreakSize, displayMetrics);
        //是否需要定边界
        IsNeedTopborder = array.getBoolean(R.styleable.BorderView_needTopBorder, true);
        //不需要左右边界
        IsNeedLeftAndRightBorder = array.getBoolean(R.styleable.BorderView_needLeftAndRigtBorder, false);
        //需要底边界
        IsNeedBottomBorder= array.getBoolean(R.styleable.BorderView_needBottomBorder, true);
        //回收TypedArray,以便后面重用
        array.recycle();

    }

    public void initpaint(){
        //引用画笔
      paint=new Paint();
      //颜色引用
      paint.setColor(paintcolor);
      //画笔为无锯齿
      paint.setAntiAlias(true);
      //引用线宽
      paint.setStrokeWidth(BorderStrokeWidth);
    }
 /*
 创建图层
  */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //画四个边
        if (IsNeedTopborder){
            canvas.drawLine(0,0,this.getWidth(),0,paint);
        }

        if (IsNeedBottomBorder){
            canvas.drawLine(BorderBottonLeftBreakSize,this.getHeight(),this.getWidth()-BorderBottonRightBreakSize,this.getHeight(),paint);

        }

        if (IsNeedLeftAndRightBorder){
            canvas.drawLine(0,0,0,this.getHeight(),paint);
            canvas.drawLine(this.getWidth(),0,this.getWidth(),this.getHeight(),paint);
        }
    }

    //设置颜色
    public void setPaintcolor(){
        paint.setColor(paintcolor);
        //刷新
        invalidate();
    }
    //设置宽度
    public void setBorderStrokeWidth(float size){
        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,size,displayMetrics));
        invalidate();
    }

    //设置是否顶部边框


    public void setNeedTopborder(boolean needTopborder) {
        IsNeedTopborder = needTopborder;
        invalidate();
    }

    //设置是否需要底部边框


    public void setNeedBottomBorder(boolean needBottomBorder) {
        IsNeedBottomBorder = needBottomBorder;
        invalidate();
    }
}
