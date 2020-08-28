package com.app.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.app.base.holder.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.lib.impl
 * @创建者: Noah.冯
 * @时间: 12:05
 * @描述： TODO
 */
public abstract class ListBaseAdapter<T,V extends ListViewHolder> extends android.widget.BaseAdapter{

    protected List<T> mData;

    public ListBaseAdapter(){
    }

    public ListBaseAdapter(List<T> data){
        mData = data;
    }

    @Deprecated
    public ListBaseAdapter(Context context, List<T> data){
        mData = data;
    }

    public List<T> getData(){
        return mData;
    }

    public void setData(List<T> data){
        mData = data;
        notifyDataSetChanged();
    }

    public void removeT(int index){
        if(mData != null){
            mData.remove(index);
            notifyDataSetChanged();
        }
    }

    public void removeT(T t){
        if(mData != null){
            mData.remove(t);
            notifyDataSetChanged();
        }
    }

    public void removeAll(){
        mData = null;
        notifyDataSetChanged();
    }

    public void update(List<T> data){
        mData = data;
        notifyDataSetChanged();
    }

    public void updateT(T t,int position){
        if(mData == null){
            mData = new ArrayList<>();
        }
        mData.set(position,t);
        notifyDataSetChanged();
    }

    public void addData(List<T> data){
        if(mData == null){
            mData = new ArrayList<>();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addT(T t){
        if(mData == null){
            mData = new ArrayList<>();
        }
        mData.add(t);
        notifyDataSetChanged();
    }

    public T getT(int position){
        if(mData != null && mData.size() > position){
            return mData.get(position);
        } else{
            return null;
        }
    }

    @Override
    public int getCount(){
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        V holder;
        if(convertView == null){
            holder = onCreateViewHolder(parent,position);
            convertView = holder.getRootView();
            convertView.setTag(holder);
            //对于listview，注意添加这一行，即可在item上使用高度
            //            AutoUtils.auto(convertView);
        } else{
            holder = (V)convertView.getTag();
        }
        onBindData(holder,position);
        return convertView;
    }

    /**
     * 创建ViewHolder
     */
    public abstract V onCreateViewHolder(ViewGroup viewGroup, int position);

    /**
     * 绑定数据
     */
    public abstract void onBindData(V holder,int position);
}
