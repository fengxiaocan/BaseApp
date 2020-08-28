package com.app.base.back;

import android.app.Activity;
import android.view.View;


/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.helper
 * @创建者: Noah.冯
 * @时间: 11:22
 * @描述： 侧滑返回的工具
 */

public class SwipeBackAdapter implements SwipeBackActivityBase{

    private SwipeBackActivityHelper mHelper;
    private Activity mActivity;

    public SwipeBackAdapter(Activity activity){
        mActivity = activity;
        mHelper = new SwipeBackActivityHelper(activity);
        mHelper.onActivityCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout(){
        return mHelper.getSwipeBackLayout();
    }

    public SwipeBackActivityHelper getHelper(){
        return mHelper;
    }

    @Override
    public void setSwipeBackEnable(boolean enable){
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity(){
        SwipeUtils.convertActivityToTranslucent(mActivity);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    public void onPostCreate(){
        mHelper.getSwipeBackLayout().attachToActivity(mActivity);
    }

    public <T extends View> T findViewById(int id){
        if(mHelper.getSwipeBackLayout() != null){
            return ((T)mHelper.getSwipeBackLayout().findViewById(id));
        }
        return null;
    }

    /**
     * 设置返回滑动的方向
     *
     * @param flags
     */
    public void setSwipeFlags(SwipeFlags flags){
        switch(flags){
            default:
            case LEFT:
                mHelper.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
                break;
            case RIGHT:
                mHelper.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
                break;
            case BOTTOM:
                mHelper.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM);
                break;
            case ALL:
                mHelper.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
                break;
        }
    }

    public enum SwipeFlags{
        LEFT,
        RIGHT,
        BOTTOM,
        ALL
    }
}
