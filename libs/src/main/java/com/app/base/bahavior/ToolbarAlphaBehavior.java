package com.app.base.bahavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 7/6/18
 * @desc ...
 */
public class ToolbarAlphaBehavior extends CoordinatorLayout.Behavior<Toolbar>{
    protected int offset = 0;
    protected int startOffset = 0;
    protected int endOffset = 0;

    public ToolbarAlphaBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes, int type)
    {
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild,
                                       View target, int nestedScrollAxes)
    {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar toolbar, View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
        startOffset = 0;
        //        endOffset = context.getResources().getDimensionPixelOffset(R.dimen.header_height) -
        //                    toolbar.getHeight();
        endOffset = getOffScrollHeight(toolbar) - toolbar.getMeasuredHeight();
        offset += dyConsumed;
        if(offset <= startOffset){  //alpha为0
            toolbar.getBackground().setAlpha(0);
        } else if(offset > startOffset && offset < endOffset){ //alpha为0到255
            float precent = (float)(offset - startOffset) / endOffset;
            int alpha = Math.round(precent * 255);
            toolbar.getBackground().setAlpha(alpha);
        } else if(offset >= endOffset){  //alpha为255
            toolbar.getBackground().setAlpha(255);
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target,
                               int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type)
    {
        //        super.onNestedScroll(
        //                coordinatorLayout,
        //                child,
        //                target,
        //                dxConsumed,
        //                dyConsumed,
        //                dxUnconsumed,
        //                dyUnconsumed,
        //                type
        //        );
        startOffset = 0;
        //        endOffset = context.getResources().getDimensionPixelOffset(R.dimen.header_height) -
        //                    child.getHeight();
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

    public int getOffScrollHeight(Toolbar child){
        return child.getMeasuredHeight() * 3;
    }
}
