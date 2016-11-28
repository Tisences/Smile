package com.tisen.smile.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by tisen on 2016/10/3.
 */
public class Helper {

    public static void blurBitmap(Context context,ImageView view,Bitmap bitmap,int radius){
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript script = RenderScript.create(context);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(script, Element.U8_4(script));

        Allocation allIn = Allocation.createFromBitmap(script,bitmap);
        Allocation allOut = Allocation.createFromBitmap(script,outBitmap);

        blur.setRadius(radius);
        blur.setInput(allIn);
        blur.forEach(allOut);

        allOut.copyTo(outBitmap);
        bitmap.recycle();
        script.destroy();

        view.setImageBitmap(outBitmap);
    }

    public static List<String> getList(Context context,String groupName){

        SharedPreferences sharedPreferences = context.getSharedPreferences("smile",Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(groupName,null);
        if(json!=null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>(){}.getType();
            List<String>lists = new ArrayList<>();
            lists = gson.fromJson(json,type);
            return lists;
        }
        return new ArrayList<String>();

    }
    public static void setList(Context context,String groupName,List<String>list){
        SharedPreferences.Editor editor = context.getSharedPreferences("smile",Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        Log.d("json:",json);
        editor.putString(groupName,json);
        editor.commit();
    }


}
