package com.cxh.busdemo.otto;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.otto.Bus;

public final class BusProvider extends Bus {
    private static BusProvider sBusProvider = new BusProvider();

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private BusProvider() {
    }

    public static BusProvider getInstance() {
        return sBusProvider;
    }

    //其实这里相当于重写post方法，但在runnable线程中不能再通过super调用Bus里面的post方法，所以重新命名
    public void mPost(final Object event) {

        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.d("otto", "--主线程");
            sBusProvider.post(event);

        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("otto", "--非主线程");
                    sBusProvider.post(event);
                }
            });
        }
    }
}