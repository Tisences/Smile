package com.tisen.smile.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tisen on 2016/10/9.
 */
public class CollectImageView extends ImageView {

    private boolean state = false;
    private int openResId;
    private int closeResId;
    private String jokeId;

    public CollectImageView(Context context) {
        super(context);
    }

    public CollectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CollectImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initState(String jokeId) {
        this.jokeId = jokeId;
        if (UserInfo.myCollectId.contains(jokeId))
            setState(true);
        else setState(false);
    }

    public void setState(boolean b) {
        state = b;
        super.setImageResource(state ? openResId : closeResId);
    }

    public void onClick() {
        if(state){
            UserInfo.myCollectId.remove(jokeId);
        }
        else {
            UserInfo.myCollectId.add(jokeId);
        }
        state = !state;
        super.setImageResource(state ? openResId : closeResId);
    }

    public void setImageResource(int openResId, int colseResId) {
        this.openResId = openResId;
        this.closeResId = colseResId;
    }
}
