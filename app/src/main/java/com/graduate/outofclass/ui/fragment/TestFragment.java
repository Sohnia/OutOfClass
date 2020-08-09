package com.graduate.outofclass.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.graduate.outofclass.PartActivity;
import com.graduate.outofclass.R;
import com.graduate.outofclass.adapter.MainViewPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.prefs.AbstractPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.fragment_viewpager)
    ViewPager viewPager;
    Bundle mBundle;
    int currentPage;
    private ArrayList<Fragment> fragments;

    private void initFragments() {
        fragments=new ArrayList<>();
        IncludeFragment [] includeFragments= {new IncludeFragment(),new IncludeFragment(),
                new IncludeFragment()};
        Collections.addAll(fragments, includeFragments);
    }
    private void initTabLayout() {
        String [] tmp = getResources().getStringArray(R.array.exam_array);
        for(int i = 0; i < tmp.length ; i++){
            try {
                tabLayout.getTabAt(i).setText(tmp[i]);
            }catch (Exception e){
                Log.e("TestFragments",e.getMessage() + " ");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test,container,false);
        unbinder = ButterKnife.bind(this,rootView);
        initFragments();
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(mainViewPagerAdapter);
//        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
        initTabLayout();
        return rootView;
//        partOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), PartActivity.class);
//                intent.putExtra("partNumber",1);
//                startActivity(intent);
//            }
//
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
