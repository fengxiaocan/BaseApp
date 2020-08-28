package com.app.base.scrollview.style;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.app.base.scrollview.OverScrollStyle;


public class VerticalOverScrollStyle extends OverScrollStyle {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Rect bounds = new Rect();

    public VerticalOverScrollStyle() {
        paint.setColor(Color.rgb(0xF3, 0xF3, 0xF3));
    }

    @Override
    public void drawOverScrollTop(float offsetY, Canvas canvas, View view) {
        bounds.left = 0;
        bounds.right = view.getWidth();
        bounds.top = 0;
        bounds.bottom = Math.round(offsetY * DEFAULT_DRAW_TRANSLATE_RATE);
        canvas.drawRect(bounds, paint);
    }

    @Override
    public void drawOverScrollBottom(float offsetY, Canvas canvas, View view) {
        final int viewWidth = view.getMeasuredWidth();
        bounds.left = 0;
        bounds.right = bounds.left + viewWidth;
        bounds.top = view.getMeasuredHeight();
        bounds.bottom = bounds.top + Math.round(offsetY * DEFAULT_DRAW_TRANSLATE_RATE);
        canvas.drawRect(bounds, paint);
    }
}
