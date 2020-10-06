package com.example.atractivity.Data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.R;

import java.util.ArrayList;

public class ActivityItemAdapter extends ArrayAdapter<ActivityItem> {


    private ArrayList<ActivityItem> items;

    public ActivityItemAdapter(@NonNull Context context, ArrayList<ActivityItem> items) {
        super(context, R.layout.activity_list_item, items);
        this.items = items;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ActivityItem currentItem = items.get(position);
        return renderItemInView(convertView, currentItem);
    }

    private View renderItemInView(View convertView, ActivityItem item) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(this.getContext()).inflate(R.layout.activity_list_item, null);
        }
        if (item != null) {
            TextView nameView = itemView.findViewById(R.id.listitemsname);
            TextView timeView = itemView.findViewById(R.id.listitemtime);

            nameView.setText(item.getActivityName());
            timeView.setText(getTimeText(item));

            itemView.setBackgroundColor(getColor(item));

        }
        return itemView;
    }

    private int getColor(ActivityItem item) {
        int color = item.getColor();
        switch (color) {
            case 1:
               return Color.parseColor("#FFEB3B");
            case 2:
                return Color.parseColor("holo_orange_dark");
            case 3:
                return Color.parseColor("#FFCC0000");
            case 4:
                return Color.parseColor("#FFAA66CC");
            case 5:
                return Color.parseColor("#FF0099CC");
            case 6:
                return Color.parseColor("#FF00DDFF");
            case 7:
                return Color.parseColor("#FF99CC00");
            default:
                return Color.parseColor("#FFCC0000");
        }

    }

    private String getTimeText(ActivityItem item) {
        String min;
        if(item.isMin()){
            min = "Mindestens";
        } else {
            min = "Maximal";
        }
        int hours = item.getHours();
        int minutes = item.getMinutes();

        return  min +" "+ hours +" Stunde(n) und "+ minutes +" Minuten";
    }


}
