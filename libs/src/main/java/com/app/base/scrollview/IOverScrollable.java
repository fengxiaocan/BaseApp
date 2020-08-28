package com.app.base.scrollview;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public interface IOverScrollable {
    int superComputeVerticalScrollExtent();

    int superComputeVerticalScrollOffset();

    int superComputeVerticalScrollRange();

    int superComputeHorizontalScrollExtent();

    int superComputeHorizontalScrollOffset();

    int superComputeHorizontalScrollRange();

    void superOnTouchEvent(MotionEvent event);

    void superDraw(Canvas canvas);

    boolean superAwakenScrollBars();

    boolean superOverScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
            int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);

    View getOverScrollableView();

    VerticalOverScrollDelegate getVerticalOverScrollDelegate();

    HorizontalOverScrollDelegate getHorizontalOverScrollDelegate();
}
