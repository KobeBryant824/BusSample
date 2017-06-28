package com.cxh.busdemo.messenger;

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

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:21.
 */
public class MessengerFragment extends Fragment {
    //  TOKEN: 相当于broadcast的Action,谁注册了这个令牌，相当于准备接收这个消息*/
    public static final String MESSENGERFRAGMENT_UPDATE_TOKEN = "messengerfragment_update_token";
    public static final String TAG = "MessengerFragment";
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

        Messenger.getDefault().register(this, MESSENGERFRAGMENT_UPDATE_TOKEN, Event.class, new Consumer<Event>() {
            @Override
            public void accept(@NonNull Event event) {
                Log.d(TAG, "Event：" + event);
                Log.d(TAG, "accept: " + Thread.currentThread().getName());
                final String str = mResultTv.getText().toString();

                // 模拟错误发生时，是否处理异常，如果处理了是否重新订阅了
                // 处理了，能接收
                event = null;
                String name = event.getName();

                if (Thread.currentThread().getName().equals("main")){
                    mResultTv.setText(TextUtils.isEmpty(str) ? event.getName() : str + "\n" + event.getName());
                } else {
                    final Event finalEvent = event;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mResultTv.setText(TextUtils.isEmpty(str) ? finalEvent.getName() : str + "\n" + finalEvent.getName());
                        }
                    });
                }
            }
        });

        view.findViewById(R.id.to_b_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BActivity.class));
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Messenger.getDefault().unregister(this);
    }
}
