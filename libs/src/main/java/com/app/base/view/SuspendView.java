package com.app.base.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.app.base.*;
import com.app.base.intface.*;
import com.app.base.log.*;
import com.app.tool.Tools;

/**
 * The type Suspend view.
 *
 * @name： BaseApp
 * @package： com.dgtle.baselib.base.view
 * @author: Noah.冯 QQ:1066537317
 * @time: 16 :41
 * @version: 1.1
 * @desc： 悬浮按钮,只在activity中有效
 */
public class SuspendView{

    /**
     * 当前的View容器
     */
    private Context mContext;
    private View mCustomView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowLayoutParams;
    private View.OnTouchListener mTouchListener;
    private OnSuspendClickListener mOnClickListener;
    private OnSuspendLongClickListener mOnLongClickListener;
    private int mStatuBarHeight;
    private int mTouchX;
    private int mTouchY;
    private float alpha = 1.0F;
    /**
     * 是否能滑动
     */
    private boolean isCanMove = true;
    /**
     * 点击时是否改变透明度
     */
    private boolean isClickChangeAlpha = true;
    private boolean isCanClick = true;//是否能点击
    private boolean isMoving = false;//这是属于移动状态
    private boolean isMoveToMargin = false;//释放按钮后是否要移动到边缘
    private long clickTime;//点击时间
    private boolean isShowing = false;
    private Runnable mRunnable = new Runnable(){
        @Override
        public void run(){
            if(isCanClick){
                if(mOnLongClickListener != null){
                    mOnLongClickListener.onLongClick(SuspendView.this);
                }
            }
        }
    };

    /**
     * Instantiates a new Suspend view.
     *
     * @param context the context
     */
    public SuspendView(Context context){
        init(context);
    }

    /**
     * Instantiates a new Suspend view.
     *
     * @param context    the context
     * @param customView the custom view
     */
    public SuspendView(Context context, View customView){
        init(context);
        setCustomView(customView);
    }

    /**
     * Instantiates a new Suspend view.
     *
     * @param context    the context
     * @param customView the custom view
     * @param xPosition  the x position 位置x坐标
     * @param yPosition  the y position 位置y坐标
     */
    public SuspendView(Context context, View customView, int xPosition, int yPosition){
        init(context);
        mWindowLayoutParams.x = xPosition;
        mWindowLayoutParams.y = yPosition;
        setCustomView(customView);
    }

    public Context getContext(){
        return mContext;
    }

    /**
     * 设置坐标点
     * Set position suspend view.
     *
     * @param xPosition the x position位置x坐标
     * @param yPosition the y position位置y坐标
     * @return the suspend view
     */
    public SuspendView setPosition(int xPosition,int yPosition){
        mWindowLayoutParams.x = xPosition;
        mWindowLayoutParams.y = yPosition;
        return this;
    }

    /**
     * 移动该悬浮按钮
     * Move suspend view.
     *
     * @param xPosition the x position位置x坐标
     * @param yPosition the y position位置y坐标
     * @return the suspend view
     */
    public SuspendView move(int xPosition,int yPosition){
        mWindowLayoutParams.x = xPosition - mCustomView.getMeasuredWidth() / 2;
        mWindowLayoutParams.y = yPosition - mCustomView.getMeasuredHeight() / 2;
        mWindowManager.updateViewLayout(mCustomView,mWindowLayoutParams);
        return this;
    }

    /**
     * 移动该悬浮按钮
     * Move suspend view.
     *
     * @param xPosition the x position位置x坐标
     * @param yPosition the y position位置y坐标
     * @return the suspend view
     */
    public SuspendView up(int xPosition,int yPosition){
        if(isMoveToMargin){
            int screenWidth = Tools.Screen.getScreenWidth();
            if(xPosition > screenWidth / 2){
                mWindowLayoutParams.x = screenWidth;
            } else{
                mWindowLayoutParams.x = 0;
            }
        } else{
            mWindowLayoutParams.x = xPosition - mCustomView.getMeasuredWidth() / 2;
        }
        mWindowLayoutParams.y = yPosition - mCustomView.getMeasuredHeight() / 2;
        mWindowManager.updateViewLayout(mCustomView,mWindowLayoutParams);
        return this;
    }

    /**
     * Get on long click listener on suspend long click listener.
     *
     * @return the on suspend long click listener
     */
    public OnSuspendLongClickListener getOnLongClickListener(){
        return mOnLongClickListener;
    }

    /**
     * Set on long click listener.
     *
     * @param onLongClickListener the on long click listener
     */
    public void setOnLongClickListener(OnSuspendLongClickListener onLongClickListener){
        mOnLongClickListener = onLongClickListener;
    }

