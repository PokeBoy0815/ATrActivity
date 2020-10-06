package com.example.atractivity.Data;

import android.content.Context;
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

        }
        return itemView;
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
