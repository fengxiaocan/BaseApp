package com.app.base.scrollview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import static com.app.base.scrollview.OverScrollStyle.DEFAULT_DRAW_TRANSLATE_RATE;


public class HorizontalOverScrollDelegate implements Runnable {

    public static final int OS_NONE = 0;// OS = "OverScroll"
    public static final int OS_DRAG_TOP = 1;
    public static final int OS_DRAG_BOTTOM = 2;
    public static final int OS_SPRING_BACK = 3;
    public static final int OS_FLING = 4;
    private static final OverScrollStyle sDefaultStyle = new OverScrollStyle() {};
    private static final PathScroller.PathPointsHolder sDragBackPathPointsHolder =
            buildDragBackPathPointsHolder();
    private static final PathScroller.PathPointsHolder sFlingBackPathPointsHolder =
            buildFlingBackPathPointsHolder();
    private static final int DRAG_BACK_DURATION = 420;
    private static final int FLING_BACK_DURATION = 550;
    private static final int INVALID_POINTER = -1;
    private final int mTouchSlop;
    private final PathScroller mScroller;
    private final View mView;
    private int mState = OS_NONE;
    private float mLastMotionX;
    private float mOffsetX;
    private int mActivePointerId = INVALID_POINTER;
    private IOverScrollable mIOverScrollable;
    private boolean mEnableDragOverScroll = true;
    private boolean mEnableFlingOverScroll = true;
    private OverScrollStyle mStyle;
    private IOnOverScrollLitener iOnOverScrollLitener;

    public HorizontalOverScrollDelegate(IOverScrollable IOverScrollable) {
        this.mView = IOverScrollable.getOverScrollableView();
        if (mView instanceof RecyclerView) {
            mView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        } else {
            mView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        this.mIOverScrollable = IOverScrollable;
        Context context = mView.getContext();
        mScroller = new PathScroller();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // TouchSlop() / 2 to make TouchSlop "more sensible"
        mTouchSlop = configuration.getScaledTouchSlop() / 2;// 8dp/2
        mStyle = sDefaultStyle;
    }

    private static PathScroller.PathPointsHolder buildDragBackPathPointsHolder() {
        final float startX = 1f;
        Path path = new Path();
        //		path.moveTo(0, startY);
        //		path.cubicTo(0.15f, 0.11f, 0.56f, 0.10f, 1f, 0f);
        path.moveTo(startX, 0);
        path.cubicTo(0.11f, 0.11f, 0.05f, 0.36f, 0f, 1f);
        return new PathScroller.PathPointsHolder(path);
    }

    private static PathScroller.PathPointsHolder buildFlingBackPathPointsHolder() {
        final float baseOverY = 1f;
        Path path = new Path();
        path.moveTo(0f, 0f);
        //		path.cubicTo(0.1f, overY * 0.6f, 0.15f, overY * 1.0f, 0.30f, overY);
        //		path.cubicTo(0.44f, overY * 1.00f, 0.58f, overY * 0.02f, 1f, 0f);
        path.cubicTo(baseOverY * 0.8f, 0.05f, baseOverY * 1.20f, 0.09f, baseOverY * 0.88f, 0.21f);
        path.cubicTo(baseOverY * 0.10f, 0.48f, baseOverY * 0.02f, 0.72f, 0f, 1f);
        return new PathScroller.PathPointsHolder(path);
    }

    // ===========================================================
    // Customization
    // ===========================================================
    public void setOverScrollStyle(OverScrollStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("OverScrollStyle should NOT be NULL!");
        }
        mStyle = style;
    }

    // private final Path mPath = new Path();

    /**
     * Enable drag/fling-overscroll
     *
     * @param dragOverScroll
     * @param flingOverScroll
     */
    public void setOverScrollType(boolean dragOverScroll, boolean flingOverScroll) {
        mEnableDragOverScroll = dragOverScroll;
        mEnableFlingOverScroll = flingOverScroll;
    }

