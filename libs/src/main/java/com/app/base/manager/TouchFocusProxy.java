package com.app.base.manager;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.app.base.intface.*;
import com.app.tool.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengxiaocan
 * @desc 一个可以监听焦点消息的工具
 */
public class TouchFocusProxy{
    private static Map<String,TouchFocusProxy> helperHashMap;
    private boolean isHideKeyboard;//是否隐藏系统键盘
    private boolean isClearFocusOfView;//最终是否需要清除监听失去焦点的View
    //    private List<Integer> mLoseFocusViewById;//需要监听失去焦点的View的ID
    private List<View> mLoseFocusView;//需要监听失去焦点的View的ID
    //    private List<Integer> mFilterViewById;//需要过滤忽略的控件
    private List<View> mFilterViews;//需要过滤忽略的控件,即是
    private OnLoseFocusListener mOnLoseFoucsListener;

    private TouchFocusProxy(Activity activity, Builder builder){
        isHideKeyboard = builder.isHideKeyboard;
        isClearFocusOfView = builder.isClearFocusOfView;
        //        mLoseFocusViewById =
        //                builder.mLoseFocusViewById.size() == 0 ? null : builder.mLoseFocusViewById;
        mLoseFocusView = builder.mLoseFocusView.size() == 0 ? null : builder.mLoseFocusView;
        //        mFilterViewById = builder.mFilterViewById.size() == 0 ? null : builder.mFilterViewById;
        mFilterViews = builder.mFilterViews.size() == 0 ? null : builder.mFilterViews;
        mOnLoseFoucsListener = builder.mOnLoseFoucsListener;

        if(helperHashMap == null){
            helperHashMap = new HashMap<>();
        }
        String key = activity.toString();
        helperHashMap.put(key,this);
    }

