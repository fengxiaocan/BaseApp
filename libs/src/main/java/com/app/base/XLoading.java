package com.app.base;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.base.log.*;


public class XLoading {
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_LOAD_SUCCESS = 2;
    public static final int STATUS_LOAD_FAILED = 3;
    public static final int STATUS_EMPTY_DATA = 4;

    private static volatile XLoading mDefault;
    private static boolean DEBUG = false;
    private Adapter mAdapter;

    private XLoading(){
    }

    /**
     * set debug mode or not
     *
     * @param debug true:debug mode, false:not debug mode
     */
    public static void debug(boolean debug){
        DEBUG = debug;
    }

    /**
     * Create a new Gloading different from the default one
     *
     * @param adapter another adapter different from the default one
     * @return Gloading
     */
    public static XLoading from(Adapter adapter){
        XLoading gloading = new XLoading();
        gloading.mAdapter = adapter;
        return gloading;
    }

    /**
     * get default Gloading object for global usage in whole app
     *
     * @return default Gloading object
     */
    public static XLoading getDefault(){
        if(mDefault == null){
            synchronized(XLoading.class){
                if(mDefault == null){
                    mDefault = new XLoading();
                }
            }
        }
        return mDefault;
    }

    /**
     * init the default loading status view creator ({@link Adapter})
     *
     * @param adapter adapter to create all status views
     */
    public static void initDefault(Adapter adapter){
        getDefault().mAdapter = adapter;
    }

    private static void printLog(String msg){
        if(DEBUG){
            LogUtils.e("XLoading",msg);
        }
    }

    /**
     * Gloading(loading status view) wrap the whole activity
     * wrapper is android.R.id.content
     *
     * @param activity current activity object
     * @return holder of Gloading
     */
    public Holder wrap(Activity activity){
        ViewGroup wrapper = activity.findViewById(android.R.id.content);
        return new Holder(mAdapter,activity,wrapper);
    }

    /**
     * Gloading(loading status view) wrap the specific view.
     *
     * @param view view to be wrapped
     * @return Holder
     */
    public Holder wrap(View view){
        FrameLayout wrapper = new FrameLayout(view.getContext());
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(lp != null){
            wrapper.setLayoutParams(lp);
        }
        if(view.getParent() != null){
            ViewGroup parent = (ViewGroup)view.getParent();
            int index = parent.indexOfChild(view);
            parent.removeView(view);
            parent.addView(wrapper,index);
        }
        ViewGroup.LayoutParams newLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        wrapper.addView(view,newLp);
        return new Holder(mAdapter,view.getContext(),wrapper);
    }

    public Holder wrapFragment(View view){
        FrameLayout wrapper = new FrameLayout(view.getContext());
        wrapper.setBackground(view.getBackground());
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(lp != null){
            wrapper.setLayoutParams(lp);
        }
        if(view.getParent() != null){
            ViewGroup parent = (ViewGroup)view.getParent();
            parent.removeView(view);
        }
        wrapper.addView(view);
        return new Holder(mAdapter,view.getContext(),wrapper);
    }

    /**
     * Provides view to show current loading status
     */
    public interface Adapter{
        /**
         * get view for current status
         *
         * @param holder Holder
         * @param status current status
         * @return status view to show. Maybe convertView for reuse.
         * @see Holder
         */
        View getView(Holder holder, int status);
    }

    /**
     * Gloading holder<br>
     * create by {@link XLoading#wrap(Activity)} or {@link XLoading#wrap(View)}<br>
     * the core API for showing all status view
     */
    public static class Holder{
        private Adapter mAdapter;
        private Context mContext;
        private Runnable mRetryTask;
        private View mCurStatusView;
        private ViewGroup mWrapper;
        private int curState;
        private SparseArray<View> mStatusViews = new SparseArray<>();
        private Object mData;

        private Holder(Adapter adapter, Context context, ViewGroup wrapper){
            this.mAdapter = adapter;
            this.mContext = context;
            this.mWrapper = wrapper;
        }

