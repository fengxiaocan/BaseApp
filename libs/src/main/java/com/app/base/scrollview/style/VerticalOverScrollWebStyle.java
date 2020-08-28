package com.app.base.scrollview.style;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.app.base.scrollview.OverScrollStyle;


public class VerticalOverScrollWebStyle extends OverScrollStyle {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Rect bounds = new Rect();

    public VerticalOverScrollWebStyle() {
        paint.setColor(Color.rgb(0xF3, 0xF3, 0xF3));
    }

    @Override
    public void drawOverScrollTop(float offsetY, Canvas canvas, View view) {
//        bounds.left = 0;
//        bounds.right = view.getWidth();
//        bounds.top = 0;
//        bounds.bottom = Math.round(offsetY * DEFAULT_DRAW_TRANSLATE_RATE);
//        canvas.drawRect(bounds, paint);
    }

    @Override
    public void drawOverScrollBottom(float offsetY, Canvas canvas, View view) {
    }
}
