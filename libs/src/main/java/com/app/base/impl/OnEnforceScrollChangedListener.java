package com.app.base.impl;

import android.view.View;
import android.view.ViewTreeObserver;

import com.app.base.util.*;

public class OnEnforceScrollChangedListener implements ViewTreeObserver.OnScrollChangedListener{
    private View mView;
    private int width;
    private int height;

    public OnEnforceScrollChangedListener(View view, int width, int height){
        mView = view;
        this.width = width;
        this.height = height;
    }

    @Override
    public void onScrollChanged(){
        ViewUtils.changeSize(mView,width,height);
    }
}
