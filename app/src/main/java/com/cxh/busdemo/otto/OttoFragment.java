package com.cxh.busdemo.otto;

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
import com.squareup.otto.Subscribe;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:21.
 */
public class OttoFragment extends Fragment {
    public static final String TAG = "OttoFragment";
    private TextView mResultTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
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

        view.findViewById(R.id.sticky_btn).setVisibility(View.GONE);
    }

    //这个注解一定要有,表示订阅了MessageEvent,并且方法的用 public 修饰的.方法名可以随意取,重点是参数,它是根据你的参数进行判断来自于哪个发送的事件
    @Subscribe
    public void showEvent(Event event) {
        Log.d(TAG, "" + event);
        // 模拟错误发生时，是否处理异常，是否下次还能接收到事件
        // 没处理，抛给开发者
        // 只好自己trycatch否则会出现后续事件不能接收
//        try{
//            String nullStr = null;
//            int length = nullStr.length();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        String str = mResultTv.getText().toString();
        mResultTv.setText(TextUtils.isEmpty(str) ? event.getName() : str + "\n" + event.getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }
}
