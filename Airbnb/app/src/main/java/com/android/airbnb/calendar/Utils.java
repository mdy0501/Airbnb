package com.android.airbnb.calendar;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class Utils {
    public static class CalendarUtil {

        private static Calendar mCalendar = Calendar.getInstance();

        public static int getDaysInMonth(int year, int month) {

            switch (month - 1) {
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
                    return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;

                default:
                    throw new IllegalArgumentException("Invalid Month");
            }
        }

        public static String formatNum(int key) {
            String formatted = key + "";
            if (formatted.length() != 2) {
                formatted = "0" + key;
            }
            return formatted;
        }

        public static List<CalendarData> getCalendarData() {

            List<CalendarData> calendarDatas = new ArrayList<>();
            List<String> days;
            CalendarData calendarData;
            int dayCount = 0;
            int currentYear = Integer.parseInt(Utils.DateUtil.getCurrentYear());
            int currentMonth = Integer.parseInt(Utils.DateUtil.getCurrentMonth());
            Log.e("CustomCalendar", "current month : " + currentMonth);

            int tempMonth = 0;
            // Utils로 추후에 빼기
            // 12월 넘을 경우, 년도 ++ 처리 및 월 reset
            for (int i = currentMonth; i <= currentMonth + 11; i++) {
                calendarData = new CalendarData();
                tempMonth = i;
                if (i > 12) {
                    tempMonth = i - 12;
                    if (i % 12 == 1) {
                        currentYear++;
                    }
                }
                days = new ArrayList<>();
                dayCount = Utils.CalendarUtil.getDaysInMonth(currentYear, tempMonth);
                calendarData.setFirstWeekDay(Utils.CalendarUtil.getFirstWeekDay(currentYear, tempMonth));

                for (int k = 1; k < calendarData.getFirstWeekDay(); k++) {
                    days.add("");
                }

                for (int j = 1; j <= dayCount; j++) {
                    days.add(j + "");
                }

                if(days.size()%7 != 0){
                    int temp = days.size()%7;
                    Log.e("utls", "temp : " + temp);
                    for(int k=0; k < 7-temp; k++){
                        days.add("");
                    }
                }

                calendarData.setYear(currentYear + "");
                calendarData.setDays(days);
                calendarData.setMonth(tempMonth + "");
                calendarData.setWeekDaysCount(Utils.CalendarUtil.getWeekCount(currentYear, tempMonth));
                Log.e("Utils", calendarData.toString());
                String formattedMonth = ((tempMonth + "").length() == 2) ? tempMonth + "" : "0" + tempMonth ;
                String key = currentYear + "-" + formattedMonth;
                calendarDatas.add(calendarData);
                Log.e("Utils", "month :: " + key + ", data size :: " + calendarDatas.size());
            }
            return calendarDatas;
        }

        public static int getWeekCount(int yyyy, int month) {
            // 시스템 상에서 달력은 0부터 시작한다.
            int maxWeeknumber = 0;
            mCalendar.set(mCalendar.YEAR, yyyy);
            mCalendar.set(mCalendar.DAY_OF_MONTH, 1);
            mCalendar.set(mCalendar.MONTH, month - 1);
            maxWeeknumber = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
            Log.d("LOG", "For Month :: " + month + " Num Week :: " + maxWeeknumber);
            return maxWeeknumber;
        }

        public static int getFirstWeekDay(int yyyy, int month) {
            // 특정 년, 월의 1일의 요일을 구한다.
            // [1 : Sunday] ~ [7 : Saturday]
            mCalendar.set(yyyy, month - 1, 1);
            int firstDayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
            return firstDayOfWeek;
        }

        public static String getFormattedForCal(String date) {
            String result = date;
            if (date.length() != 2)
                return "0" + date;
            else
                return result;
            }

        public static long calculatePeriod(String beginDate, String endDate) {
            long result = 0;

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date formattedBegin = sdf.parse(beginDate);
                Date formattedEnd = sdf.parse(endDate);

                // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
                long diff = formattedEnd.getTime() - formattedBegin.getTime();
                result = diff / (24 * 60 * 60 * 1000);
                System.out.println("날짜차이=" + result);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return result;
        }
        }



        public static class DateUtil {

            static long now = System.currentTimeMillis();

            public static String getCurrentMonth() {
                SimpleDateFormat sdf = new SimpleDateFormat("M");
                Log.e("Utils", "getCurrentMonth :: sdf " + sdf.toString());
                Log.e("Utils", "getCurrentMonth :: " + sdf.format(new Date(now)));
                return sdf.format(new Date(now));
            }

            public static String getCurrentYear() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Log.e("Utils", "getCurrentYear :: sdf " + sdf.toString());
                Log.e("Utils", "getCurrentYear :: " + sdf.format(new Date(now)));

                return sdf.format(new Date(now));
            }
        }
    }
