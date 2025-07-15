package com.cricbuzzplus.liveline.livedata.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> hmFragmentList = new ArrayList<>();
    private final List<String> hmFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return hmFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return hmFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        hmFragmentList.add(fragment);
        hmFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return hmFragmentTitleList.get(position);
    }
}





