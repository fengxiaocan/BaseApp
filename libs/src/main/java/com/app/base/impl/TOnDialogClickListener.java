package com.app.base.impl;

import android.content.DialogInterface;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 17/10/18
 * @desc ...
 */
public abstract class TOnDialogClickListener<T> implements DialogInterface.OnClickListener{
    protected T data;

    public TOnDialogClickListener(T data){
        this.data = data;
    }

    public T getData(){
        return data;
    }
}
