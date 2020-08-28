package com.app.base.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/**
 * 通过一行代码即可实现阴影效果
 * ShadowDrawable.setShadowDrawable(textView1, Color.parseColor("#3D5AFE"), dpToPx(8),
 * Color.parseColor("#66000000"), dpToPx(10), 0, 0);
 */
public class ShadowDrawable extends Drawable {

    public final static int SHAPE_ROUND = 1;
    public final static int SHAPE_CIRCLE = 2;
    private Paint mPaint;
    private int mShadowRadius;
    private int mShape;
    private int mShapeRadius;
    private int mOffsetX;
    private int mOffsetY;
    private int[] mBgColor;
    private RectF mRect;

    private ShadowDrawable(int shape,int[] bgColor,int shapeRadius,int shadowColor,int shadowRadius,int offsetX,
            int offsetY)
    {
        this.mShape = shape;
        this.mBgColor = bgColor;
        this.mShapeRadius = shapeRadius;
        this.mShadowRadius = shadowRadius;
        this.mOffsetX = offsetX;
        this.mOffsetY = offsetY;
        mPaint = new Paint();
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setAntiAlias(true);
        mPaint.setShadowLayer(shadowRadius,offsetX,offsetY,shadowColor);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        mRect = new RectF(mShadowRadius - mOffsetX,mShadowRadius - mOffsetY,mShadowRadius - mOffsetX,
                mShadowRadius - mOffsetY);
    }

    public static void setShadowDrawable(View view, Drawable drawable){
        view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        ViewCompat.setBackground(view,drawable);
    }

    public static void setShadowDrawable(View view, int shapeRadius, int shadowColor, int shadowRadius, int offsetX,
                                         int offsetY)
    {
        ShadowDrawable drawable = new Builder().setShapeRadius(shapeRadius)
                                                              .setShadowColor(shadowColor)
                                                              .setShadowRadius(shadowRadius)
                                                              .setOffsetX(offsetX)
                                                              .setOffsetY(offsetY)
                                                              .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        ViewCompat.setBackground(view,drawable);
    }

    public static void setShadowDrawable(View view, int bgColor, int shapeRadius, int shadowColor, int shadowRadius,
                                         int offsetX, int offsetY)
    {
        ShadowDrawable drawable = new Builder().setBgColor(bgColor)
                                                              .setShapeRadius(shapeRadius)
                                                              .setShadowColor(shadowColor)
                                                              .setShadowRadius(shadowRadius)
                                                              .setOffsetX(offsetX)
                                                              .setOffsetY(offsetY)
                                                              .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        ViewCompat.setBackground(view,drawable);
    }

    public static void setShadowDrawable(View view, int shape, int bgColor, int shapeRadius, int shadowColor,
                                         int shadowRadius, int offsetX, int offsetY)
    {
        ShadowDrawable drawable = new Builder().setShape(shape).setBgColor(bgColor).setShapeRadius(
                shapeRadius).setShadowColor(shadowColor).setShadowRadius(shadowRadius).setOffsetX(offsetX).setOffsetY(
                offsetY).builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        ViewCompat.setBackground(view,drawable);
    }

    public static void setShadowDrawable(View view, int[] bgColor, int shapeRadius, int shadowColor, int shadowRadius,
                                         int offsetX, int offsetY)
    {
        ShadowDrawable drawable = new Builder().setBgColor(bgColor)
                                                              .setShapeRadius(shapeRadius)
                                                              .setShadowColor(shadowColor)
                                                              .setShadowRadius(shadowRadius)
                                                              .setOffsetX(offsetX)
                                                              .setOffsetY(offsetY)
                                                              .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        ViewCompat.setBackground(view,drawable);
    }

    @Override
    public void setBounds(int left,int top,int right,int bottom){
        super.setBounds(left,top,right,bottom);
        mRect = new RectF(left + mShadowRadius - mOffsetX,top + mShadowRadius - mOffsetY,
                right - mShadowRadius - mOffsetX,bottom - mShadowRadius - mOffsetY);
    }

    @Override
    public void draw(@NonNull Canvas canvas){
        Paint newPaint = new Paint();
        if(mBgColor != null){
            if(mBgColor.length == 1){
                newPaint.setColor(mBgColor[0]);
            } else{
                newPaint.setShader(
                        new LinearGradient(mRect.left,mRect.height() / 2,mRect.right,mRect.height() / 2,mBgColor,null,
                                Shader.TileMode.CLAMP));
            }
        }
        newPaint.setAntiAlias(true);
        if(mShape == SHAPE_ROUND){
            canvas.drawRoundRect(mRect,mShapeRadius,mShapeRadius,mPaint);
            canvas.drawRoundRect(mRect,mShapeRadius,mShapeRadius,newPaint);
        } else{
            canvas.drawCircle(mRect.centerX(),mRect.centerY(), Math.min(mRect.width(),mRect.height()) / 2,mPaint);
            canvas.drawCircle(mRect.centerX(),mRect.centerY(), Math.min(mRect.width(),mRect.height()) / 2,newPaint);

        }
    }

    @Override
    public void setAlpha(int alpha){
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter){
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity(){
        return PixelFormat.TRANSLUCENT;
    }

    public static class Builder{
        private int mShape;
        private int mShapeRadius;
        private int mShadowColor;
        private int mShadowRadius;
        private int mOffsetX = 0;
        private int mOffsetY = 0;
        private int[] mBgColor;

        public Builder(){
            mShape = ShadowDrawable.SHAPE_ROUND;
            mShapeRadius = 12;
            mShadowColor = Color.parseColor("#4d000000");
            mShadowRadius = 18;
            mOffsetX = 0;
            mOffsetY = 0;
            mBgColor = new int[1];
            mBgColor[0] = Color.TRANSPARENT;
        }

        public Builder setShape(int mShape){
            this.mShape = mShape;
            return this;
        }

        public Builder setShapeRadius(int ShapeRadius){
            this.mShapeRadius = ShapeRadius;
            return this;
        }

        public Builder setShadowColor(int shadowColor){
            this.mShadowColor = shadowColor;
            return this;
        }

        public Builder setShadowRadius(int shadowRadius){
            this.mShadowRadius = shadowRadius;
            return this;
        }

        public Builder setOffsetX(int OffsetX){
            this.mOffsetX = OffsetX;
            return this;
        }

        public Builder setOffsetY(int OffsetY){
            this.mOffsetY = OffsetY;
            return this;
        }

        public Builder setBgColor(int BgColor){
            this.mBgColor[0] = BgColor;
            return this;
        }

        public Builder setBgColor(int[] BgColor){
            this.mBgColor = BgColor;
            return this;
        }

        public ShadowDrawable builder(){
            return new ShadowDrawable(mShape,mBgColor,mShapeRadius,mShadowColor,mShadowRadius,mOffsetX,mOffsetY);
        }
    }
}
