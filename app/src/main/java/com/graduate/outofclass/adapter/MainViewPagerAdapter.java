package com.graduate.outofclass.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.graduate.outofclass.ui.fragment.IncludeFragment;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
//    private static final int TAB_COUNT = 3;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment includeFragment = fragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("state", position);
        includeFragment.setArguments(bundle);
        return includeFragment;
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    //Remove a page for the given position. The adapter is responsible for removing the view from its container
    //防止重新销毁视图
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //如果注释这行，那么不管怎么切换，page都不会被销毁
//        super.destroyItem(container, position, object);
    }
}
