package com.app.base.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.app.base.back.SwipeBackAdapter;

/**
 * The type Swipe back activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 11 :19
 * @描述： 可以侧滑的activity
 */
public class SwipeBackActivity extends BasicActivity{
    /**
     * The M adapter.
     */
    protected SwipeBackAdapter swipeBackAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(isCanSwipeBack()){
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().getDecorView()
                       .setBackgroundColor(Color.TRANSPARENT);
            swipeBackAdapter= new SwipeBackAdapter(this);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        if(swipeBackAdapter!=null){
            swipeBackAdapter.onPostCreate();
        }
    }

    /**
     * 设置滑动方向
     *
     * @param flags the flags
     */
    public void setSwipeFlags(SwipeBackAdapter.SwipeFlags flags){
        swipeBackAdapter.setSwipeFlags(flags);
    }

    @Override
    public <T extends View> T findViewById(int id){
        T v = super.findViewById(id);
        if(v == null&&swipeBackAdapter!=null){
            return swipeBackAdapter.findViewById(id);
        }
        return v;
    }

    /**
     * Is can swipe back boolean.是否能侧滑返回
     *
     * @return the boolean
     */
    public boolean isCanSwipeBack(){
        return true;
    }

    public void setWindowBackground(Drawable drawable){
        if(swipeBackAdapter!=null){
            swipeBackAdapter.getSwipeBackLayout()
                            .setWindowBackground(drawable);
        } else{
            ViewGroup decor = (ViewGroup)getWindow().getDecorView();
            ViewGroup decorChild = (ViewGroup)decor.getChildAt(0);
            decorChild.setBackground(drawable);
        }
    }

    public void setWindowBackgroundColor(int color){
        if(swipeBackAdapter!=null){
            swipeBackAdapter.getSwipeBackLayout()
                            .setWindowBackgroundColor(color);
        } else{
            ViewGroup decor = (ViewGroup)getWindow().getDecorView();
            ViewGroup decorChild = (ViewGroup)decor.getChildAt(0);
            decorChild.setBackgroundColor(color);
        }
    }
}
