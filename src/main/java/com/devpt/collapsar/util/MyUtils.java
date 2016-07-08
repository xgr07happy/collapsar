package com.devpt.collapsar.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenyong on 2016/5/23.
 */
public class MyUtils {
    private static int debugCurrentTimeStamp = 0;

    /**
     * format timeStamp to date str.(use pattern as "yyyy-MM-dd HH:mm:ss z")
     * @param timeStamp
     * @return
     */
    public static String formatTimeStamp(int timeStamp) {
        String pattern = "yyyy-MM-dd HH:mm:ss z";
        return formatTimpStamp(timeStamp, pattern);
    }

    /**
     * format timeStamp to date str, use given pattern
     * @param timeStamp
     * @param pattern
     * @return
     */
    public static String formatTimpStamp(int timeStamp, final String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(timeStamp * 1000L));
    }

    /**
     * Convert current time into int type. used for create_time/update time
     *
     * @param
     * @return timestamp
     */
    public static int getCurrentTimeStamp() {
        if (debugCurrentTimeStamp == 0) {
            return (int) (System.currentTimeMillis() / 1000);
        } else {
            return debugCurrentTimeStamp;
        }
    }

}
