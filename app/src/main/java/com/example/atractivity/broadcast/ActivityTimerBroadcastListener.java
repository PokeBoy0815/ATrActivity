package com.example.atractivity.broadcast;

public interface ActivityTimerBroadcastListener {
    void onTimerUpdate(int remainingTimeInSeconds);

    void onTimerFinished();
}
