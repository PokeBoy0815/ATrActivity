package com.example.atractivity.overviewFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atractivity.R;


public class OverviewFragment extends Fragment {
    public static final String ARG = "parameter";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        Bundle args = getArguments();
        String param = Integer.toString(args.getInt(OverviewFragment.ARG));
        ((TextView)view.findViewById(R.id.overview)).setText(param);
        return view;
    }

    private String[] getTitleStrings(){
        return null;
    }

    
}