package com.app.base.bahavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 7/6/18
 * @desc 一个可以随滑动改变透明度的Behavior
 */
public class ViewAlphaBehavior extends CoordinatorLayout.Behavior<View>{
    protected int offset = 0;
    protected int startOffset = 0;
    protected int endOffset = 0;

    public ViewAlphaBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes, int type)
    {
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target,
                               int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type)
    {
        startOffset = 0;
        endOffset = getOffScrollHeight(child) - child.getMeasuredHeight();
        offset += dyConsumed;
        if(offset <= startOffset){  //alpha为0
            child.getBackground().setAlpha(0);
        } else if(offset > startOffset && offset < endOffset){ //alpha为0到255
            float precent = (float)(offset - startOffset) / endOffset;
            int alpha = Math.round(precent * 255);
            child.getBackground().setAlpha(alpha);
        } else if(offset >= endOffset){  //alpha为255
            child.getBackground().setAlpha(255);
        }
    }

    public int getOffScrollHeight(View child){
        return child.getMeasuredHeight() * 3;
    }
}
