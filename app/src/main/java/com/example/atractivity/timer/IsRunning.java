package com.example.atractivity.timer;

public class IsRunning {
    public static boolean isRunning = false;
    public static int activeNumber = 0;

    static public boolean testRunning(){
        return isRunning;
    }
    static public void setRunning(){
        isRunning = true;
    }
    static public void setNotRunning(){
        isRunning = false;
    }

    static public int testActiveNumber(){return activeNumber;}
    static public void setActiveNumber(int i){activeNumber = i;}
}
