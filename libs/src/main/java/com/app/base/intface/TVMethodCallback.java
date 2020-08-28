package com.app.base.intface;

/**
 * The interface Tv method callback.
 *
 * @param <T> the type parameter
 * @param <V> the type parameter
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.lib.intface
 * @创建者: Noah.冯
 * @时间: 11 :17
 * @描述： 多方法参数回调
 */
public interface TVMethodCallback<T,V>{

    /**
     * Method.
     *
     * @param t the t
     * @param v the v
     */
    void method(T t,V v);
}
