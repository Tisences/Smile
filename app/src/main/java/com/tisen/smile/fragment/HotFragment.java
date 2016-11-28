package com.tisen.smile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tisen.smile.R;
import com.tisen.smile.utils.Classification;

/**
 * Created by tisen on 2016/10/4.
 */
public class HotFragment extends SwipeRefreshFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.viewpager_view,null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.orangered,R.color.greenyellow,R.color.cornflowerblue,R.color.lightcyan);
        refreshLayout.setOnRefreshListener(onRefreshListener);
        isPrepared = true;
        return view;
    }

    @Override
    protected String getClassification() {
        return Classification.HOT;
    }
}