        /**
         * set retry task when user click the retry button in load failed page
         *
         * @param task when user click in load failed UI, run this task
         * @return this
         */
        public Holder withRetry(Runnable task){
            mRetryTask = task;
            return this;
        }

        /**
         * set extension data
         *
         * @param data extension data
         * @return this
         */
        public Holder withData(Object data){
            this.mData = data;
            return this;
        }

        /**
         * show UI for status: {@link #STATUS_LOADING}
         */
        public void showLoading(){
            showXloading(STATUS_LOADING);
        }

        /**
         * show UI for status: {@link #STATUS_LOAD_SUCCESS}
         */
        public void showLoadSuccess(){
            showXloading(STATUS_LOAD_SUCCESS);
        }

        /**
         * show UI for status: {@link #STATUS_LOAD_FAILED}
         */
        public void showLoadFailed(){
            showXloading(STATUS_LOAD_FAILED);
        }

        /**
         * show UI for status: {@link #STATUS_EMPTY_DATA}
         */
        public void showEmpty(){
            showXloading(STATUS_EMPTY_DATA);
        }

        public void hideXloading(){
            if(mCurStatusView != null){
                mWrapper.removeView(mCurStatusView);
            }
            mCurStatusView = null;
            curState = 0;
            for(int i = 0;i < mWrapper.getChildCount();i++){
                mWrapper.getChildAt(i).setVisibility(View.VISIBLE);
            }
        }

        /**
         * Show specific status UI
         *
         * @param status status
         * @see #showLoading()
         * @see #showLoadFailed()
         * @see #showLoadSuccess()
         * @see #showEmpty()
         */
        public void showXloading(int status){
            if(curState == status || ! validate()){
                return;
            }
            curState = status;
            //first try to reuse status view
            View convertView = mStatusViews.get(status);
            if(convertView == null){
                convertView = mAdapter.getView(this,status);
            }
            try{
                //call customer adapter to get UI for specific status. convertView can be reused
                if(convertView == null){
                    printLog(mAdapter.getClass().getName() + ".getView returns null");
                    return;
                }
                if(convertView != mCurStatusView || mWrapper.indexOfChild(convertView) < 0){
                    if(mCurStatusView != null){
                        mWrapper.removeView(mCurStatusView);
                    }
                    for(int i = 0;i < mWrapper.getChildCount();i++){
                        mWrapper.getChildAt(i).setVisibility(View.GONE);
                    }
                    //                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //                        convertView.setElevation(Float.MAX_VALUE);
                    //                    }
                    mWrapper.addView(convertView);
                    ViewGroup.LayoutParams lp = convertView.getLayoutParams();
                    if(lp != null){
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    }
                } else if(mWrapper.indexOfChild(convertView) != mWrapper.getChildCount() - 1){
                    // make sure loading status view at the front
                    convertView.bringToFront();
                }
                mCurStatusView = convertView;
                mStatusViews.put(status,convertView);
            } catch(Exception e){
                if(DEBUG){
                    e.printStackTrace();
                }
            }
        }

        private boolean validate(){
            if(mAdapter == null){
                printLog("Gloading.Adapter is not specified.");
            }
            if(mContext == null){
                printLog("Context is null.");
            }
            if(mWrapper == null){
                printLog("The mWrapper of loading status view is null.");
            }
            return mAdapter != null && mContext != null && mWrapper != null;
        }

        public Context getContext(){
            return mContext;
        }

        /**
         * get wrapper
         *
         * @return container of gloading
         */
        public ViewGroup getWrapper(){
            return mWrapper;
        }

        /**
         * get retry task
         *
         * @return retry task
         */
        public Runnable getRetryTask(){
            return mRetryTask;
        }

        /**
         * get extension data
         *
         * @param <T> return type
         * @return data
         */
        public <T> T getData(){
            try{
                return (T)mData;
            } catch(Exception e){
                if(DEBUG){
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
