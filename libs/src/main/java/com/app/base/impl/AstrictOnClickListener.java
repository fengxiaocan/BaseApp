package com.app.base.impl;

import android.view.View;

public abstract class AstrictOnClickListener implements View.OnClickListener{
    protected int intervalTime = 2000;
    protected boolean canClick;
    private Runnable mRunnable = new Runnable(){
        @Override
        public void run(){
            canClick = true;
        }
    };

    public AstrictOnClickListener(){
        canClick = true;
    }

    public boolean isCanClick(){
        return canClick;
    }

    public void setCanClick(boolean canClick){
        this.canClick = canClick;
    }

    public int getIntervalTime(){
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime){
        this.intervalTime = intervalTime;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v){
        if(canClick){
            canClick = false;
            postCanClick(v);
            onCanClick(v);
        }
    }

    public abstract void onCanClick(View v);

    public void postCanClick(View v){
        v.postDelayed(mRunnable,intervalTime);
    }
}
