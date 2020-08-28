package com.app.base.scrollview;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.View;


public class OverScrollStyle {
    public static final float DEFAULT_DRAW_TRANSLATE_RATE = 0.36f;
    public static final float SYSTEM_DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static final int dp2px(int dp) {
        return (int) (dp * SYSTEM_DENSITY + 0.5f);
    }

    /**
     * Transform canvas before draw content, for example, do
     * canvas.translate
     */
    public void transformOverScrollCanvasY(float offsetY, Canvas canvas, View view) {
        final int translateY = Math.round(offsetY * DEFAULT_DRAW_TRANSLATE_RATE);
        canvas.translate(0, translateY);
    }

    public void transformOverScrollCanvasX(float offsetX, Canvas canvas, View view) {
        final int translateX = Math.round(offsetX * DEFAULT_DRAW_TRANSLATE_RATE);
        canvas.translate(translateX,0);
    }


    /**
     * Draw overscroll effect(e.g. logo) at top, the direction of offsetY is
     * same as TouchEvent
     */
    public void drawOverScrollTop(float offsetY, Canvas canvas, View view) {
    }


    public void drawOverScrollLeft(float offsetx, Canvas canvas, View view) {
    }


    /**
     * Draw overscroll effect(e.g. logo) at bottom, the direction of offsetY
     * is same as TouchEvent
     */
    public void drawOverScrollBottom(float offsetY, Canvas canvas, View view) {
    }

    public void drawOverScrollRight(float offsetx, Canvas canvas, View view) {
    }

    /**
     * To get the "real bottom" when overscroll bottom, that is height +
     * getScrollY
     **/
    public final int getOverScrollViewCanvasBottom(View view) {
        return view.getMeasuredHeight() + view.getScrollY();
    }
}
