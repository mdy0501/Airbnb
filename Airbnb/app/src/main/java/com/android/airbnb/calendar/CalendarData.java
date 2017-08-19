package com.android.airbnb.calendar;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class CalendarData {

    private String year = "";
    private String month = "";
    private List<String> days;
    private int weekDaysCount;
    private int firstWeekDay = 0;

    public CalendarData(String month, List<String> days){
        this.month = month;
        this.days = days;
    }

    public CalendarData() {

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getFirstWeekDay() {
        return firstWeekDay;
    }

    public void setFirstWeekDay(int firstWeekDay) {
        this.firstWeekDay = firstWeekDay;
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

    public int getWeekDaysCount() {
        return weekDaysCount;
    }

    public void setWeekDaysCount(int weekDaysCount) {
        this.weekDaysCount = weekDaysCount;
    }

    @Override
    public String toString() {
        return "CalendarData{" +
                "month=" + month +
                ", year=" + year +
                ", days=" + days +
                ", weekDaysCount=" + weekDaysCount +
                ", firstWeekDay=" + firstWeekDay +
                '}';
    }
}
