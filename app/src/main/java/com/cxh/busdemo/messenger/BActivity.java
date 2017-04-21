package com.cxh.busdemo.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxh.busdemo.R;

/**
 * Created by Hai (haigod7@gmail.com) on 2017/4/18 17:30.
 */
public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        setTitle("B");
    }

    public void toC(View view) {
        startActivity(new Intent(this, CActivity.class));
    }

}
