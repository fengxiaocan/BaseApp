package com.app.base.log;

import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

/**
 * @name： FingerprintLoader
 * @package： com.evil.com.dgtle.baselib.log
 * @author: Noah.冯 QQ:1066537317
 * @time: 10:54
 * @version: 1.1
 * @desc： 日志读写缓冲池
 */

class LogPool{
    static DeviceInfo DEVICE_INFO;
    private static LogPool ourInstance;
    //最大缓存数量
    private static int sMaxPoolSize = 1000;
    private LinkedList<LogInfo> mLogPool;

    {
        Log.w("LogUtils","LogPool.INIT");
    }


    private LogPool(){
        mLogPool = new LinkedList<>();
        DEVICE_INFO = new DeviceInfo();
    }

    public static LogPool getInstance(){
        synchronized(LogPool.class){
            if(ourInstance == null){
                synchronized(LogPool.class){
                    ourInstance = new LogPool();
                }
            }
        }
        return ourInstance;
    }

    public static void setMaxPoolSize(int maxPoolSize){
        sMaxPoolSize = maxPoolSize;
    }


    void add(int priority, String tag, String msg){
        mLogPool.add(new LogInfo(priority,tag,msg));
        if(mLogPool.size() >= sMaxPoolSize){
            flush();
        }
    }

    public void flush(){
        final LinkedList<LogInfo> cloneList;
        synchronized(LogPool.class){
            cloneList = (LinkedList<LogInfo>)mLogPool.clone();
            mLogPool.clear();
        }
        new Thread(() -> {
            synchronized(LogPool.class){
                try{
                    CharSequence format = DateFormat.format("yy_MM_dd_HH_mm.txt", System.currentTimeMillis());
                    File file = new File(LogUtils.LOG_FILE_PATH,format.toString());
                    File parentFile = file.getParentFile();
                    if(! parentFile.exists()){
                        parentFile.mkdirs();
                    }
                    PrintStream pw = new PrintStream(new FileOutputStream(file,true),true);
                    pw.println(DEVICE_INFO.toString());

                    for(LogInfo info: cloneList){
                        pw.print(info.time);
                        pw.print(" ");
                        pw.print(info.getType());
                        pw.print(" ");
                        pw.print(info.TAG);
                        pw.print(":");
                        pw.print(info.log);
                        pw.println();
                    }
                    pw.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void clear(){
        synchronized(LogPool.class){
            mLogPool.clear();
        }
    }
}
