package com.example.atractivity.overviewFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.Data.Database.ActivityItemQueryResultListener;
import com.example.atractivity.R;

import java.util.ArrayList;
import java.util.List;

public class OverviewPagerAdapter extends FragmentStatePagerAdapter {

    private ActivityItemDatabaseHelper databaseHelper;
    private ArrayList<ActivityItem> activityItems;

    public OverviewPagerAdapter(FragmentManager fragmentManager, ActivityItemDatabaseHelper databaseHelper){
        super(fragmentManager);
        this.databaseHelper = databaseHelper;
        activityItems = new ArrayList<>();
        databaseHelper.getAllActivityItemsFromRoom(new ActivityItemQueryResultListener() {
            @Override
            public void onResult(ActivityItem aitem) {

            }

            @Override
            public void onListResult(List<ActivityItem> aitems) {
                activityItems.addAll(aitems);
            }
        });
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
            String activityNameFromDatabase = activityItems.get(i-1).getActivityName();
            arguments.putString(OverviewFragment.TITLE_ARG, activityNameFromDatabase);
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
