package com.alen.rollimage;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Alen on 2018/2/5.
 */


public class RollImage extends AppCompatImageView {

    private View parentView;//本view的父元素
    private int parentHight, move;
    private int mMinDx;//image控件的当前高度
    private int windowY = 0;
    private int[] location = new int[2], parentLocation = new int[2];//控件,和父元素相对于屏幕左上角的坐标
    private int w, h;//图片按比例适应控件大小后的宽高
    private Drawable drawable;//图片资源

    public RollImage(Context context) {
        this(context, null);
    }

    public RollImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //每当view滚动均执行isInvalidate();方法
        getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                isInvalidate();
            }
        });
    }



    private void isInvalidate(){
        //获取父控件
        parentView = (View) getParent();
        //获取src图片资源
        drawable = getDrawable();
        //判断该元素是否显示在屏幕上,如果是就绘制图片,如果不是就不绘制,保证性能
        if (getLocalVisibleRect(new Rect()) && getVisibility()!=GONE && drawable != null && parentView != null){
            //获取父控件的高
            parentHight = parentView.getHeight();
            //定义此控件和父控件的控件坐标及获取,getLocationOnScreen会返回以屏幕左上角为圆点的控件左上角的坐标数组
            getLocationOnScreen(location);
            parentView.getLocationOnScreen(parentLocation);
            //定义控件左上角在屏幕上的y坐标
            windowY = location[1];
            w = getWidth();
            h = (int) (getWidth() * 1.0f / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());//等比例设置图片高度

            //move = (父布局的高度+父布局到屏幕顶端的距离-控件距离顶端的距离-控件自身的高度)*(图片高度-控件高度)/(父元素高度-控件高度)
            move = (int)((parentHight + parentLocation[1] - windowY - mMinDx)*1.0f*(h-mMinDx)/(parentHight -mMinDx));
            //如果移动距离小于0就保持为0
            if (move < 0){
                move = 0;
                return;
            }
            //移动距离不能大于图片高度-控件高度
            if (move > h-mMinDx) {
                move = h-mMinDx;
                return;
            }
            invalidate();
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDx = h;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //移动图片
        if (drawable!=null){
            drawable.setBounds(0, 0, w, h);
            canvas.translate(0, -move);
        }
        super.onDraw(canvas);
    }
}
