package com.app.base.viewpager;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * The type Scale page transformer.
 * 自定义ViewPager的切换动画
 * 请在ViewPager上设置android:clipToPadding="false" 即是允许在父布局中的padding绘制视图
 */
public class ScalePageTransformer implements ViewPager.PageTransformer{

    /**
     * The constant MIN_SCALE.
     */
    public static final float MIN_SCALE = 0.91f;


    public ScalePageTransformer(){
    }


    /**
     * 重新计算 page 的位置transformPos，使得
     * 当page居中时 transformPos为0
     * 当page向左偏移时 transformPos为[-Infinity, 0)
     * 当page向右偏移时 transformPos为(0, +Infinity]
     */
    @Override
    public void transformPage(View page, float position){
        ViewPager viewPager = (ViewPager)page.getParent();
        int scrollX = viewPager.getScrollX();

        int clientWidth = viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight();
        int offsetX = page.getLeft() - scrollX;
        int parentWidth = viewPager.getMeasuredWidth();
        int childWidth = page.getMeasuredWidth();
        float deltaX = (float)(parentWidth - childWidth) / 2;

        float transformPos = (offsetX - deltaX) / clientWidth;

        if(transformPos < - 1){ // [-Infinity,-1)
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if(transformPos <= 1){ // [-1,1]
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(transformPos));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            //            if (onScalePageListener != null) {
            //                onScalePageListener.onScale(1-Math.abs(transformPos));
            //            }
        } else{ // (1,+Infinity]
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
