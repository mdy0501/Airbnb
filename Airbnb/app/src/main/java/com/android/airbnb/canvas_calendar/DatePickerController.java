package com.android.airbnb.canvas_calendar;

/**
 * Created by JunHee on 2017. 8. 12..
 */

public interface DatePickerController {

    public abstract int getMaxYear();

    public abstract void onDayOfMonthSelected(int year, int month, int day);

    public abstract void onDateRangeSelected(final SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays);
}