    // ===========================================================
    // Delegate
    // ===========================================================
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mEnableDragOverScroll) {
            return onInterceptTouchEventInternal(event);
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (mEnableDragOverScroll) {
            return onTouchEventInternal(event);
        }
        return false;
    }

    /**
     * In RecyclerView, overScrollBy does not work. Call absorbGlows instead of
     * this method. If super.overScrollBy return true and isTouchEvent, means
     * current scroll is fling-overscroll, we use the deltaY to compute
     * velocityY.
     */
    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
            int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
    {
        maxOverScrollX = maxOverScrollY = 0;
        final boolean overScroll = mIOverScrollable.superOverScrollBy(deltaX, deltaY, scrollX,
                scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        if (!mEnableFlingOverScroll) {
            return overScroll;
        }
        if (!overScroll) {
            if (mState == OS_FLING) {
            }
        } else {// overScroll = true;
            if (!isTouchEvent) {
                // isTouchEvent=false, means fling by the scroller of View
                if (mState != OS_FLING) {
                    // Compute the velocity by the deltaY
                    final int velocityX = -(int) (deltaX / 0.0166666f);
                    onAbsorb(velocityX);
                }
            }
        }
        return overScroll;
    }

    /**
     * In RecyclerView, overScrollBy does not work. Call absorbGlows instead of
     * this method.
     */
    public void recyclerViewAbsorbGlows(int velocityX, int velocityY) {
        if (mEnableFlingOverScroll) {
            // The direction mOffsetX is same as TouchEvent,
            // and the direction of "velocityY" is same as scroll,
            // so we need to reverse it.
            if (velocityX != 0) {
                onAbsorb(-velocityX);
            }
        }
    }

    private void onAbsorb(final int velocityX) {
        // offset the start of fling 1px
        mOffsetX = velocityX > 0 ? -1 : 1;
        final float overX = velocityX * 0.07f;
        mScroller.start(overX, FLING_BACK_DURATION, sFlingBackPathPointsHolder);
        setState(OS_FLING);
        mView.invalidate();
    }

    public void draw(Canvas canvas) {
        if (mState == OS_NONE) {
            mIOverScrollable.superDraw(canvas);
        } else {
            if (mState == OS_SPRING_BACK || mState == OS_FLING) {
                if (mScroller.computeScrollOffsetX()) {
                    mOffsetX = mScroller.getCurrX();
                } else {
                    mOffsetX = 0;
                    setState(OS_NONE);
                }
                ViewCompat.postInvalidateOnAnimation(mView);
            }
            final int sc = canvas.save();
            mStyle.transformOverScrollCanvasX(mOffsetX, canvas, mView);
            mIOverScrollable.superDraw(canvas);
            canvas.restoreToCount(sc);

            final int sc1 = canvas.save();
            if (mOffsetX > 0) {// top
                mStyle.drawOverScrollLeft(mOffsetX, canvas, mView);
            } else if (mOffsetX < 0) {// bottom
                mStyle.drawOverScrollRight(mOffsetX, canvas, mView);
            }
            canvas.restoreToCount(sc1);
        }
    }

    private void setState(int newState) {
        if (mState != newState) {
            mState = newState;
        }
    }

    private boolean isOsTop() {
        return mState == OS_DRAG_TOP;
    }

    // ===========================================================
    // Internal
    // ===========================================================

    private boolean isOsBottom() {
        return mState == OS_DRAG_BOTTOM;
    }

    private boolean isOsDrag() {
        return mState == OS_DRAG_TOP || mState == OS_DRAG_BOTTOM;
    }

    private boolean onInterceptTouchEventInternal(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = event.getX();
                // mLastMotionX = ev.getX();
                mActivePointerId = event.getPointerId(0);
                // If OS_FLING, we do not Intercept and allow the scroller to finish
                if (mState == OS_SPRING_BACK) {
                    if (mScroller.computeScrollOffsetX()) {
                        mOffsetX = mScroller.getCurrX();
                        mScroller.abortAnimation();
                        if (mOffsetX == 0) {
                            setState(OS_NONE);
                        } else {
                            setState(mOffsetX > 0 ? OS_DRAG_TOP : OS_DRAG_BOTTOM);
                        }
                        mView.invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // log("onInterceptTouchEvent -> ACTION_MOVE " + ev.toString());
                if (mActivePointerId == INVALID_POINTER) {
                    break;
                }
                final int pointerIndex = MotionEventCompat.findPointerIndex(event,
                        mActivePointerId);
                if (pointerIndex == -1) {
                    break;
                }
                final float x = MotionEventCompat.getX(event, pointerIndex);
                // final float x = ev.getX(pointerIndex);
                final float xDiff = x - mLastMotionX;
                // final float xDiff = x - mLastMotionX;
                mLastMotionX = x;
                if (!isOsDrag()) {
                    boolean canScrollUp, canScrollDown;
                    final int offset = mIOverScrollable.superComputeHorizontalScrollOffset();
                    final int range = mIOverScrollable.superComputeHorizontalScrollRange() -
                                      mIOverScrollable.superComputeHorizontalScrollExtent();
                    if (range == 0) {
                        canScrollDown = canScrollUp = false;
                    } else {
                        canScrollUp = offset > 0;
                        canScrollDown = offset < (range - 1);
                    }
                    if (canScrollUp && canScrollDown) {
                        break;
                    }
                    // mLastMotionX = x;
                    if ((Math.abs(xDiff) > mTouchSlop)) {
                        boolean isOs = false;
                        if (!canScrollUp && xDiff > 0) {
                            setState(OS_DRAG_TOP);
                            isOs = true;
                        } else if (!canScrollDown && xDiff < 0) {
                            setState(OS_DRAG_BOTTOM);
                            isOs = true;
                        }
                        if (isOs) {
                            // Cancel the "real touch of View"
                            MotionEvent fakeCancelEvent = MotionEvent.obtain(event);
                            fakeCancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                            mIOverScrollable.superOnTouchEvent(fakeCancelEvent);
                            fakeCancelEvent.recycle();
                            mIOverScrollable.superAwakenScrollBars();

                            final ViewParent parent = mView.getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                }

                break;

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(event);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return isOsDrag();
    }

    private boolean onTouchEventInternal(MotionEvent event) {
        // log("onTouchEvent->" + ev.toString());
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = event.getX();
                // mLastMotionX = ev.getX();
                mActivePointerId = event.getPointerId(0);
                // ACTION_DOWN is hanled in InterceptTouchEvent
                break;
            case MotionEvent.ACTION_MOVE: {
                // log("onTouchEvent -> ACTION_MOVE "+ ev.toString());
                if (mActivePointerId == INVALID_POINTER) {
                    break;
                }
                final int pointerIndex = MotionEventCompat.findPointerIndex(event,
                        mActivePointerId);
                if (pointerIndex < 0) {
                    break;
                }
                final float y = MotionEventCompat.getX(event, pointerIndex);
                // final float x = ev.getX(pointerIndex);
                final float yDiff = y - mLastMotionX;
                // final float xDiff = x - mLastMotionX;
                mLastMotionX = y;
                if (!isOsDrag()) {
                    boolean canScrollUp, canScrollDown;
                    final int offset = mIOverScrollable.superComputeHorizontalScrollOffset();
                    final int range = mIOverScrollable.superComputeHorizontalScrollRange() -
                                      mIOverScrollable.superComputeHorizontalScrollExtent();
                    if (range == 0) {
                        canScrollDown = canScrollUp = false;
                    } else {
                        canScrollUp = offset > 0;
                        canScrollDown = offset < (range - 1);
                    }
                    if (canScrollUp && canScrollDown) {
                        break;
                    }
                    // mLastMotionX = x;
                    // In TouchEvent, if can not UP or Down and yDiff > 1px,
                    // we start drag overscroll
                    if ((Math.abs(yDiff) >= 1f)) {// mTouchSlop
                        boolean isOs = false;
                        if (!canScrollUp && yDiff > 0) {
                            setState(OS_DRAG_TOP);
                            isOs = true;
                        } else if (!canScrollDown && yDiff < 0) {
                            setState(OS_DRAG_BOTTOM);
                            isOs = true;
                        }
                        if (isOs) {
                            // Cancel the "real touch of View"
                            MotionEvent fakeCancelEvent = MotionEvent.obtain(event);
                            fakeCancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                            mIOverScrollable.superOnTouchEvent(fakeCancelEvent);
                            fakeCancelEvent.recycle();
                            mIOverScrollable.superAwakenScrollBars();

                            final ViewParent parent = mView.getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                }
                if (isOsDrag()) {
                    // mLastMotionX = MotionEventCompat.getX(ev, pointerIndex);
                    mOffsetX += yDiff;
                    if (isOsTop()) {// mDragOffsetY should > 0
                        if (mOffsetX <= 0) {
                            setState(OS_NONE);
                            mOffsetX = 0;
                            // return to "touch real view"
                            MotionEvent fakeDownEvent = MotionEvent.obtain(event);
                            fakeDownEvent.setAction(MotionEvent.ACTION_DOWN);
                            mIOverScrollable.superOnTouchEvent(fakeDownEvent);
                            fakeDownEvent.recycle();
                        }
                    } else if (isOsBottom()) {// mDragOffsetY should < 0
                        if (mOffsetX >= 0) {
                            setState(OS_NONE);
                            mOffsetX = 0;
                            // return to "touch real view"
                            MotionEvent fakeDownEvent = MotionEvent.obtain(event);
                            fakeDownEvent.setAction(MotionEvent.ACTION_DOWN);
                            mIOverScrollable.superOnTouchEvent(fakeDownEvent);
                            fakeDownEvent.recycle();
                        }
                    }
//                    if (iOnOverScrollLitener != null) {
//                        int round = Math.round(mOffsetX * DEFAULT_DRAW_TRANSLATE_RATE);
//                        iOnOverScrollLitener.overScrollUp(true, round);
//                    }
                    mView.invalidate();
                }
                mView.removeCallbacks(this);
                mView.postDelayed(this,2000);
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(event);
                mLastMotionX = MotionEventCompat.getX(event, index);
                // mLastMotionX = MotionEventCompat.getX(ev, index);
                mActivePointerId = MotionEventCompat.getPointerId(event, index);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP: {
                onSecondaryPointerUp(event);
                final int index = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                if (index != -1) {
                    mLastMotionX = MotionEventCompat.getX(event, index);
                    // mLastMotionX = MotionEventCompat.getX(ev, index);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                mView.removeCallbacks(this);
                if (mOffsetX != 0f) {
                    // Sping back to 0
                    final int startScrollY = Math.round(mOffsetX);
                    // mScroller.startScroll(0, startScrollY, 0, -startScrollY,
                    // SPRING_BACK_DURATION);
                    // mPath.reset();
                    // mPath.moveTo(0f, startScrollY);
                    // mPath.lineTo(1f, 0);
                    // mScroller.start(1f, SPRING_BACK_DURATION, mPath);
                    mScroller.start(startScrollY, DRAG_BACK_DURATION, sDragBackPathPointsHolder);
                    setState(OS_SPRING_BACK);
                    mView.invalidate();
                    if (iOnOverScrollLitener != null) {
                        int round = Math.round(mOffsetX * DEFAULT_DRAW_TRANSLATE_RATE);
                        iOnOverScrollLitener.overScrollUp(true, round);
                    }
                }
                mActivePointerId = INVALID_POINTER;
            }
        }
        return isOsDrag();
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    public void setOnOverScrollLitener(IOnOverScrollLitener iOnOverScrollLitener) {
        this.iOnOverScrollLitener = iOnOverScrollLitener;
    }

    @Override
    public void run() {
        if (mOffsetX != 0f) {
            final int startScrollY = Math.round(mOffsetX);
            mScroller.start(startScrollY, DRAG_BACK_DURATION, sDragBackPathPointsHolder);
            setState(OS_SPRING_BACK);
            mView.invalidate();
            if (iOnOverScrollLitener != null) {
                int round = Math.round(mOffsetX * DEFAULT_DRAW_TRANSLATE_RATE);
                iOnOverScrollLitener.overScrollUp(true, round);
            }
        }
        mActivePointerId = INVALID_POINTER;
    }
}
