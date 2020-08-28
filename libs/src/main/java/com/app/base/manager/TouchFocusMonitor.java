package com.app.base.manager;

import android.app.Activity;
import android.app.Dialog;
import android.view.MotionEvent;
import android.view.View;

import com.app.base.intface.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengxiaocan
 * @desc 监听View的触摸焦点是否在其上
 */
public class TouchFocusMonitor{
    private List<Integer> mMonitorFocusViewById;
    private List<View> mMonitorFocusView;//需要监听获取焦点的View
    private List<View> mIgnoreFocusView;//需要忽略获取焦点的View
    private OnMonitorFocusListener mOnMonitorFocusListener;

    private TouchFocusMonitor(Builder builder){
        mMonitorFocusView = builder.mMonitorFocusView.size() == 0 ? null : builder.mMonitorFocusView;
        mMonitorFocusViewById = builder.mMonitorFocusViewById.size() == 0 ? null : builder.mMonitorFocusViewById;

        mIgnoreFocusView = builder.mIgnoreFocusView.size() == 0 ? null : builder.mIgnoreFocusView;

        mOnMonitorFocusListener = builder.mOnMonitorFocusListener;
    }

    public static Builder builder(){
        return new Builder();
    }


    public void onDispatchTouchEvent(Activity activity, MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            if(isTouchOfView(mIgnoreFocusView,motionEvent)){
                return;
            }
            isTouchView(mMonitorFocusView,motionEvent);
            isTouchView(activity,mMonitorFocusViewById,motionEvent);
        }
    }

    public void onDispatchTouchEvent(View viewParent, MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            if(isTouchOfView(mIgnoreFocusView,motionEvent)){
                return;
            }
            isTouchView(mMonitorFocusView,motionEvent);
            isTouchView(viewParent,mMonitorFocusViewById,motionEvent);
        }
    }

    public void onDispatchTouchEvent(Dialog dialog, MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            if(isTouchOfView(mIgnoreFocusView,motionEvent)){
                return;
            }
            isTouchView(mMonitorFocusView,motionEvent);
            isTouchView(dialog,mMonitorFocusViewById,motionEvent);
        }
    }


    //是否触摸在指定view上面,对某个控件过滤
    private void isTouchView(List<View> views, MotionEvent motionEvent){
        if(views == null || views.size() == 0){
            return;
        }
        int[] location = new int[2];
        for(View view: views){
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if(motionEvent.getX() > x && motionEvent.getX() < (x + view.getMeasuredWidth()) && motionEvent.getY() > y &&
               motionEvent.getY() < (y + view.getMeasuredHeight()))
            {
                focusListener(view,true);
            } else{
                focusListener(view,false);
            }
        }
    }

    private boolean isTouchOfView(List<View> views, MotionEvent motionEvent){
        if(views == null || views.size() == 0){
            return false;
        }
        int[] location = new int[2];
        for(View view: views){
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if(motionEvent.getX() >= x && motionEvent.getX() <= (x + view.getMeasuredWidth()) &&
               motionEvent.getY() >= y && motionEvent.getY() <= (y + view.getMeasuredHeight()))
            {
                return true;
            }
        }
        return false;
    }


    //是否触摸在指定view上面,对某个控件过滤
    private void isTouchView(View viewGroup, List<Integer> views, MotionEvent motionEvent){
        if(views == null || views.size() == 0){
            return;
        }
        int[] location = new int[2];
        for(int viewId: views){
            View view = viewGroup.findViewById(viewId);
            if(view == null){
                continue;
            }
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if(motionEvent.getX() > x && motionEvent.getX() < (x + view.getWidth()) && motionEvent.getY() > y &&
               motionEvent.getY() < (y + view.getHeight()))
            {
                focusListener(view,true);
            } else{
                focusListener(view,false);
            }
        }
    }

    //是否触摸在指定view上面,对某个控件过滤
    private void isTouchView(Dialog dialog, List<Integer> views, MotionEvent motionEvent){
        if(views == null || views.size() == 0){
            return;
        }
        int[] location = new int[2];
        for(int viewId: views){
            View view = dialog.findViewById(viewId);
            if(view == null){
                continue;
            }
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if(motionEvent.getX() > x && motionEvent.getX() < (x + view.getWidth()) && motionEvent.getY() > y &&
               motionEvent.getY() < (y + view.getHeight()))
            {
                focusListener(view,true);
            } else{
                focusListener(view,false);
            }
        }
    }

    //是否触摸在指定view上面,对某个控件过滤
    private void isTouchView(Activity activity, List<Integer> views, MotionEvent motionEvent){
        if(views == null || views.size() == 0){
            return;
        }
        int[] location = new int[2];
        for(int viewId: views){
            View view = activity.findViewById(viewId);
            if(view == null){
                continue;
            }
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if(motionEvent.getX() > x && motionEvent.getX() < (x + view.getWidth()) && motionEvent.getY() > y &&
               motionEvent.getY() < (y + view.getHeight()))
            {
                focusListener(view,true);
            } else{
                focusListener(view,false);
            }
        }
    }

    private void focusListener(View view, boolean isGetFocus){
        if(mOnMonitorFocusListener != null){
            mOnMonitorFocusListener.onFocusChange(view,isGetFocus);
        }
    }


    public static final class Builder{
        private List<Integer> mMonitorFocusViewById = new ArrayList<>();
        private List<View> mMonitorFocusView = new ArrayList<>();
        private List<View> mIgnoreFocusView = new ArrayList<>();
        private OnMonitorFocusListener mOnMonitorFocusListener;

        private Builder(){
        }

        private <T> void add(List<T> mData, T... val){
            if(val == null || val.length == 0){
                return;
            }
            for(T t: val){
                mData.add(t);
            }
        }

        /**
         * 需要监听焦点的View的Id
         *
         * @param val
         * @return
         */
        public Builder monitorFocusView(Integer... val){
            add(mMonitorFocusViewById,val);
            return this;
        }

        public Builder monitorFocusView(int val){
            add(mMonitorFocusViewById,val);
            return this;
        }

        public Builder monitorFocusView(View... val){
            add(mMonitorFocusView,val);
            return this;
        }

        public Builder monitorFocusView(View val){
            add(mMonitorFocusView,val);
            return this;
        }

        public Builder ignoreFocusView(View... val){
            add(mIgnoreFocusView,val);
            return this;
        }

        public Builder ignoreFocusView(View val){
            add(mIgnoreFocusView,val);
            return this;
        }

        public Builder listener(OnMonitorFocusListener val){
            mOnMonitorFocusListener = val;
            return this;
        }

        public TouchFocusMonitor build(){
            return new TouchFocusMonitor(this);
        }
    }
}
