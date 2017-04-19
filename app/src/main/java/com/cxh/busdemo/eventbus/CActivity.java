package com.cxh.busdemo.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxh.busdemo.Event;
import com.cxh.busdemo.R;

import org.greenrobot.eventbus.EventBus;

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

    public void onPublishEventOnMainThread(View view){
        EventBus.getDefault().post(new Event("大家好，我是EventBus，我是一条来自C的消息"));
        finish();
    }

    public void onPublishEventOnBGThread(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new Event("大家好，我是EventBus，我是一条来自C的后台线程的消息"));
                finish();
            }
        }).start();
    }

}
