package com.app.base.intface;

/**
 * The interface Net statu change listener.
 *
 * @name： BaseApp
 * @package： com.dgtle.baselib.intface
 * @author: Noah.冯 QQ:1066537317
 * @time: 19 :45
 * @version: 1.1
 * @desc： 网络状态监听器
 */
public interface OnNetStatusChangeListener {

    /**
     * On net change.网络状态改变
     */
    void onChange(int change);
}
