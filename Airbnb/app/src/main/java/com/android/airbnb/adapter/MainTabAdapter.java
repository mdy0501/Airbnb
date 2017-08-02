package com.android.airbnb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by MDY on 2017-08-02.
 */

public class MainTabAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public MainTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0 :

//                return searchFragment;
//            case 1 :
//
//                return wishFragment;
//            case 2 :
//
//                return travelFragment;
//            case 3 :
//
//                return messageFragment;
//            case 4 :
//
//                return profileFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
