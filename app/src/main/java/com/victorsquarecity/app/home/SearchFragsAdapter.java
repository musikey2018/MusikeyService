package com.victorsquarecity.app.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bilaw on 6/8/2017.
 */
/*
* class for storing search fragments for app*/
public class SearchFragsAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SearchFragsAdapter";
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public SearchFragsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }
}
