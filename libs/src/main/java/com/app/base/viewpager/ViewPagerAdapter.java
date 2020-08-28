package com.app.base.viewpager;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewPagerAdapter<T,V extends ViewPagerViewHolder> extends PagerAdapter {
    protected int mChildCount = 0;
    protected List<T> mDatas;
    protected ViewPagerViewHolder mCurrentPrimaryItem = null;
    protected OnViewPagerItemClickListener<T> onItemClickListener;

    public ViewPagerAdapter(){
    }

    public ViewPagerAdapter(OnViewPagerItemClickListener<T> listener){
        this.onItemClickListener = listener;
    }

    public void setOnItemClickListener(OnViewPagerItemClickListener<T> onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public List<T> getDatas(){
        return mDatas;
    }

    public void setDatas(T... datas){
        if(datas != null){
            if(mDatas == null){
                mDatas = new ArrayList<>();
            }
            mDatas.clear();
            for(T data: datas){
                mDatas.add(data);
            }
        }
    }

    public void setDatas(List<T> data){
        mDatas = data;
    }

    @Override
    public void notifyDataSetChanged(){
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object){
        if(mChildCount > 0){
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public void addDatas(T... datas){
        if(datas != null){
            if(mDatas == null){
                mDatas = new ArrayList<>();
            }
            for(T data: datas){
                mDatas.add(data);
            }
        }
    }

    public void setDataAndNotify(List<T> data){
        setDatas(data);
        notifyDataSetChanged();
    }

    public void addDatas(List<T> data){
        if(mDatas == null){
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(data);
    }

    public void addDatasAndNotify(List<T> data){
        addDatas(data);
        notifyDataSetChanged();
    }

    public void addData(T t){
        if(mDatas == null){
            mDatas = new ArrayList<>();
        }
        mDatas.add(t);
    }

    public void addDataAndNotify(T t){
        addData(t);
        notifyDataSetChanged();
    }

    public void insertData(T t,int position){
        if(mDatas == null){
            mDatas = new ArrayList<>();
            mDatas.add(t);
        } else{
            if(position < 0){
                mDatas.add(0,t);
            } else if(mDatas.size() > position){
                mDatas.add(t);
            } else{
                mDatas.add(position,t);
            }
        }
    }

    public void insertDataAndNotify(T t,int position){
        insertData(t,position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        if(mDatas != null){
            return mDatas.size();
        }
        return 0;
    }

    public T getItemData(int position){
        return mDatas.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        T data = getItemData(position);
        V holder = createViewHolder(container,data,position);
        container.addView(holder.getRootView());
        if(onItemClickListener != null){
            holder.mRootView.setOnClickListener(v -> onItemClickListener.onItemClick(data,position));
        }
        holder.findViewById(holder.mRootView);
        holder.initData(data,position);
        return holder;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        ViewPagerViewHolder viewHolder = (ViewPagerViewHolder)object;
        if(viewHolder != mCurrentPrimaryItem){
            if(mCurrentPrimaryItem != null){
                mCurrentPrimaryItem.onPagerVisibleHint(false);
            }
            if(viewHolder != null){
                viewHolder.onPagerVisibleHint(true);
            }
            mCurrentPrimaryItem = viewHolder;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        ((ViewPagerViewHolder)object).destroyItem(container,position);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return view == ((ViewPagerViewHolder)object).getRootView();
    }

    public abstract V createViewHolder(ViewGroup container, T data, int position);

    public ViewPagerViewHolder getCurrentPrimaryItem(){
        return mCurrentPrimaryItem;
    }
}
