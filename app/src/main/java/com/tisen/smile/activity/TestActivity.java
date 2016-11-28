package com.tisen.smile.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tisen.smile.R;
import com.tisen.smile.bean.Joke;
import com.tisen.smile.utils.Helper;
import com.tisen.smile.utils.HttpHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by tisen on 2016/10/8.
 */
public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        GestureImageView view = new GestureImageView(this);
//        view.setImageResource(R.drawable.long_image);
//        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        view.setLayoutParams(params);
//        ViewGroup layout = (ViewGroup) findViewById(R.id.layout);
//        layout.addView(view);

        Joke joke = new Joke();
        joke.setTitle("1234");
        joke.setContent("2345");
        joke.save();
        ArrayList<Joke>jokes = (ArrayList<Joke>) Joke.listAll(Joke.class);
        Log.d("TestActivity",Arrays.toString(jokes.toArray()));

    }
}
