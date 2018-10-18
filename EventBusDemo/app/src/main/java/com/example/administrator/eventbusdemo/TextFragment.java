package com.example.administrator.eventbusdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/10/17.
 */

@SuppressLint("ValidFragment")
class TextFragment extends BaseFragment {
    @BindView(R.id.text)
    TextView txt;
    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_text;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册监听
        EventBus.getDefault().register(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //EventBus处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final Event event) {
        if (event != null) {
            //调用后台接口，更新UI
            if (event.message.startsWith("text")) {
                String msg = event.message.replace("text#","");
                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
