package com.app.base.drawable.sprite;

import android.view.animation.Interpolator;

import androidx.core.view.animation.PathInterpolatorCompat;

/**
 * Created by ybq.
 */
public class Ease{
    public static Interpolator inOut(){
        return PathInterpolatorCompat.create(0.42f,0f,0.58f,1f);
    }
}
