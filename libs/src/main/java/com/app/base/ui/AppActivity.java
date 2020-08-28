package com.app.base.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.app.base.back.*;

/**
 * The type App activity.
 *
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.base
 * @创建者: Noah.冯
 * @时间: 15 :19
 * @描述： Activity基类
 */
public class AppActivity extends BasicActivity{

    public SwipeBackAdapter mSwipeBackAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(isCanSwipeBack()){
            if(isCanSwipeBack()){
                getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
            }
            mSwipeBackAdapter = new SwipeBackAdapter(this);
        }
    }

    /**
     * 是否能侧滑返回
     *
     * @return boolean
     */
    public boolean isCanSwipeBack(){
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        if(mSwipeBackAdapter != null){
            mSwipeBackAdapter.onPostCreate();
        }
    }

    @Override
    public View findViewById(int id){
        View v = super.findViewById(id);
        if(v == null && mSwipeBackAdapter != null)
            return mSwipeBackAdapter.findViewById(id);
        return v;
    }
}
