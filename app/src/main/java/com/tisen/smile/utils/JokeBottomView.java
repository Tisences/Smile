package com.tisen.smile.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.tisen.smile.R;
import com.tisen.smile.model.Joke;
import com.tisen.smile.model.User;

/**
 * Created by tisen on 2016/10/9.
 */
public class JokeBottomView extends View {
    public JokeBottomView(Context context) {
        super(context);
    }

    public JokeBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JokeBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JokeBottomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setJokeInfo(Joke joke, User user){

    }
}
