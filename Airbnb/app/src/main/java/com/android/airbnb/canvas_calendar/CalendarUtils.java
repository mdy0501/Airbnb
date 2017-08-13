package com.android.airbnb.canvas_calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JunHee on 2017. 8. 12..
 */

public class CalendarUtils {

    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    public static String getCurrentYear(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date(timeMillis));
    }

    public static String getCurretMonth(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("M");
        return sdf.format(new Date(timeMillis));
    }

    public static String getCurrentDay(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        return sdf.format(new Date(timeMillis));
    }

    public static int getPastTenYears(long timeMillis) {
        int result = Integer.parseInt(getCurrentDay(timeMillis)) - 10;
        return result;
    }
}
