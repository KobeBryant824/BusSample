package com.cxh.busdemo.rxbusolder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cxh.busdemo.Event;
import com.cxh.busdemo.R;
import com.cxh.busdemo.TUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:30.
 */
public class BActivity extends AppCompatActivity {
    private final String TAG = "BActivity";
    private TextView stickyTv;
    private Disposable mRxSubSticky;
    private boolean mIsChecked;
    private Event mStickyEvent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        setTitle("B");

        stickyTv = (TextView) findViewById(R.id.sticky_btn);

        mIsChecked = getIntent().getBooleanExtra("isChecked", false);

        subscribeEventSticky();
    }

    public void toC(View view) {
        startActivity(new Intent(this, CActivity.class));
    }

    private void subscribeEventSticky() {

        mStickyEvent = RxBus.getDefault().getStickyEvent(Event.class);
        Log.d(TAG, "获取到StickyEvent--->" + mStickyEvent);

        if (mStickyEvent == null) return;

        mRxSubSticky = RxBus.getDefault().toObservableSticky(Event.class)
                // 建议在Sticky时,在操作符内主动try,catch
                .map(new Function<Event, Event>() {
                    @Override
                    public Event apply(@NonNull Event eventSticky) throws Exception {
                        try {
                            // 变换操作
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return eventSticky;
                    }
                })
                .subscribe(new Consumer<Event>() {
                    @Override
                    public void accept(@NonNull Event eventSticky) throws Exception {
                        try {
                            stickyTv.setVisibility(View.VISIBLE);
                            // 这里模拟产生 Error
                            if (mIsChecked) {
                                eventSticky = null;
                                TUtil.showShort(BActivity.this, R.string.sticky);
                                String error = eventSticky.getName();
                            }

                            final String name = eventSticky.getName();
                            Observable.timer(1, TimeUnit.SECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Long>() {
                                        @Override
                                        public void accept(@NonNull Long aLong) throws Exception {
                                            stickyTv.setText(name);

                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Log.e(TAG, "onError--Sticky");
                        /**
                         * 这里注意: Sticky事件 不能在onError时重绑事件,这可能导致因绑定时得到引起Error的Sticky数据而产生死循环
                         */
                    }
                });

        RxDisposable.add(mRxSubSticky);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxDisposable.remove(mRxSubSticky);
    }
}
