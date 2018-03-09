package com.rhettnewton.musicplayer.utils;

/**
 * Created by Rhett on 3/9/2018.
 */

public class TimeUtils {

    public static String convertMillisToMinutesSeconds(long millis) {
        long minutes = (millis / 1000)  / 60;
        long seconds = (millis / 1000) % 60;
        return String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }
}
