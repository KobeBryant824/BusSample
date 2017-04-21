package com.cxh.busdemo.rxbusolder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cxh.busdemo.Event;
import com.cxh.busdemo.R;
import com.cxh.busdemo.TUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:21.
 */
public class RxBusFragment extends Fragment {
    public static final String TAG = "RxBusFragment";
    private TextView mResultTv;
    private CheckBox mCheckBox;
    private Disposable mRxSub;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eventbus, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mResultTv = (TextView) view.findViewById(R.id.result_tv);
        mCheckBox = (CheckBox) view.findViewById(R.id.checkbox);
        mCheckBox.setVisibility(View.VISIBLE);

        subscribeEvent();

        view.findViewById(R.id.to_b_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BActivity.class));
            }
        });

        view.findViewById(R.id.sticky_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().postSticky(new Event("我来自A，我是先发送，你再注册，但也能接受到"));

                Intent intent = new Intent(getContext(), BActivity.class);
                intent.putExtra("isChecked", mCheckBox.isChecked());
                startActivity(intent);
            }
        });
    }

    private void subscribeEvent() {
        RxDisposable.remove(mRxSub);

        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .map(new Function<Event, Event>() {
                    @Override
                    public Event apply(@NonNull Event event) throws Exception {
                        // 变换等操作
                        return event;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//子线程发布事件需要切换回来操作ui
                .subscribe(new Consumer<Event>() {

                    @Override
                    public void accept(@NonNull Event myEvent) {
                        Log.d(TAG, "onNext--->" + myEvent.getName());
                        Log.d(TAG, "onNext--->" + Thread.currentThread().getName());

                        String str = mResultTv.getText().toString();

                        // 这里模拟产生 Error
                        if (mCheckBox.isChecked()) {
                            myEvent = null;
                            String error = myEvent.getName();
                        }

                        mResultTv.setText(TextUtils.isEmpty(str) ? myEvent.getName() : str + "\n" + myEvent.getName());
                    }
                }, new Consumer<Throwable>() {

                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                        throwable.printStackTrace();
                        Log.e(TAG, "onError");
                        /**
                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
                         */
                        TUtil.showShort(getActivity(), R.string.resubscribe);

                        subscribeEvent();
                    }
                });

        RxDisposable.add(mRxSub);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 从CompositeDisposable中移除取消订阅事件,防止内存泄漏
        RxDisposable.remove(mRxSub);
    }
}
