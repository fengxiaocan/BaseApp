package com.app.base.back;

import android.app.Activity;

/**
 * @name： BaseApp
 * @package： com.dgtle.baselib.back
 * @author: Noah.冯 QQ:1066537317
 * @time: 18:49
 * @version: 1.1
 * @desc： TODO
 */

public interface ISwipeBack{
    /**
     * 获取上一个activity。 这点很重要,当大家维护一个activity栈时,
     * 一定要获取正确。尤其是遇到旋转屏或者activity因内存不足被杀死时。
     *
     * @return
     */
    Activity getBackActivity();
}
