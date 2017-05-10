package com.cxh.busdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cxh.busdemo.eventbus.EventBusFragment;
import com.cxh.busdemo.messenger.MessengerFragment;
import com.cxh.busdemo.otto.OttoFragment;
import com.cxh.busdemo.rxbus.RxBusFragment;
import com.cxh.busdemo.rxbusold.RxBusOldFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_eventbus:
                    switchContent(new EventBusFragment());
                    return true;

                case R.id.navigation_otto:
                    switchContent(new OttoFragment());
                    return true;

                case R.id.navigation_rxbus_old:
                    switchContent(new RxBusOldFragment());
                    return true;

                case R.id.navigation_rxbus_new:
                    switchContent(new RxBusFragment());
                    return true;

                case R.id.navigation_messenger:
                    switchContent(new MessengerFragment());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("A");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        switchContent(new EventBusFragment());
    }

    private void switchContent(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment).commit();
    }



}
