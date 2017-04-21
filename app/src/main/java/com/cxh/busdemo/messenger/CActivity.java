package com.cxh.busdemo.messenger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxh.busdemo.Event;
import com.cxh.busdemo.R;

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
     * 发生异常直接终止后续事件的传送，这种方式更麻烦，还不能在注册房处理异常，重新订阅
     */
    public void onPublishEventOnMainThread(View view) {
        try {
            Messenger.getDefault().send(new Event("大家好，我是Messenger，我是一条来自C的消息"), MessengerFragment.MESSENGERFRAGMENT_UPDATE_TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();
    }

    public void onPublishEventOnBGThread(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Messenger.getDefault().send(new Event("大家好，我是Messenger，我是一条来自C的后台进程的消息"), MessengerFragment.MESSENGERFRAGMENT_UPDATE_TOKEN);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();
            }
        }).start();
    }

}
