package com.example.administrator.eventbusdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    //根布局视图
    private View mContentView;
    //视图是否已经初始化完毕
    private boolean isViewReady;
    //fragment是否处于可见状态
    private boolean isFragmentVisible;
    //是否已经初始化加载过
    protected boolean isLoaded;
    //用于butterknife解绑
    private Unbinder unbinder;
    /***封装toast对象**/
    private static Toast toast;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();

    protected abstract boolean isLazyLoad();//是否使用懒加载 (Fragment可见时才进行初始化操作(以下四个方法))

    protected abstract int getContentLayout();//返回页面布局id

    protected abstract void initView();//做视图相关的初始化工作

    protected abstract void initData();//做数据相关的初始化工作

    protected abstract void setListener();//做数据相关的初始化工作

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            mContentView = inflater.inflate(getContentLayout(), container, false);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //视图准备完毕
        isViewReady = true;
        if (!isLazyLoad() || isFragmentVisible) {
            init();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragmentVisible = isVisibleToUser;
        //如果视图准备完毕且Fragment处于可见状态，则开始初始化操作
        if (isLazyLoad() && isViewReady && isFragmentVisible) {
            init();
        }
    }

    public void init() {
        if (!isLoaded) {
            isLoaded = true;
            initView();
            initData();
            setListener();
        }
    }
    /**
     * 显示长toast
     *
     * @param msg
     */
    @SuppressLint("ShowToast")
    public void toastLong(String msg) {
        if (null == toast) {
            toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    /**
     * 显示短toast
     *
     * @param msg
     */
    @SuppressLint("ShowToast")
    public void toastShort(String msg) {
        if (null == toast) {
            toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    /**
     * 显示Log
     * @param strMsg
     */
    public void logger(String strMsg) {
        Log.e(TAG, strMsg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife解绑
        if (unbinder != null) unbinder.unbind();
        isViewReady = false;
        isLoaded = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
