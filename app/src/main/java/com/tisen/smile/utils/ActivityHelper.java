package com.tisen.smile.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tisen on 2016/9/29.
 */
public class ActivityHelper {

    public static void toast(Context context,String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
