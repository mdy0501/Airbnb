package com.android.airbnb.calendar;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class CalendarData {

    private int month = 0;
    private List<Integer> days;
    private int weekDaysCount;
    private int firstWeekDay = 0;

    public CalendarData(int month, List<Integer> days){
        this.month = month;
        this.days = days;
    }

    public CalendarData() {

    }

    public int getFirstWeekDay() {
        return firstWeekDay;
    }

    public void setFirstWeekDay(int firstWeekDay) {
        this.firstWeekDay = firstWeekDay;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public int getWeekDaysCount() {
        return weekDaysCount;
    }

    public void setWeekDaysCount(int weekDaysCount) {
        this.weekDaysCount = weekDaysCount;
    }

    @Override
    public String toString() {
        return "CalendarData{" +
                "month='" + month + '\'' +
                ", days=" + days +
                ", weekDaysCount='" + weekDaysCount + '\'' +
                ", dayOfWeek='" + firstWeekDay + '\'' +
                '}';
    }
}
