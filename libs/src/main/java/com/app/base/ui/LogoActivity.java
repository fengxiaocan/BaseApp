package com.app.base.ui;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

/**
 * The type Logo activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 17 :19
 * @描述： logo页面
 */
public abstract class LogoActivity extends BasicActivity implements Runnable {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        onCreate();
    }

    /**
     * On create.
     */
    public abstract boolean isFullScreen();
    public abstract void onCreate();

    @Override
    protected void onStart(){
        super.onStart();
        getRootView().postDelayed(this,taskTime());
    }

    @Override
    protected void onStop(){
        super.onStop();
        getRootView().removeCallbacks(this);
    }

    /**
     * 定时任务
     */
    public abstract void runTask();

    /**
     * 定时任务
     *
     * @return long
     */
    public abstract long taskTime();

    @Override
    public void run() {
        runTask();
    }
}
