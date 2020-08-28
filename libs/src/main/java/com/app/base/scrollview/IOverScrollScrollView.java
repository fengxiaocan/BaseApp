package com.app.base.scrollview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


/**
 * https://github.com/Mixiaoxiao/OverScroll-Everywhere
 *
 * @author Mixiaoxiao 2016-08-31
 */
public class IOverScrollScrollView extends ScrollView implements IOverScrollable {

    private VerticalOverScrollDelegate mVerticalOverScrollDelegate;

    // ===========================================================
    // Constructors
    // ===========================================================
    public IOverScrollScrollView(Context context) {
        super(context);
        createOverScrollDelegate(context);
    }

    public IOverScrollScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createOverScrollDelegate(context);
    }

    public IOverScrollScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createOverScrollDelegate(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IOverScrollScrollView(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        createOverScrollDelegate(context);
    }

    // ===========================================================
    // createOverScrollDelegate
    // ===========================================================
    private void createOverScrollDelegate(Context context) {
        mVerticalOverScrollDelegate = new VerticalOverScrollDelegate(this);
    }

    // ===========================================================
    // Delegate
    // ===========================================================
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mVerticalOverScrollDelegate.onInterceptTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVerticalOverScrollDelegate.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        mVerticalOverScrollDelegate.draw(canvas);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
            int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
            boolean isTouchEvent)
    {
        return mVerticalOverScrollDelegate.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    // ===========================================================
    // IOverScrollable, aim to call view internal methods
    // ===========================================================

    @Override
    public int superComputeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override
    public int superComputeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    @Override
    public int superComputeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    public int superComputeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @Override
    public int superComputeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override
    public int superComputeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }
    @Override
    public void superOnTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
    }

    @Override
    public void superDraw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public boolean superAwakenScrollBars() {
        return super.awakenScrollBars();
    }

    @Override
    public boolean superOverScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
            int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
            boolean isTouchEvent)
    {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public View getOverScrollableView() {
        return this;
    }

    @Override
    public VerticalOverScrollDelegate getVerticalOverScrollDelegate() {
        return mVerticalOverScrollDelegate;
    }

    @Override
    public HorizontalOverScrollDelegate getHorizontalOverScrollDelegate() {
        return null;
    }

}
