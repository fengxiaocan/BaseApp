package com.app.base.adapter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import com.app.base.intface.*;
import com.app.base.util.*;


/**
 * @项目名： yuanyang
 * @包名： com.yyjl.yuanyangjinlou.adapter
 * @创建者: Noah.冯
 * @时间: 11:08
 * @描述： EditText 的确认键监听键的适配器
 */
public class OnKeyListenerAdapter implements View.OnKeyListener{
    public Context mContext;
    public MethodCallback mMethodCallback;

    public OnKeyListenerAdapter(Context context, MethodCallback methodCallback){
        mContext = context;
        mMethodCallback = methodCallback;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            // 先隐藏键盘
            KeyboardUtils.hideSoftInput(mContext,v);
            //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
            mMethodCallback.method();
        }
        return false;
    }
}
