package com.cxh.busdemo.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxh.busdemo.Event;
import com.cxh.busdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:21.
 */
public class EventBusFragment extends Fragment {
    public static final String TAG = "EventBusFragment";
    private TextView mResultTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eventbus, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mResultTv = (TextView) view.findViewById(R.id.result_tv);

        view.findViewById(R.id.to_b_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BActivity.class));
            }
        });

        view.findViewById(R.id.sticky_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky("我来自A，我是先发送，你再注册，但也能接受到");
                startActivity(new Intent(getContext(), BActivity.class));
            }
        });
    }

    /**
     * POSTING线程模型：在哪个线程发布事件，就在哪个线程执行onPostingEvent方法
     * （方法名可以随便取，只要参数和post的匹配就会找到）
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(Event event) {

        Log.d(TAG, "onPostingEvent:" + event);
        Log.d(TAG, "onPostingEvent:" + Thread.currentThread().getName());

        // 模拟错误发生时，是否处理异常，如果处理了是否重新订阅了
        // 处理了，能接收
        event = null;
        String name = event.getName();
        Log.d(TAG, "hahahahhah:" );

    }

    /**
     * MAIN线程模型：不管是哪个线程发布事件，都在主线程执行onMainEvent方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(Event event) {
        String str = mResultTv.getText().toString();
        mResultTv.setText(TextUtils.isEmpty(str) ? event.getName() : str + "\n" + event.getName());

        Log.d(TAG, "onMainEvent:" + event);
        Log.d(TAG, "onMainEvent: " + Thread.currentThread().getName());
    }

    /**
     * BACKGROUND线程模型：事件如果是在子线程发布，onBackgroundEvent方法就在该子线程执行，事件如果是在
     * 主线程中发布，onBackgroundEvent方法就在EventBus内部的线程池中执行
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackgroundEvent(Event event) {

        Log.d(TAG, "onBackgroundEvent:" + event);
        Log.d(TAG, "onBackgroundEvent: " + Thread.currentThread().getName());
    }

    /**
     * ASYNC线程模型：不管事件在哪个线程发布，onAsyncEvent方法都在EventBus内部的线程池中执行
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onAsyncEvent(Event event) {

        Log.d(TAG, "onAsyncEvent:" + event);
        Log.d(TAG, "onAsyncEvent: " + Thread.currentThread().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
