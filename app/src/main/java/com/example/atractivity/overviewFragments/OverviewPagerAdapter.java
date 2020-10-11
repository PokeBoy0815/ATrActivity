package com.example.atractivity.overviewFragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

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


    private ArrayList<String> nameList;

    public OverviewPagerAdapter(FragmentManager fragmentManager, ArrayList<String> nameList){
        super(fragmentManager);
        this.nameList = nameList;
        Log.e("VOR_DATBASE QUERY", "hier");
        databaseSpaTest();
        Log.e("Nach_DATBASE QUERY", "hier");
    }

    private void databaseSpaTest() {

    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new OverviewFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(OverviewFragment.VIEW_NUMBER_ARG, i);
        if (i==0){
            String titleString = "Heute";
            arguments.putString(OverviewFragment.TITLE_ARG, titleString);
        }
        else {
            String activityNameFromDatabase = nameList.get(i);
            arguments.putString(OverviewFragment.TITLE_ARG, activityNameFromDatabase);
        }
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        //getCountFromDatabase
        return 4;

    }

    @Override
    public CharSequence getPageTitle(int position){
        return "item" + (position+1);
    }
}
