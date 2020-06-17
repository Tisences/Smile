package com.tisen.smile.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.tisen.smile.utils.HttpHelper;
import com.tisen.smile.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;

public class LoadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(HttpHelper.getConfig(this));
        setContentView(R.layout.activity_load);


        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(LoadActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, 2000);
    }
}
