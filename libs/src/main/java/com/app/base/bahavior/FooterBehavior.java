package com.app.base.bahavior;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class FooterBehavior extends CoordinatorLayout.Behavior<View>{
    private boolean isAnimateIng = false;   // 是否正在动画
    private boolean isShow = true;  // 是否已经显示
    private AccelerateDecelerateInterpolator LINEAR_INTERRPLATOR = new AccelerateDecelerateInterpolator();

    public FooterBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull View child, @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type)
    {
        return super.onStartNestedScroll(coordinatorLayout,child,directTargetChild,target,axes,type) ||
               axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target,
                               int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type)
    {
        //界面向下滑动,fab动画结束,且正在显示
        //隐藏Fab
        if((dyConsumed > 0 || dyUnconsumed > 0) && ! isAnimateIng && isShow){
            hideFab(child,new AnimateListener());
        }
        //界面向上滑动,fab动画结束,且隐藏
        //显示Fab
        else if((dyConsumed < 0 || dyUnconsumed < 0) && ! isAnimateIng && ! isShow){
            showFab(child,new AnimateListener());
        }
    }

    public void showFab(View view, AnimateListener... listener){
        if(listener.length != 0){
            view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(600)
                .setInterpolator(LINEAR_INTERRPLATOR)
                .setListener(listener[0])
                .start();
        } else{
            view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(600)
                .setInterpolator(LINEAR_INTERRPLATOR)
                .start();
        }

    }

    public void hideFab(View view, AnimateListener listener){
        view.animate()
            .scaleX(0f)
            .scaleY(0f)
            .alpha(0f)
            .setDuration(600)
            .setInterpolator(LINEAR_INTERRPLATOR)
            .setListener(listener)
            .start();
    }

    public class AnimateListener implements Animator.AnimatorListener{


        @Override
        public void onAnimationStart(Animator animation){
            isAnimateIng = true;
            isShow = ! isShow;
        }

        @Override
        public void onAnimationEnd(Animator animation){
            isAnimateIng = false;

        }

        @Override
        public void onAnimationCancel(Animator animation){


        }

        @Override
        public void onAnimationRepeat(Animator animation){
        }
    }
}


