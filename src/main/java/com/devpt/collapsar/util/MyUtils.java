package com.devpt.collapsar.util;

/**
 * Created by chenyong on 2016/5/23.
 */
public class MyUtils {
    private static int debugCurrentTimeStamp = 0;



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
