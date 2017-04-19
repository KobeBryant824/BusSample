package com.cxh.busdemo.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cxh.busdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:30.
 */
public class BActivity extends AppCompatActivity {

    private TextView stickyTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        setTitle("B");

        stickyTv = (TextView) findViewById(R.id.sticky_btn);
        // 得先拿控件再注册
        EventBus.getDefault().register(this);

    }

    public void toC(View view) {
        startActivity(new Intent(this, CActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void stickyFromA(final String event) {
        stickyTv.setVisibility(View.VISIBLE);

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        stickyTv.setText(event);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
