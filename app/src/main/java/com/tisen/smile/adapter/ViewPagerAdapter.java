package com.tisen.smile.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tisen.smile.fragment.ViewPagerFragment;

import java.util.ArrayList;

/**
 * Created by tisen on 2016/10/3.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String>tabTitles;
    private ArrayList<Fragment>fragments;
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<String> tabTitles, Context context, ArrayList<Fragment>fragments) {
        super(fm);
        this.tabTitles = tabTitles;
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
