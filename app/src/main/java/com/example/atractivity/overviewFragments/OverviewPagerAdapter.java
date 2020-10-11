package com.example.atractivity.overviewFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;

public class OverviewPagerAdapter extends FragmentStatePagerAdapter {

    public OverviewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putInt(OverviewFragment.ARG, i+1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return "item" + (position+1);
    }
}
