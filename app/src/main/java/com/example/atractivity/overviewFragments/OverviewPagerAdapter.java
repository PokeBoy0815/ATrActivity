package com.example.atractivity.overviewFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.R;

public class OverviewPagerAdapter extends FragmentStatePagerAdapter {

    private ActivityItemDatabaseHelper databaseHelper;

    public OverviewPagerAdapter(FragmentManager fragmentManager, ActivityItemDatabaseHelper databaseHelper){
        super(fragmentManager);
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new OverviewFragment();
        if (i==0){
            Bundle arguments = new Bundle();
            arguments.putInt(OverviewFragment.VIEW_NUMBER_ARG, i);
            arguments.putString(OverviewFragment.TITLE_ARG, String.valueOf(R.string.overview_title_today));
            fragment.setArguments(arguments);
            return fragment;
        }
        else {
            Bundle arguments = new Bundle();
            arguments.putInt(OverviewFragment.VIEW_NUMBER_ARG, i);
            fragment.setArguments(arguments);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        //getCountFromDatabase
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return "item" + (position+1);
    }
}
