package com.app.base.scrollview.style;

import android.graphics.Canvas;
import android.view.View;

import com.app.base.scrollview.OverScrollStyle;


public class Miui8OverScrollStyle extends OverScrollStyle {

    final float scaleRate = 0.2f;

    @Override
    public void transformOverScrollCanvasY(float offsetY, Canvas canvas, View view) {
        final int viewHeight = view.getHeight();
        final int viewWidth = view.getWidth();
        //scaleY ,depends on viewWidth.
        final float scaleY = (Math.abs(offsetY * scaleRate) + viewWidth) / viewWidth;
        canvas.scale(1, scaleY, viewWidth / 2f,
                offsetY >= 0 ? 0 : (viewHeight + view.getScrollY()));
    }

}
