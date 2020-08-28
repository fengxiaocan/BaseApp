package com.app.base.scrollview.style;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.app.base.scrollview.OverScrollStyle;
import com.app.tool.Tools;


public class MoreOverScrollStyle extends OverScrollStyle {

    private int circleRadiu;
    private int textSize;
    private int textWidth;
    private Paint textPaint = new Paint();
    private Paint paint = new Paint();

    public MoreOverScrollStyle(Context context) {
        textSize = Tools.Size.sp2px(context, 15);
        circleRadiu = Tools.Size.dp2px(28f);
        textPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textWidth = Tools.Strings.getTextWidth(textPaint, "更多");
        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        paint.setColor(Color.parseColor("#00488ACE"));
    }

    @Override
    public void drawOverScrollRight(float offsetx, Canvas canvas, View view) {
        int height = view.getMeasuredHeight();
        int round = Math.round(offsetx * DEFAULT_DRAW_TRANSLATE_RATE);
        int circleX = Tools.Screen.getScreenWidth() + round + circleRadiu;
        int circleY = height / 2;
        int abs = Math.abs(round);
        if (abs <= circleRadiu) {
            paint.setColor(Color.TRANSPARENT);
        } else if (abs <= circleRadiu * 2) {
            float v = (abs - circleRadiu) / (circleRadiu * 2F);
            int alpha = (int) (0x99 * v);
            paint.setColor(Color.argb(alpha,0x48,0x8A,0xCE));
            canvas.drawCircle(circleX, circleY, abs - circleRadiu, paint);// 大圆
        } else {
            paint.setColor(Color.argb(0x99,0x48,0x8A,0xCE));
            canvas.drawCircle(circleX, circleY, circleRadiu, paint);// 大圆
        }

        canvas.drawText("更多", circleX - textWidth / 2, circleY + textSize / 2, textPaint);// 画文本
    }
}
