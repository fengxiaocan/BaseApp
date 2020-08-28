package com.app.base.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.app.tool.IntentBuilder;

/**
 * The type App fragment.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base
 * @创建者: Noah.冯
 * @时间: 14 :17
 * @描述： Fragment基类
 */
public class AppFragment extends Fragment implements View.OnClickListener{

    //	protected CommonProgressDialog mLoadingDialog;

    protected boolean isResume = false;

    /**
     * 设置点击事件
     *
     * @param view the view
     */
    public void setOnClick(View view){
        if(view != null){
            view.setOnClickListener(this);
        }
    }

    /**
     * Set on click.
     *
     * @param views the views
     */
    public void setOnClick(View... views){
        for(View view: views){
            if(view != null){
                view.setOnClickListener(this);
            }
        }
    }

    /**
     * 打开一个界面
     *
     * @param clazz    the clazz
     * @param isFinish the is finish
     */
    public void openActivity(Class<? extends BasicActivity> clazz, boolean isFinish){
        openActivity(clazz,null,isFinish);
    }

    /**
     * 打开一个界面
     *
     * @param clazz  the clazz
     * @param bundle the bundle
     */
    public void openActivity(Class<? extends BasicActivity> clazz, Bundle bundle){
        openActivity(clazz,bundle,false);
    }

    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     */
    public void openActivity(Class<? extends BasicActivity> clazz){
        openActivity(clazz,null,false);
    }

    /**
     * 打开一个界面
     *
     * @param clazz    the clazz
     * @param bundle   the bundle
     * @param isFinish the is finish
     */
    public void openActivity(Class<? extends BasicActivity> clazz, Bundle bundle, boolean isFinish){
        Intent intent = new Intent(getContext(),clazz);
        if(bundle != null){
            intent.putExtra("Bundle",bundle);
        }
        startActivity(intent);
        if(isFinish){
            getActivity().finish();
        }
    }

    /**
     * 打开一个等待返回结果的界面
     *
     * @param clazz       the clazz
     * @param bundle      the bundle
     * @param requestCode the request code
     */
    public void openActivityForResult(Class<? extends BasicActivity> clazz, Bundle bundle, int requestCode){
        Intent intent = new Intent(getContext(),clazz);
        if(bundle != null){
            intent.putExtra("Bundle",bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * 打开一个等待返回结果的界面
     *
     * @param clazz       the clazz
     * @param requestCode the request code
     */
    public void openActivityForResult(Class<? extends BasicActivity> clazz, int requestCode){
        openActivityForResult(clazz,null,requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }

    /**
     * 新建一个bundle
     *
     * @return bundle
     */
    public Bundle newBundle(){
        return new Bundle();
    }

    @Override
    public void onClick(View v){

    }

    public IntentBuilder intent(Class<? extends Activity> clazz){
        return new IntentBuilder(this,clazz);
    }

    public IntentBuilder intent(String action, Uri uri){
        return new IntentBuilder(action,uri,this);
    }

    public IntentBuilder intent(String action){
        return new IntentBuilder(action,this);
    }


    @Override
    public void onResume(){
        super.onResume();
        isResume = true;
    }

    @Override
    public void onPause(){
        super.onPause();
        isResume = false;
    }
}
