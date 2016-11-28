package com.tisen.smile.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tisen.smile.R;

/**
 * Created by tisen on 2016/10/5.
 */
public class ContentActivity extends BaseActivity {
    protected ImageView back;
    protected TextView title;
    protected ImageView buttonImage;

    public void initTop(String s){
        back = (ImageView) findViewById(R.id.activity_back);
        title = (TextView) findViewById(R.id.activity_title);
        buttonImage = (ImageView) findViewById(R.id.activity_button_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        title.setText(s);
    }
}
