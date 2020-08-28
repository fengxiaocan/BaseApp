package com.app.base;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;

import com.app.base.litepal.*;
import com.app.base.received.*;
import com.app.tool.Tools;

/**
 * The type Base application.
 *
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.base
 * @创建者: Noah.冯
 * @时间: 16 :43
 * @描述： Application基类
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Tools.initApplication(this);
        if(interceptOtherProcess()){
            if(getPackageName().equals(Tools.App.getProcessName())){
                initCreate();
            }
        } else{
            initCreate();
        }
    }

    /**
     * Init create.初始化入口
     */
    public void initCreate(){
        RxLitepal.initialize(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);  //广播接收器想要监听什么广播，就在这里添加相应的action
            registerReceiver(new NetworksBroadcastReceiver(),intentFilter);
        }
    }

    /**
     * <p>是否拦截非包名进程初始化</p>
     * <p>引入其他的第三方sdk，可能会造成多个以当前包名+：xxx 进程启动，造成application的onCreate方法多次启动</p>
     *
     * @return 默认为拦截 boolean
     */
    public boolean interceptOtherProcess(){
        return true;
    }

    /**
     * 程序终止时调用，可以手动调用，在此处释放某些内存引用。
     */
    @Override
    public void onTerminate(){
        super.onTerminate();
    }

}
