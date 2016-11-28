package com.tisen.smile.activity;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by tisen on 2016/9/29.
 */
public class BaseActivity extends AppCompatActivity {
    public static String TAG = "BaseActivity";

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
    public String getTAG(){
        return getClass().getSimpleName();
    }
}
