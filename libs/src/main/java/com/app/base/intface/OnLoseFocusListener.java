package com.app.base.intface;

import android.view.MotionEvent;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public interface OnLoseFocusListener{
    /**
     * 调用了隐藏系统键盘的功能
     * 失去焦点监听
     *
     * @param motionEvent
     */
    void onLoseFocus(MotionEvent motionEvent);
}