    /**
     * Is can move boolean.
     *
     * @return the boolean
     */
    public boolean isCanMove(){
        return isCanMove;
    }

    public void setCanMove(boolean canMove){
        isCanMove = canMove;
    }

    /**
     * Set can move.
     */
    public void openMove(){
        isCanMove = true;
    }

    /**
     * Set can't move.
     */
    public void closeMove(){
        isCanMove = false;
    }

    /**
     * Set format suspend view.
     * 设置周围环境模式
     *
     * @param format the format
     * @return the suspend view
     */
    public SuspendView setFormat(int format){
        mWindowLayoutParams.format = format;
        return this;
    }

    /**
     * Set width suspend view.
     *
     * @param width the width
     * @return the suspend view
     */
    public SuspendView setWidth(int width){
        mWindowLayoutParams.width = width;
        return this;
    }

    /**
     * Set height suspend view.
     *
     * @param height the height
     * @return the suspend view
     */
    public SuspendView setHeight(int height){
        mWindowLayoutParams.height = height;
        return this;
    }

    /**
     * Set gravity suspend view.
     * 设置方位
     *
     * @param gravity the gravity
     * @return the suspend view
     */
    public SuspendView setGravity(int gravity){
        mWindowLayoutParams.gravity = gravity;
        return this;
    }

    /**
     * Various behavioral options/flags.  Default is none.
     * 设置模式
     * <p>WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON</p>
     * <p>WindowManager.LayoutParams.FLAG_DIM_BEHIND</p>
     * <p>WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE</p>
     * <p>WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE</p>
     * <p>WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL</p>
     * <p>WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING</p>
     * <p>WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON</p>
     * <p>WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN</p>
     * <p>WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS</p>
     * <p>WindowManager.LayoutParams.FLAG_FULLSCREEN</p>
     * <p>WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN</p>
     * <p>WindowManager.LayoutParams.FLAG_SECURE</p>
     * <p>WindowManager.LayoutParams.FLAG_SCALED</p>
     * <p>WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES</p>
     * <p>WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR</p>
     * <p>WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM</p>
     * <p>WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH</p>
     * <p>WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED</p>
     * <p>WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER</p>
     * <p>WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON</p>
     * <p>WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD</p>
     * <p>WindowManager.LayoutParams.FLAG_SPLIT_TOUCH</p>
     * <p>WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED</p>
     * <p>WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE</p>
     * <p>WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS</p>
     *
     * @param flags the flags
     * @return the suspend view
     */
    public SuspendView setFlags(int flags){
        mWindowLayoutParams.flags = flags;
        return this;
    }

    public boolean isClickChangeAlpha(){
        return isClickChangeAlpha;
    }

    public void setClickChangeAlpha(boolean clickChangeAlpha){
        isClickChangeAlpha = clickChangeAlpha;
    }

    /**
     * Set alpha suspend view.
     * 设置透明度
     *
     * @param alpha the alpha
     * @return the suspend view
     */
    public SuspendView setAlpha(float alpha){
        mWindowLayoutParams.alpha = alpha;
        this.alpha = alpha;
        return this;
    }

    /**
     * 设置类型
     *
     * @param type the alpha
     * @return the suspend view
     */
    public SuspendView setType(int type){
        mWindowLayoutParams.type = type;
        return this;
    }

    /**
     * Create suspend view.
     * 创建
     * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
     *
     * @return the suspend view
     */
    public SuspendView show(){
        if(! isShowing){
            mCustomView.setOnTouchListener(mTouchListener);
            try{
                mWindowManager.addView(mCustomView,mWindowLayoutParams);
                isShowing = true;
            } catch(Exception e){
                LogUtils.e("SuspendView","show()--->没有在其他窗口上运行的权限");
                if(Build.VERSION.SDK_INT >= 23){
                    //必须先判断是否有在其他窗口上运行的权限
                    if(! Settings.canDrawOverlays(getContext())){
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        getContext().startActivity(intent);
                    }
                }
            }
        }
        return this;
    }

    /**
     * Cancel immediate suspend view.
     * 马上取消
     *
     * @return the suspend view
     */
    public SuspendView cancelImmediate(){
        isShowing = false;
        mWindowManager.removeViewImmediate(mCustomView);
        return this;
    }

    /**
     * Cancel suspend view.
     * 取消
     *
     * @return the suspend view
     */
    public SuspendView cancel(){
        isShowing = false;
        mWindowManager.removeView(mCustomView);
        return this;
    }

    /**
     * Is showing boolean.
     *
     * @return the boolean
     */
    public boolean isShowing(){
        return isShowing;
    }