    /**
     * 初始化Activity
     */
    public static void onDispatchTouchEvent(@NonNull Activity activity, MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            if(helperHashMap != null){
                String key = activity.toString();
                TouchFocusProxy proxy = helperHashMap.get(key);
                if(proxy != null){
                    proxy.dispose(activity,motionEvent);
                }
            }
        }
    }

    public static void unregister(Activity activity){
        if(helperHashMap != null){
            String key = activity.toString();
            helperHashMap.remove(key);
            if(helperHashMap !=null && helperHashMap.size() == 0){
                helperHashMap = null;
            }
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    /**
     * 是否有需要监视的丢失焦点的控件
     */
    private boolean hasMonitorLoseFocus(){
        return mLoseFocusView != null && mLoseFocusView.size()>0;
    }

    /**
     * 是否有需要过滤的控件
     *
     * @return
     */
    private boolean hasFilterFocus(){
        return mFilterViews != null && mFilterViews.size()>0;
    }

    private void dispose(Activity activity, MotionEvent motionEvent){

        if(! hasMonitorLoseFocus()){
            return;
        }
        //            if (isTouchView(activity, mFilterViewById, motionEvent)) {
        //                return;
        //            }
        if(isTouchView(mFilterViews,motionEvent)){
            return;
        }
        //            if (isTouchView(activity, mLoseFocusViewById, motionEvent)) {
        //                return;
        //            }
        //是否触摸在监听View的位置上
        if(isTouchView(mLoseFocusView,motionEvent)){
            return;
        }

        //隐藏键盘
        if(isHideKeyboard){
            Tools.Keyboard.hideSoftInput(activity);
        }
        if(isClearFocusOfView){
            clearViewFocus(activity.getCurrentFocus(),mLoseFocusView);
        }
        if(mOnLoseFoucsListener != null){
            mOnLoseFoucsListener.onLoseFocus(motionEvent);
        }
    }

    /**
     * 清除View的焦点
     *
     * @param v 焦点所在View
     */
    private void clearViewFocusInt(View v, List<Integer> ids){
        if(ids !=null && ids.size() == 0){
            return;
        }
        for(int id: ids){
            if(v.getId() == id){
                v.clearFocus();
                break;
            }
        }
    }

    /**
     * 清除View的焦点
     *
     * @param v 焦点所在View
     */
    private void clearViewFocus(View v, List<View> views){
        if(views !=null && views.size() == 0){
            return;
        }
        for(View view: views){
            if(v.getId() == view.getId()){
                v.clearFocus();
                break;
            }
        }
    }


    //region软键盘的处理

    /**
     * 隐藏键盘
     * 焦点是否在View上
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在View上
     */
    private boolean isFocusViewInt(View v, List<Integer> ids){
        if(ids !=null && ids.size() == 0){
            return false;
        }
        for(int id: ids){
            if(v.getId() == id){
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏键盘
     * 焦点是否在View上
     *
     * @param v     焦点所在View
     * @param views 需要监听失去焦点的View
     * @return true代表焦点在View上
     */
    private boolean isFocusView(View v, List<View> views){
        if(views !=null && views.size() == 0){
            return false;
        }
        for(View view: views){
            if(v.getId() == view.getId()){
                return true;
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    private boolean isTouchView(List<View> views, MotionEvent motionEvent){
        if(views !=null && views.size() == 0){
            return false;
        }
        int[] location = new int[2];
        for(View view: views){
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if(motionEvent.getX() > x && motionEvent.getX() < (x + view.getWidth()) && motionEvent.getY() > y &&
               motionEvent.getY() < (y + view.getHeight()))
            {
                return true;
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    private boolean isTouchView(Activity activities, List<Integer> ids, MotionEvent motionEvent){
        if(ids !=null && ids.size() == 0){
            return false;
        }
        int[] location = new int[2];
        for(int id: ids){
            View view = activities.findViewById(id);
            if(view == null){
                continue;
            }
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if(motionEvent.getX() > x && motionEvent.getX() < (x + view.getWidth()) && motionEvent.getY() > y &&
               motionEvent.getY() < (y + view.getHeight()))
            {
                return true;
            }
        }
        return false;
    }

    public static final class Builder{
        private boolean isHideKeyboard;
        private boolean isClearFocusOfView;
        //        private List<Integer> mLoseFocusViewById = new ArrayList<>();//需要监听失去焦点的View的ID
        private List<View> mLoseFocusView = new ArrayList<>();//需要监听失去焦点的View的ID
        //        private List<Integer> mFilterViewById = new ArrayList<>();//需要过滤忽略的控件
        private List<View> mFilterViews = new ArrayList<>();//需要过滤忽略的控件,即是
        private OnLoseFocusListener mOnLoseFoucsListener;

        private Builder(){
        }

        public Builder hideKeyboard(boolean val){
            isHideKeyboard = val;
            return this;
        }

        public Builder clearFocus(boolean val){
            isClearFocusOfView = val;
            return this;
        }

        private <T> void add(List<T> mData, T... val){
            //            if (ObjectUtils.isEmpty(val)) {
            //                return;
            //            }
            for(T t: val){
                mData.add(t);
            }
        }

        //        public Builder loseFocusView(Integer... val) {
        //            add(mLoseFocusViewById, val);
        //            return this;
        //        }

        //        public Builder loseFocusView(Integer val) {
        //            add(mLoseFocusViewById, val);
        //            return this;
        //        }

        public Builder loseFocusView(View... val){
            add(mLoseFocusView,val);
            return this;
        }

        public Builder loseFocusView(View val){
            add(mLoseFocusView,val);
            return this;
        }

        //        public Builder filterView(Integer... val) {
        //            add(mFilterViewById, val);
        //            return this;
        //        }
        //
        //        public Builder filterView(int val) {
        //            add(mFilterViewById, val);
        //            return this;
        //        }

        public Builder filterView(View... val){
            add(mFilterViews,val);
            return this;
        }

        public Builder filterView(View val){
            add(mFilterViews,val);
            return this;
        }

        public Builder loseFoucsListener(OnLoseFocusListener val){
            mOnLoseFoucsListener = val;
            return this;
        }

        public TouchFocusProxy register(Activity activity){
            return new TouchFocusProxy(activity,this);
        }
    }
}
