package com.example.atractivity.timer;

public interface ActivityTimerListener {
    void onUpdate(int remainingTimeInSeconds);
    void onFinished();
}
