package com.app.base.viewpager;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class FragmentStateViewPagerAdapter<T> extends PagerAdapter {
    protected final FragmentManager mFragmentManager;
    protected int mChildCount = 0;
    protected List<T> mData;
    protected OnFragmentVisibleChangeListener mChangeListener;
    protected FragmentTransaction mCurTransaction = null;
    protected SparseArray<Fragment.SavedState> mSavedState = new SparseArray<Fragment.SavedState>();
    protected SparseArray<Fragment> mFragments = new SparseArray<Fragment>();
    protected Fragment mCurrentPrimaryItem = null;

    public FragmentStateViewPagerAdapter(FragmentManager fm){
        mFragmentManager = fm;
    }

    public abstract Fragment getFragment(int position, T data);

    public void setChangeListener(OnFragmentVisibleChangeListener changeListener){
        mChangeListener = changeListener;
    }

    public List<T> getDatas(){
        return mData;
    }

    public void setDatas(List<T> data){
        mData = data;
    }

    public void clear(){
        mData = null;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> data){
        if(data == null){
            return;
        }
        if(mData != null){
            mData.addAll(data);
        } else{
            mData = data;
        }
    }

    public T getData(int position){
        if(position < 0 || position >= mData.size()){
            return null;
        }
        return mData.get(position);
    }

    public void insertDatas(List<T> data, int position){
        if(data == null){
            return;
        }
        if(mData != null){
            if(position >= 0 && position < mData.size()){
                mData.addAll(position,data);
            }
        } else{
            mData = data;
        }
    }

    public void addData(T data){
        if(data == null){
            return;
        }
        if(mData != null){
            mData.add(data);
        } else{
            mData = new ArrayList<>();
            mData.add(data);
        }
    }

    public void insertData(T data,int position){
        if(data == null){
            return;
        }
        if(mData != null){
            if(position >= 0 && position < mData.size()){
                mData.add(data);
            }
        } else{
            mData = new ArrayList<>();
            mData.add(data);
        }
    }

    public void addDataAndNotify(T data){
        addData(data);
        notifyDataSetChanged();
    }

    public void addDatasAndNotify(List<T> data){
        addDatas(data);
        notifyDataSetChanged();
    }

    public void setDatasAndNotify(List<T> data){
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return mData != null ? mData.size() : 0;
    }

    @Override
    public synchronized void notifyDataSetChanged(){
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public synchronized int getItemPosition(Object object){
        if(mChildCount > 0){
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public Fragment getCurrentFragment(){
        return mCurrentPrimaryItem;
    }


    @Override
    public void startUpdate(ViewGroup container){
//        if(container.getId() == View.NO_ID){
//            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
//        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        if(mFragments.size() > position){
            Fragment f = mFragments.get(position);
            if(f != null){
                return f;
            }
        }
        if(mCurTransaction == null){
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        Fragment fragment = getFragment(position,getData(position));
        Fragment.SavedState fss = mSavedState.get(position);
        if(fss != null){
            fragment.setInitialSavedState(fss);
        }
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        mFragments.put(position,fragment);
        mCurTransaction.add(container.getId(),fragment);

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        Fragment fragment = (Fragment)object;

        if(fragment instanceof OnFragmentDetachListener){
            ((OnFragmentDetachListener)fragment).onPagerDetach();
        }

        if(mCurTransaction == null){
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        mSavedState.put(position,fragment.isAdded() ? mFragmentManager.saveFragmentInstanceState(fragment) : null);
        mFragments.remove(position);

        mCurTransaction.remove(fragment);
    }

    @Override
    @SuppressWarnings("ReferenceEquality")
    public void setPrimaryItem(ViewGroup container, int position, Object object){
        Fragment fragment = (Fragment)object;
        if(fragment != mCurrentPrimaryItem){
            if(mCurrentPrimaryItem != null){
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if(fragment != null){
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }

            //如果之前的fragment实现过OnFragmentSelectListener接口的,则调用取消当前选择onPageUnSelect的方法
            if(mCurrentPrimaryItem instanceof OnFragmentSelectListener){
                ((OnFragmentSelectListener)mCurrentPrimaryItem).onPageUnSelect();
            }

            mCurrentPrimaryItem = fragment;

            //如果现在的fragment实现过OnFragmentSelectListener接口的,则调用当前选择onPageSelect的方法
            if(mCurrentPrimaryItem instanceof OnFragmentSelectListener){
                ((OnFragmentSelectListener)mCurrentPrimaryItem).onPageSelect();
            }

            if(mChangeListener != null){
                mChangeListener.onVisible(fragment);
            }
        }
    }

    @Override
    public void finishUpdate(ViewGroup container){
        if(mCurTransaction != null){
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return ((Fragment)object).getView() == view;
    }

    @Override
    public Parcelable saveState(){
        Bundle state = null;
        if(mSavedState.size() > 0){
            state = new Bundle();
            state.putSparseParcelableArray("states",mSavedState.clone());
        }
        for(int i = 0;i < mFragments.size();i++){
            Fragment f = mFragments.get(i);
            if(f != null && f.isAdded()){
                if(state == null){
                    state = new Bundle();
                }
                String key = "f" + i;
                mFragmentManager.putFragment(state,key,f);
            }
        }
        return state;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader){
        if(state != null){
            Bundle bundle = (Bundle)state;
            bundle.setClassLoader(loader);
            mSavedState.clear();
            mFragments.clear();
            if(bundle.containsKey("states")){
                mSavedState = bundle.getSparseParcelableArray("states");
            }
            Iterable<String> keys = bundle.keySet();
            for(String key: keys){
                if(key.startsWith("f")){
                    int index = Integer.parseInt(key.substring(1));
                    Fragment f = mFragmentManager.getFragment(bundle,key);
                    if(f != null){
                        f.setMenuVisibility(false);
                        mFragments.put(index,f);
                    }
                }
            }
        }
    }
}
