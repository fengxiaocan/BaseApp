package com.app.base.log;

import java.util.LinkedList;

/**
 * @name： FingerprintLoader
 * @package： com.evil.com.dgtle.baselib.log
 * @author: Noah.冯 QQ:1066537317
 * @time: 10:54
 * @version: 1.1
 * @desc： 日志缓存
 */

class LogCache{
    private static LogCache ourInstance;
    //最大缓存数量
    private static int sMaxCacheSize = 1000;
    private LinkedList<LogInfo> mLogCache;

    private LogCache(){
        mLogCache = new LinkedList<>();
    }

    public static LogCache getInstance(){
        synchronized(LogCache.class){
            if(ourInstance == null){
                synchronized(LogCache.class){
                    ourInstance = new LogCache();
                }
            }
        }
        return ourInstance;
    }

    public static void setMaxCacheSize(int maxPoolSize){
        sMaxCacheSize = maxPoolSize;
    }

    public LinkedList<LogInfo> getLogCache(){
        return mLogCache;
    }

    void add(int priority, String tag, String msg){
        mLogCache.add(new LogInfo(priority,tag,msg));
        if(mLogCache.size() > sMaxCacheSize){
            mLogCache.removeFirst();
        }
    }

    public void clear(){
        synchronized(LogCache.class){
            mLogCache.clear();
        }
    }
}
