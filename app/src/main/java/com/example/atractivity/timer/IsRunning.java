package com.example.atractivity.timer;

public class IsRunning {
    public static boolean isRunning = false;

    static public boolean testRunning(){
        return isRunning;
    }
    static public void setRunning(){
        isRunning = true;
    }
    static public void setNotRunning(){
        isRunning = false;
    }

}
