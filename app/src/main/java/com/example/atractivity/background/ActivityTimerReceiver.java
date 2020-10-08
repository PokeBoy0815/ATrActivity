package com.example.atractivity.background;

//background process, which will not be closed when the app gets closed.
//maybe use "IntentService" instead of "Service"?
//https://www.linkedin.com/pulse/service-vs-intentservice-android-anwar-samir

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ActivityTimerReceiver extends Service {

    /*private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder{
        ActivityTimerReceiver getService(){
            return ActivityTimerReceiver.this;
        }
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        super.onStartCommand(intent, flags, startID);
        //while (timeLimit-startTime >0) true do {wait} else {benachrichtigung}
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
