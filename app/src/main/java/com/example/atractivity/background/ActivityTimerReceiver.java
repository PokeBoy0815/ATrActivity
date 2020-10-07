package com.example.atractivity.background;

//background process, which will not be closed when the app gets closed.
//maybe use "IntentService" instead of "Service"?
//https://www.linkedin.com/pulse/service-vs-intentservice-android-anwar-samir

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ActivityTimerReceiver extends Service {

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        super.onStartCommand(intent, flags, startID);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
