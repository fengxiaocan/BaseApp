package com.app.base.viewpager;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class FragmentViewPagerAdapter<T> extends PagerAdapter {
    protected final FragmentManager mFragmentManager;
    protected int mChildCount = 0;//计数器
    protected List<T> mData;
    protected OnFragmentVisibleChangeListener mChangeListener;
    protected FragmentTransaction mCurTransaction = null;
    protected Fragment mCurrentPrimaryItem = null;

    public FragmentViewPagerAdapter(FragmentManager fm){
        mFragmentManager = fm;
    }

    private static String makeFragmentName(int viewId, long id){
        return "android:switcher:" + viewId + ":" + id;
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
        if(container.getId() == View.NO_ID){
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    public abstract Fragment getFragment(int position, T data);

    public void setChangeListener(OnFragmentVisibleChangeListener changeListener){
        mChangeListener = changeListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        if(mCurTransaction == null){
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        final long itemId = getItemId(position);
        String name = makeFragmentName(container.getId(),itemId);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if(fragment != null){
            //当Fragment被detach后，Fragment的生命周期执行完onDestroyView就终止了，
            //这意味着Fragment的实例并没有被销毁，只是UI界面被移除了（注意和remove是有区别的）。
            //当Fragment被detach后，执行attach操作，会让Fragment从onCreateView开始执行，一直执行到onResume。
            //attach无法像add一样单独使用，单独使用会抛异常。方法存在的意义是对detach后的Fragment进行界面恢复。
            mCurTransaction.attach(fragment);
        } else{
            fragment = getFragment(position,getData(position));
            mCurTransaction.add(container.getId(),fragment,makeFragmentName(container.getId(),itemId));
        }
        if(fragment != mCurrentPrimaryItem){
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        if(mCurTransaction == null){
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        Fragment fragment = (Fragment)object;
        if(fragment instanceof OnFragmentDetachListener){
            ((OnFragmentDetachListener)fragment).onPagerDetach();
        }
        mCurTransaction.detach(fragment);
    }

    @Override
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

    public long getItemId(int position){
        return position;
    }
}
