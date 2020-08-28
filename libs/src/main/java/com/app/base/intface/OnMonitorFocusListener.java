package com.app.base.intface;

import android.view.View;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/6/18
 * @desc ...
 */
public interface OnMonitorFocusListener{
    /**
     * 调用了隐藏系统键盘的功能
     * 失去焦点监听
     */
    void onFocusChange(View view, boolean isGetFocus);
}
