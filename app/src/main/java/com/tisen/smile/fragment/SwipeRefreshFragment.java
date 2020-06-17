package com.tisen.smile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tisen.smile.utils.HttpHelper;
import com.tisen.smile.adapter.JokeAdapter;
import com.tisen.smile.model.Joke;
import com.tisen.smile.model.User;
import com.tisen.smile.utils.Classification;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by tisen on 2016/10/4.
 */
public abstract class SwipeRefreshFragment extends Fragment {


    protected View view;

    protected Context context;
    protected ImageLoader loader;
    protected User user;

    protected SwipeRefreshLayout refreshLayout;
    protected List<Joke> jokes;
    protected ListView listView;
    protected JokeAdapter adapter;

    protected TextView timeTip;

    protected long time;
    protected boolean isPrepared;
    protected boolean isVisible;
    protected abstract String getClassification();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        loader = HttpHelper.getImageLoader(context);
        user = (User) getArguments().getSerializable("user");
        jokes = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        lazyLoad();
    }

    protected SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            jokeDown.sendEmptyMessage(1);
        }
    };


    protected Handler jokeDown = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            BmobQuery<Joke>query = new BmobQuery<>();
            query.addWhereEqualTo("classification",getClassification());
            query.order("-createdAt");
            query.findObjects(new FindListener<Joke>() {
                @Override
                public void done(List<Joke> list, BmobException e) {
                    refreshLayout.setRefreshing(false);
                    if(e==null){
                        time = System.currentTimeMillis();
                        jokes = list;
                        adapter.reFresh(list);
                    }else {

                    }
                }
            });
        }
    };


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            lazyLoad();
        }
        else {
            isVisible = false;
        }
    }
    protected void lazyLoad(){
        if(!isPrepared||!isVisible){
            return;
        }
        if(System.currentTimeMillis()-time<=1000*300){
            return;
        }
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                jokeDown.sendEmptyMessage(1);
            }
        });
    }
}
