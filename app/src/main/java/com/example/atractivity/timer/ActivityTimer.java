package com.example.atractivity.timer;

import com.example.atractivity.Data.ActivityItem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ActivityTimer implements Runnable {

    private static final int DEFAULT_DELAY = 1000;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    private ActivityTimerListener listener;
    private ScheduledFuture scheduledTimerFuture;

    private long endTime;

    public ActivityTimer(ActivityItem activity, ActivityTimerListener listener){
        this.listener = listener;
        this.endTime = System.currentTimeMillis() + ((activity.getHours()*60+activity.getMinutes())*60000);
    }

    public void start(){
        scheduledTimerFuture = Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this,DEFAULT_DELAY, DEFAULT_DELAY, DEFAULT_TIME_UNIT);
    }

    public void stop(){
        scheduledTimerFuture.cancel(true);
    }

    @Override
    public void run() {
        final long now = System.currentTimeMillis();
        final int secondsRemaining = (int) (endTime - now) / 1000;
        listener.onUpdate(secondsRemaining);
        if (now >= endTime) {
            listener.onFinished();
            scheduledTimerFuture.cancel(true);
        }
    }


}
