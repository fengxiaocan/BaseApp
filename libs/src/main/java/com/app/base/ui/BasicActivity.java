package com.app.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.app.tool.IntentBuilder;

/**
 * The type Basic activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 11 :48
 * @描述： 基类activity
 */
public class BasicActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * 获取bundle
     *
     * @return bundle bundle
     */
    public Bundle getBundle() {
        Intent intent = getIntent();
        return intent.getBundleExtra("Bundle");
    }

    /**
     * 获取上下文
     *
     * @return context context
     */
    public Context getContext() {
        return this;
    }

    /**
     * 获取当前activity的快捷方式
     *
     * @return activity activity
     */
    public Activity getActivity() {
        return this;
    }

    /**
     * 获取根节点View
     *
     * @return view view
     */
    public View getRootView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 获取根节点Group
     *
     * @return view view
     */
    public ViewGroup getRootViewGroup() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * 打开一个界面
     *
     * @param clazz    the clazz
     * @param isFinish the is finish
     */
    public void openActivity(Class<? extends Activity> clazz, boolean isFinish) {
        openActivity(clazz, null, isFinish);
    }

    /**
     * 打开一个界面
     *
     * @param clazz  the clazz
     * @param bundle the bundle
     */
    public void openActivity(Class<? extends Activity> clazz, Bundle bundle) {
        openActivity(clazz, bundle, false);
    }

    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     */
    public void openActivity(Class<? extends Activity> clazz) {
        openActivity(clazz, null, false);
    }

    /**
     * 打开一个界面
     *
     * @param clazz    the clazz
     * @param bundle   the bundle
     * @param isFinish the is finish
     */
    public void openActivity(Class<? extends Activity> clazz, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtra("Bundle", bundle);
        }
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    /**
     * 打开一个等待返回结果的界面
     *
     * @param clazz       the clazz
     * @param bundle      the bundle
     * @param requestCode the request code
     */
    public void openActivityForResult(Class<? extends Activity> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtra("Bundle", bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 打开一个等待返回结果的界面
     *
     * @param clazz       the clazz
     * @param requestCode the request code
     */
    public void openActivityForResult(Class<? extends Activity> clazz, int requestCode) {
        openActivityForResult(clazz, null, requestCode);
    }

    /**
     * 新建一个bundle
     *
     * @return bundle bundle
     */
    public Bundle newBundle() {
        return new Bundle();
    }

    public IntentBuilder intent(Class<? extends Activity> clazz) {
        return new IntentBuilder(this, clazz);
    }

    public IntentBuilder intent(String action, Uri uri) {
        return new IntentBuilder(action, uri, this);
    }

    public IntentBuilder intent(String action) {
        return new IntentBuilder(action, this);
    }

    /**
     * 设置点击事件
     *
     * @param view the view
     */
    public void setOnClick(View view) {
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    /**
     * Set on click.
     *
     * @param views the views
     */
    public void setOnClick(View... views) {
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * Set full screen.
     * 设置是否全屏
     *
     * @param isFullScreen the is full screen
     */
    public void setFullScreen(boolean isFullScreen) {
        if (!isFullScreen) {//设置为非全屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {//设置为全屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

}
