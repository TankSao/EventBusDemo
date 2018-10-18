package com.example.administrator.eventbusdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/17.
 */

@SuppressLint("ValidFragment")
class SendFragment extends BaseFragment {

    private static int NUM = 0;//0吐司，1图片，2文字
    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_send;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {

    }
    @OnClick({R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send:
                UpdateUI(NUM%3);
                NUM++;
                break;
        }
    }

    private void UpdateUI(final int num) {
        Log.e("num",num+"s");
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    // 发布事件，在后台线程发的事件
                    switch (num){
                        case 0:
                            EventBus.getDefault().post(new Event("toast#吐司吐司吐司吐司"));
                            break;
                        case 1:
                            EventBus.getDefault().post(new Event("picture#吐司吐司吐司吐司"));
                            break;
                        case 2:
                            EventBus.getDefault().post(new Event("text#发生了改变"));
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }
}
