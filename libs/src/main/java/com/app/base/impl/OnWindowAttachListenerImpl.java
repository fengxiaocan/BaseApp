package com.app.base.impl;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public abstract class OnWindowAttachListenerImpl implements ViewTreeObserver.OnWindowAttachListener{
    private View mView;

    public OnWindowAttachListenerImpl(View view){
        mView = view;
    }

    public View getView(){
        return mView;
    }


}
