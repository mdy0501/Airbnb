package com.android.airbnb.calendar;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class Utils {


    public static class CalendarUtil{

        private static Calendar mCalendar = Calendar.getInstance();

        public static int getDaysInMonth(int month, int year) {

            switch (month-1) {
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

                // 2월달은 윤년으로 인해 아래와 같은 코드를 리턴해줘야 한다.
                case Calendar.FEBRUARY:
                    return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 28 : 29;

                default:
                    throw new IllegalArgumentException("Invalid Month");
            }
        }

        public static int getWeekCount(int yyyy, int month) {
            // 시스템 상에서 달력은 0부터 시작한다.
            int maxWeeknumber = 0;
            mCalendar.set(mCalendar.YEAR, yyyy);
            mCalendar.set(mCalendar.DAY_OF_MONTH, 1);
            mCalendar.set(mCalendar.MONTH, month-1);
            maxWeeknumber = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
            Log.d("LOG", "For Month :: " + month + " Num Week :: " + maxWeeknumber);
            return maxWeeknumber;
        }

        public static int getFirstWeekDay(int yyyy, int month){
            mCalendar.set(yyyy, month-1, 1);
            int day_of_week = mCalendar.get(Calendar.DAY_OF_WEEK);
            return day_of_week;
        }
    }

    public static class DateUtil {

        static long now = System.currentTimeMillis();

        public static String getCurrentMonth(){
            SimpleDateFormat sdf = new SimpleDateFormat("M");
            Log.e("Utils", "getCurrentMonth :: sdf " + sdf.toString());
            Log.e("Utils", "getCurrentMonth :: " + sdf.format(new Date(now)));
            return sdf.format(new Date(now));
        }

        public static String getCurrentYear(){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Log.e("Utils", "getCurrentYear :: sdf " + sdf.toString());
            Log.e("Utils", "getCurrentYear :: " + sdf.format(new Date(now)));

            return sdf.format(new Date(now));
        }
    }


}
