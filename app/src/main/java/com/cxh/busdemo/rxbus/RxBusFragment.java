package com.cxh.busdemo.rxbus;

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
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:21.
 */
public class RxBusFragment extends Fragment {
    //  TOKEN: 相当于broadcast的Action,谁注册了这个令牌，相当于准备接收这个消息*/
    public static final String RXBUSNEWFRAGMENT_UPDATE_TOKEN = "rxbusnewfragment_update_token";
    public static final String TAG = "RxBusFragment";
    private TextView mResultTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eventbus, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mResultTv = (TextView) view.findViewById(R.id.result_tv);
        view.findViewById(R.id.sticky_btn).setVisibility(View.GONE);

        view.findViewById(R.id.to_b_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BActivity.class));
            }
        });

        RxBus.get().register(this);
    }

    @Subscribe
    public void eat(Event event) {
        Log.d(TAG, "" + event);
        Log.d(TAG, "eat: " + Thread.currentThread().getName());

        // 模拟错误发生时，是否处理异常，是否下次还能接收到事件
        // 没处理，抛给开发者
//        try{
//            event = null;
//            String name = event.getName();
//        } catch (Exception e){
//            RxBus.get().register(this);// 假如应用上线时出现错误，将不会重新订阅事件
//        }

        String str = mResultTv.getText().toString();
        mResultTv.setText(TextUtils.isEmpty(str) ? event.getName() : str + "\n" + event.getName());
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag}
    )
    public void eat1(String event) {
        Log.d(TAG, "eat1: " + Thread.currentThread().getName());

        String str = mResultTv.getText().toString();
        mResultTv.setText(TextUtils.isEmpty(str) ? event : str + "\n" + event);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(RXBUSNEWFRAGMENT_UPDATE_TOKEN)
            }
    )
    public void eatMore(Event event) {
        Log.d(TAG, "eatMore: " + Thread.currentThread().getName());

        String str = mResultTv.getText().toString();
        mResultTv.setText(TextUtils.isEmpty(str) ? event.getName() : str + "\n" + event.getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }
}
