package com.example.atractivity.overviewFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atractivity.R;


public class OverviewFragment extends Fragment {
    public static final String VIEW_NUMBER_ARG = "viewNumber";
    public static final String TITLE_ARG = "titelparameter";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        Bundle args = getArguments();
        int viewNumber = args.getInt(OverviewFragment.VIEW_NUMBER_ARG);
        String param = Integer.toString(args.getInt(OverviewFragment.VIEW_NUMBER_ARG));
        ((TextView)view.findViewById(R.id.overview)).setText(param);
        return view;
    }

}