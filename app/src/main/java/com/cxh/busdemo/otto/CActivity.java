package com.cxh.busdemo.otto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxh.busdemo.Event;
import com.cxh.busdemo.R;
import com.squareup.otto.Produce;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:48.
 */
public class CActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        setTitle("C");
    }

    @Produce
    public Event produceMessageEvent() {
//        return null;// post讲不会发送事件出去
        return new Event("大家好，我是Otto，我是一条来自C的消息");
    }

    public void onPublishEventOnMainThread(View view){
//        BusProvider.getInstance().mPost(new Event("大家好，我是Otto，我是一条来自C的消息"));
        BusProvider.getInstance().mPost(produceMessageEvent());
        finish();
    }

    public void onPublishEventOnBGThread(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BusProvider.getInstance().mPost(new Event("大家好，我是Otto，我是一条来自C的后台线程的消息"));
                finish();
            }
        }).start();
    }

}
