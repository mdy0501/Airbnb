package com.android.airbnb.calendar;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class CalendarData {

    String month = "";
    List<String> days;
    String weekDaysCount;

    public CalendarData(String month, List<String> days){
        this.month = month;
        this.days = days;
    }

    public CalendarData() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public String getWeekDaysCount() {
        return weekDaysCount;
    }

    public void setWeekDaysCount(String weekDaysCount) {
        this.weekDaysCount = weekDaysCount;
    }

    @Override
    public String toString() {
        return "CalendarData{" +
                "month='" + month + '\'' +
                ", days=" + days +
                ", weekDaysCount='" + weekDaysCount + '\'' +
                '}';
    }
}
