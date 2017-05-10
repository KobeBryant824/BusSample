package com.cxh.busdemo.rxbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxh.busdemo.Event;
import com.cxh.busdemo.R;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

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

    /**
     * 生产默认事件
     */
    @Produce(
            thread = EventThread.NEW_THREAD,
            tags = {@Tag}
    )
    public String tell() {
        return "大家好，我是RxBusNew，我是一条来自C的消息" + Thread.currentThread();
    }

    /**
     * 发生异常直接终止后续事件的传送，这种方式更麻烦，还不能在注册房处理异常，重新订阅
     */
    public void onPublishEventOnMainThread(View view) {

//        RxBus.get().post(tell());

        RxBus.get().post(new Event("大家好，我是RxBusNew，我是一条来自C的消息"));
        finish();
    }

    public void onPublishEventOnBGThread(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                RxBus.get().post(RxBusFragment.RXBUSNEWFRAGMENT_UPDATE_TOKEN, new Event("大家好，我是RxBusNew，我是一条来自C的子线程带Tag消息"));

                finish();
            }
        }).start();
    }

}
