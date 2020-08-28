package com.app.base.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.lib.impl
 * @创建者: Noah.冯
 * @时间: 17:07
 * @描述： TODO
 */
public abstract class ListViewHolder{
    protected View rootView;

    public ListViewHolder(View rootView){
        this.rootView = rootView;
    }

    public ListViewHolder(Context context, int layoutID){
        this.rootView = View.inflate(context,layoutID,null);
    }

    public ListViewHolder(Context context, int layoutID, ViewGroup viewGroup){
        this.rootView = View.inflate(context,layoutID,viewGroup);
    }

    public View getRootView(){
        return rootView;
    }

    public Context getContext(){
        return rootView.getContext();
    }

    public View findViewById(int id){
        return rootView.findViewById(id);
    }
}
