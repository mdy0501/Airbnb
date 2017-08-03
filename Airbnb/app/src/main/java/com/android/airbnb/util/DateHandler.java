package com.android.airbnb.util;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public class DateHandler {


    public static String getDateYYYYdd(String date){
        if(date != null) {
            String splited[] = date.split("-");
            return splited[0] + "년 " + splited[1] + "월";
        }
        return "날짜없음";
    }
}
