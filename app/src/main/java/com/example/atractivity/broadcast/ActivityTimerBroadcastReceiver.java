package com.example.atractivity.broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


public class ActivityTimerBroadcastReceiver extends BroadcastReceiver {

    private static final String TIMER_UPDATE = "com.example.atractivity.broadcast.TIMER_UPDATE";
    private static final String TIMER_FINISHED = "com.example.atractivity.broadcast.TIMER_FINISHED";
    private static final String REMAINING_TIME_IN_SECONDS = "REMAINING_TIME_IN_SECONDS";

    private ActivityTimerBroadcastListener listener;

    public ActivityTimerBroadcastReceiver(ActivityTimerBroadcastListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case TIMER_UPDATE:
                int remainingTimeInSeconds = intent.getExtras().getInt(REMAINING_TIME_IN_SECONDS);
                listener.onTimerUpdate(remainingTimeInSeconds);
                break;
            case TIMER_FINISHED:
                listener.onTimerFinished();
                break;
        }
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ActivityTimerBroadcastReceiver.TIMER_UPDATE);
        filter.addAction(ActivityTimerBroadcastReceiver.TIMER_FINISHED);
        return filter;
    }
    /**
     * Erzeugt einen Intent, der diesen BroadcastReceiver darüber informiert, wieviele Sekunden im
     * Timer noch verbleiben.
     */
    public static Intent getUpdateIntent(int remainingTimeInSeconds) {
        Intent intent = new Intent();
        intent.setAction(TIMER_UPDATE);
        intent.putExtra(REMAINING_TIME_IN_SECONDS,remainingTimeInSeconds);
        return intent;
    }

    /**
     * Erzeugt einen Intent, der diesen BroadcastReceiver darüber informiert, dass die Zeit im Timer
     * vollständig abgelaufen ist.
     */
    public static Intent getEndIntent() {
        Intent intent = new Intent();
        intent.setAction(TIMER_FINISHED);
        return intent;
    }
}
