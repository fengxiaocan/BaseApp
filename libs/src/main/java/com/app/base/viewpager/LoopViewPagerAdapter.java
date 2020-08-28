package com.app.base.viewpager;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 无限循环的ViewPager
 *
 * @param <T>
 * @param <V>
 */
public abstract class LoopViewPagerAdapter<T,V extends ViewPagerViewHolder> extends PagerAdapter {
    protected List<T> mDatas;
    protected boolean isLoop;
    protected int loopAllCount = Integer.MAX_VALUE;
    protected ViewPagerViewHolder mCurrentPrimaryItem = null;

    public LoopViewPagerAdapter(){
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
        isLoop = mDatas != null && mDatas.size() > 1;
        if(mDatas != null && mDatas.size() > 0){
            loopAllCount = Integer.MAX_VALUE / mDatas.size() * mDatas.size();
        } else{
            loopAllCount = Integer.MAX_VALUE;
        }
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object){
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
            if(isLoop){
                return loopAllCount;
            }
            return mDatas.size();
        }
        return 0;
    }

    public int getRealCount(){
        if(mDatas != null){
            return mDatas.size();
        }
        return 0;
    }

    public T getItemData(int position){
        return mDatas.get(position % mDatas.size());
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        T data = getItemData(position);
        V holder = createViewHolder(container,data,position);
        container.addView(holder.getRootView());

        holder.findViewById(holder.mRootView);
        holder.initData(data,position);

        return holder;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        ((ViewPagerViewHolder)object).destroyItem(container,position);
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
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return view == ((ViewPagerViewHolder)object).getRootView();
    }

    public abstract V createViewHolder(ViewGroup container, T data, int position);

}