    /**
     * Get custom view view.
     *
     * @return the view
     */
    public View getCustomView(){
        return mCustomView;
    }

    /**
     * Set custom view suspend view.
     *
     * @param customView the custom view
     * @return the suspend view
     */
    public SuspendView setCustomView(View customView){
        mCustomView = customView;
        return this;
    }

    /**
     * 设置触摸
     *
     * @param onTouchEvent
     * @return
     */
    private SuspendView setOnTouchEvent(View.OnTouchListener onTouchEvent){
        mTouchListener = onTouchEvent;
        return this;
    }

    /**
     * 是否能移动到边缘
     *
     * @return
     */
    public boolean isMoveToMargin(){
        return isMoveToMargin;
    }

    /**
     * 设置是否能移动到边缘
     */
    public void setMoveToMargin(boolean moveToMargin){
        isMoveToMargin = moveToMargin;
    }

    /**
     * Get window layout params window manager . layout params.
     *
     * @return the window manager . layout params
     */
    public WindowManager.LayoutParams getWindowLayoutParams(){
        return mWindowLayoutParams;
    }

    /**
     * Set window layout params suspend view.
     *
     * @param windowLayoutParams the window layout params
     * @return the suspend view
     */
    public SuspendView setWindowLayoutParams(WindowManager.LayoutParams windowLayoutParams){
        mWindowLayoutParams = windowLayoutParams;
        return this;
    }

    /**
     * Get on click listener on suspend click listener.
     *
     * @return the on suspend click listener
     */
    public OnSuspendClickListener getOnClickListener(){
        return mOnClickListener;
    }

    /**
     * Set on click listener.
     *
     * @param onClickListener the on click listener
     */
    public void setOnClickListener(OnSuspendClickListener onClickListener){
        mOnClickListener = onClickListener;
    }

    private void init(Context context){
        mContext = context;
        mWindowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mStatuBarHeight = Tools.Bar.getStatusBarHeight();
        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.format = PixelFormat.RGBA_8888; //图片之外的其他地方透明
        mWindowLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWindowLayoutParams.alpha = 1.0f; //透明度
        alpha = 1.0F;
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //        mWindowLayoutParams.type = WindowManager.LayoutParams
        // .TYPE_APPLICATION;
        mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;

        mTouchListener = new OnTouchEvent();
    }

    public boolean isMoving(){
        return isMoving;
    }

    public boolean isCanClick(){
        return isCanClick;
    }

    public void setCanClick(boolean canClick){
        isCanClick = canClick;
    }

