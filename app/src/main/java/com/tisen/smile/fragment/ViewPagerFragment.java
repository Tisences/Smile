package com.tisen.smile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tisen.smile.utils.HttpHelper;
import com.tisen.smile.adapter.JokeAdapter;
import com.tisen.smile.model.Joke;
import com.tisen.smile.model.User;

import java.util.ArrayList;

/**
 * Created by tisen on 2016/10/3.
 */
public class ViewPagerFragment extends Fragment {


    protected View view;
    public static final String PAGE_NAME = "VIEWPAGER";

    protected Context context;
    protected ImageLoader loader;
    protected User user;

    protected ArrayList<Joke> jokes;
    protected ListView jokeListView;
    protected JokeAdapter jokeAdapter;

    public ViewPagerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        loader = HttpHelper.getImageLoader(context);
        user = (User) getArguments().getSerializable("user");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
