package com.app.base.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;

public abstract class ViewPagerViewHolder<T>{
    public View mRootView;
    public int position;

    public ViewPagerViewHolder(View rootView){
        mRootView = rootView;
    }

    public ViewPagerViewHolder(ViewGroup container, int layoutRes, int position){
        this.position = position;
        mRootView = LayoutInflater.from(container.getContext()).inflate(layoutRes,container,false);
    }

    public View getRootView(){
        return mRootView;
    }

    public void destroyItem(ViewGroup container, int position){
        container.removeView(mRootView);
    }

    public abstract void initData(T t,int position);

    public abstract void findViewById(View rootView);

    public final <T extends View> T findViewById(@IdRes int id){
        return getRootView().findViewById(id);
    }

    public void onPagerVisibleHint(boolean isVisible){
    }

    public Context getContext(){
        return mRootView.getContext();
    }
}
