package com.app.base.intface;

/**
 * The interface T method callback.
 *
 * @param <T> the type parameter
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.lib.intface
 * @创建者: Noah.冯
 * @时间: 11 :17
 * @描述： 方法参数回调
 */
public interface TMethodCallback<T>{

    /**
     * Method.
     *
     * @param t the t
     */
    void method(T t);
}
