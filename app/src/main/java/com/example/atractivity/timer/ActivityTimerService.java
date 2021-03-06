package com.example.atractivity.timer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.ReturnKeys;
import com.example.atractivity.Homescreen;
import com.example.atractivity.R;
import com.example.atractivity.broadcast.ActivityTimerBroadcastReceiver;



public class ActivityTimerService extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "ACTIVITY_TIMER_CHANNEL_ID";
    public static final String NOTIFICATION_CHANNEL_NAME = "ActivityTimer Channel";
    public static final String NOTIFICATION_CHANNEL_DESCRIPTION= "Notification channel for ActivityTimer";

    private static int currentNotificationID = 0;
    private ActivityTimer timer;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

        @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Notification foregroundNotification = getNotificationForForegroundService();
        startForeground(getCurrentNotificationID(), foregroundNotification);
    }

    @Override
    public void onDestroy() {
        timer.stop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final ActivityItem activityItem = (ActivityItem) intent.getSerializableExtra(ReturnKeys.ACTIVITY_EXTRA_KEY);

        startTimerForActivity(activityItem);

        return super.onStartCommand(intent, flags, startId);
    }


    /** This method starts an timer for an specific activity. */
    private void startTimerForActivity(final ActivityItem activityItem){
        timer = new ActivityTimer(activityItem, new ActivityTimerListener() {
            @Override
            public void onUpdate(int remainingTimeInSeconds) {
                broadcastTimerUpdate(remainingTimeInSeconds);
            }

            @Override
            public void onFinished() {
                broadcastTimerFinished();
            }

            @Override
            public void onStopped() {
                broadcastTimerStopped();
            }


        });
        timer.start();
    }


    /** The following methods notify the broadcast, if the timer is updated, finished or stopped. */
    private void broadcastTimerUpdate(int remainingTimeInSeconds) {
        Intent intent = ActivityTimerBroadcastReceiver.getUpdateIntent(remainingTimeInSeconds);
        sendBroadcast(intent);
    }

    private void broadcastTimerFinished() {
        Intent intent = ActivityTimerBroadcastReceiver.getEndIntent();
        sendBroadcast(intent);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(getCurrentNotificationID(), getNotificationForFinishedTimer());

        this.stopSelf();
    }

    private void broadcastTimerStopped(){
        Intent intent = ActivityTimerBroadcastReceiver.getEndIntent();
        sendBroadcast(intent);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(getCurrentNotificationID(), getNotificationForStoppedTimer());
    }

    /** The following methods are system notifications if an activity is started, is running,
     * has reached the goal or gets aborted. */
  private int getCurrentNotificationID() {
        currentNotificationID++;
        return currentNotificationID;
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }

    private Notification getNotificationForForegroundService() {
        Intent notificationIntent = new Intent(this, Homescreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getText(R.string.foreground_notification_title))
                .setContentText(getText(R.string.foreground_notification_text))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.foreground_notification_ticker))
                .build();
        return notification;
    }

    private Notification getNotificationForFinishedTimer() {
        Intent notificationIntent = new Intent(this, Homescreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getText(R.string.timer_finished_notification_title))
                .setContentText(getText(R.string.timer_finished_notification_text))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        return notification;
    }

    private Notification getNotificationForStoppedTimer(){
        Intent notificationIntent = new Intent(this, Homescreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getText(R.string.timer_stopped_notification_title))
                .setContentText(getText(R.string.timer_stopped_notification_text))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        return notification;
    }

}
