package com.example.atractivity.Data.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.ActivityItemAdapter;
import com.example.atractivity.R;

public class ActivityItemAdapterBase extends BaseAdapter{

    private ActivityItemDatabaseHelper databaseHelper;
    private Context context;
    private ActivityItem activityItem;

    public ActivityItemAdapterBase(Context context, ActivityItemDatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {

        databaseHelper.getActivityItemByUID(i, new ActivityItemQueryResultListener() {
            @Override
            public void onResult(ActivityItem aitem) {
                activityItem = aitem;
            }
        });
        return renderItemInView(view, activityItem);
    }

    private View renderItemInView(View convertView, ActivityItem item) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.activity_list_item, null);
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

        return  min +" "+ hours +" Stunde und "+ minutes +" Minuten";
    }
}