    private class OnTouchEvent implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event){
            int x = (int)event.getRawX();
            int y = (int)event.getRawY() - mStatuBarHeight;
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mTouchX = x;
                    mTouchY = y;
                    isMoving = false;
                    if(isCanClick){
                        clickTime = System.currentTimeMillis();
                        if(mOnLongClickListener != null){
                            v.postDelayed(mRunnable,1500);
                        }
                    }
                    if(isClickChangeAlpha){
                        mWindowLayoutParams.alpha = 1.0f;
                    }
                    mWindowManager.updateViewLayout(mCustomView,mWindowLayoutParams);
                    break;
                case MotionEvent.ACTION_MOVE:

                    if(isMoving){
                        if(isCanMove){
                            if(isClickChangeAlpha){
                                mWindowLayoutParams.alpha = 1.0f;
                            }
                            move(x,y);
                        }
                        v.removeCallbacks(mRunnable);
                    } else{
                        if(mTouchX == x && mTouchY == y){
                            //坐标点相同,为未移动
                        } else{
                            if(isCanMove){
                                if(isClickChangeAlpha){
                                    mWindowLayoutParams.alpha = 1.0f;
                                }
                                move(x,y);
                            }
                            v.removeCallbacks(mRunnable);
                            isMoving = true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(isCanMove){
                        mWindowLayoutParams.alpha = alpha;
                        if(isMoving){
                            up(x,y);
                        } else{
                            mWindowManager.updateViewLayout(mCustomView,mWindowLayoutParams);
                        }
                    }
                    v.removeCallbacks(mRunnable);
                    if(isCanClick){
                        if(mOnClickListener != null){
                            //点击
                            if(! isMoving && System.currentTimeMillis() - clickTime < 500){
                                mOnClickListener.onClick(SuspendView.this);
                            }
                        }
                    }
                    break;
            }
            return isCanMove;
        }
    }
    /**
     * 应用程序窗口。
     public static final int FIRST_APPLICATION_WINDOW = 1;
     所有程序窗口的“基地”窗口，其他应用程序窗口都显示在它上面。
     public static final int TYPE_BASE_APPLICATION   =1;
     普通应哟功能程序窗口。token必须设置为Activity的token，以指出该窗口属谁。
     public static final int TYPE_APPLICATION       = 2;
     用于应用程序启动时所显示的窗口。应用本身不要使用这种类型。
     它用于让系统显示些信息，直到应用程序可以开启自己的窗口。
     public static final int TYPE_APPLICATION_STARTING = 3;
     应用程序窗口结束。
     public static final int LAST_APPLICATION_WINDOW = 99;
     子窗口。子窗口的Z序和坐标空间都依赖于他们的宿主窗口。
     public static final int FIRST_SUB_WINDOW       = 1000;
     面板窗口，显示于宿主窗口上层。
     public static final int TYPE_APPLICATION_PANEL  = FIRST_SUB_WINDOW;
     媒体窗口，例如视频。显示于宿主窗口下层。
     public static final int TYPE_APPLICATION_MEDIA  = FIRST_SUB_WINDOW+1;
     应用程序窗口的子面板。显示于所有面板窗口的上层。（GUI的一般规律，越“子”越靠上）
     public static final int TYPE_APPLICATION_SUB_PANEL = FIRST_SUB_WINDOW +2;
     对话框。类似于面板窗口，绘制类似于顶层窗口，而不是宿主的子窗口。
     public static final int TYPE_APPLICATION_ATTACHED_DIALOG =
     FIRST_SUB_WINDOW +3;
     媒体信息。显示在媒体层和程序窗口之间，需要实现透明（半透明）效果。（例如显示字幕）
     public static final int TYPE_APPLICATION_MEDIA_OVERLAY  =
     FIRST_SUB_WINDOW +4;
     子窗口结束。（ End of types of sub-windows ）
     public static final int LAST_SUB_WINDOW        = 1999;
     系统窗口。非应用程序创建。
     public static final int FIRST_SYSTEM_WINDOW    = 2000;
     状态栏。只能有一个状态栏；它位于屏幕顶端，其他窗口都位于它下方。
     public static final int TYPE_STATUS_BAR        =  FIRST_SYSTEM_WINDOW;
     搜索栏。只能有一个搜索栏；它位于屏幕上方。
     public static final int TYPE_SEARCH_BAR        = FIRST_SYSTEM_WINDOW+1;
     电话窗口。它用于电话交互（特别是呼入）。它置于所有应用程序之上，状态栏之下。
     public static final int TYPE_PHONE            = FIRST_SYSTEM_WINDOW+2;
     系统提示。它总是出现在应用程序窗口之上。
     public static final int TYPE_SYSTEM_ALERT      =  FIRST_SYSTEM_WINDOW +3;
     锁屏窗口。
     public static final int TYPE_KEYGUARD          = FIRST_SYSTEM_WINDOW +4;
     信息窗口。用于显示toast。
     public static final int TYPE_TOAST            = FIRST_SYSTEM_WINDOW +5;
     系统顶层窗口。显示在其他一切内容之上。此窗口不能获得输入焦点，否则影响锁屏。
     public static final int TYPE_SYSTEM_OVERLAY    =  FIRST_SYSTEM_WINDOW +6;
     电话优先，当锁屏时显示。此窗口不能获得输入焦点，否则影响锁屏。
     public static final int TYPE_PRIORITY_PHONE    =  FIRST_SYSTEM_WINDOW +7;
     系统对话框。（例如音量调节框）。
     public static final int TYPE_SYSTEM_DIALOG     =  FIRST_SYSTEM_WINDOW +8;
     锁屏时显示的对话框。
     public static final int TYPE_KEYGUARD_DIALOG   =  FIRST_SYSTEM_WINDOW +9;
     系统内部错误提示，显示于所有内容之上。
     public static final int TYPE_SYSTEM_ERROR      =  FIRST_SYSTEM_WINDOW +10;
     内部输入法窗口，显示于普通UI之上。应用程序可重新布局以免被此窗口覆盖。
     public static final int TYPE_INPUT_METHOD      =  FIRST_SYSTEM_WINDOW +11;
     内部输入法对话框，显示于当前输入法窗口之上。
     public static final int TYPE_INPUT_METHOD_DIALOG= FIRST_SYSTEM_WINDOW +12;
     墙纸窗口。 public static final int TYPE_WALLPAPER         =
     FIRST_SYSTEM_WINDOW +13;
     状态栏的滑动面板。 public static final int TYPE_STATUS_BAR_PANEL   =
     FIRST_SYSTEM_WINDOW +14;
     系统窗口结束。     public static final int LAST_SYSTEM_WINDOW     = 2999;
     *
     */
}
