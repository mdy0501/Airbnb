package com.android.airbnb.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 18..
 */

public class CalendarItem extends LinearLayout {

    private Context context;
    private String year;
    private String month;
    private String yearMonth;
    private List<String> days;
    private List<TextView> cols;
    private List<TextView> selectedTvs;
    private LinearLayout.LayoutParams calendarLayoutParams;
    private LinearLayout.LayoutParams rowParams;
    private LinearLayout.LayoutParams textParams;
    private OnCalendarChangedListener mListener;
    private CalendarData calendarData;
    public static final int CHECK_BOOKING_TAG = R.string.check_booking_tag;
    public static final int DATE_TAG = R.string.date_tag;
    private int startIndex;

    public CalendarItem(CalendarData calendarData, Context context, OnCalendarChangedListener mListener) {
        super(context);
        this.context = context;
        this.year = calendarData.getYear();
        this.month = calendarData.getMonth();
        this.days = calendarData.getDays();
        this.mListener = mListener;
        this.yearMonth = year + "-" + Utils.CalendarUtil.getFormattedForCal(month);
        this.calendarData = calendarData;
        this.startIndex = calendarData.getFirstWeekDay() - 1;
        this.cols = new ArrayList<>();
        selectedTvs = new ArrayList<>();
        setOrientation(VERTICAL);
        setCalendarItemParams(this);
        initView();
    }

    private void setCalendarItemParams(CalendarItem layout) {
        calendarLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        calendarLayoutParams.setMargins(0, 0, 0, 30);
        layout.setLayoutParams(calendarLayoutParams);
    }

    private void setRowParams(LinearLayout row) {
        rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rowParams.height = 150;
        rowParams.gravity = Gravity.CENTER;
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(rowParams);
    }

    private void setTextViewParams(TextView tv) {
        textParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        textParams.weight = 1;
        textParams.gravity = Gravity.CENTER;
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(textParams);
    }

    // 메소드 분리 및 정리하기
    private void initView() {
        // 년월 세팅
        TextView title = new TextView(context);
        title.setText(String.format("%s년 %s월", year, month));
        title.setTextColor(Color.BLACK);
        title.setTextSize(30f);
        addView(title);

        // 일 세팅
        LinearLayout row = null;
        for (int i = 0; i < days.size(); i++) {
            if (((i != 0) && i % 7 == 0 || i == 0)) {
                row = new LinearLayout(context);
                row.setOrientation(HORIZONTAL);
                setRowParams(row);
                addView(row);
            }

            final TextView col = new TextView(context);
            setTextViewParams(col);
            col.setText(days.get(i) + "");
            col.setBackgroundResource(R.color.white);
            col.setTextColor(Color.BLACK);
            col.setTextSize(20f);
            if (days.get(i) != "" || !days.get(i).equals("")) {
                col.setTag(DATE_TAG, year + "-" + Utils.CalendarUtil.getFormattedForCal(month) +
                        "-" + Utils.CalendarUtil.getFormattedForCal(days.get(i) + ""));
                col.setTag(CHECK_BOOKING_TAG, false);
            }
            col.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = (String) v.getTag(DATE_TAG);
                    if (key != null) {
                        Toast.makeText(v.getContext(), key, Toast.LENGTH_SHORT).show();
                    }
                    setCompleted(key, CalendarActivity.STATUS, col);
                }
            });

            if (!days.get(i).equals("") && days.get(i) != "") {
                cols.add(col);
            }
            row.addView(col);
        }
    }

    public void resetAll() {
        for (int i = 0; i < cols.size(); i++) {
            TextView tv = cols.get(i);
            Log.e("CalendarItem", "isBooked ? " + (boolean) tv.getTag(CHECK_BOOKING_TAG));
            if ((boolean)tv.getTag(CHECK_BOOKING_TAG) == false) {
                tv.setText(days.get(i + startIndex));
                tv.setTextColor(Color.BLACK);
                tv.setTypeface(null, Typeface.NORMAL);
                tv.setBackgroundResource(R.color.white);
                selectedTvs.clear();
            }
        }
    }

    public void setCompleted(String date, String status, TextView tv) {
        switch (status) {
            // 1. 초기 아무것도 선택하지 않았을 때
            case CalendarActivity.NOTHING:
                Log.e("JunheeCalendarItem", " 1 ==================");
                CalendarActivity.STATUS = CalendarActivity.CHECK_IN;
                CalendarActivity.checkinDate = date;
                Log.e("JunheeCalendarItem", "check in : " + CalendarActivity.checkinDate + ", check out : " + CalendarActivity.checkoutDate);
                setSelected(tv);
                selectedTvs.add(tv);
                mListener.checkInChanged(date);
                break;

            // 2. CHECK IN date가 선택된 상황일 때
            case CalendarActivity.CHECK_IN:
                Log.e("JunheeCalendarItem", " 2 ==================");
                CalendarActivity.STATUS = CalendarActivity.CHECK_OUT;
                CalendarActivity.checkoutDate = date;
                Log.e("JunheeCalendarItem", "check in : " + CalendarActivity.checkinDate + ", check out : " + CalendarActivity.checkoutDate);
                setSelected(tv);
                selectedTvs.add(tv);
                mListener.checkOutChanged(date);
                break;

            // 3. CHECK OUT date가 선택된 상황일 때
            case CalendarActivity.CHECK_OUT:
                Log.e("JunheeCalendarItem", " 3 ==================");
                mListener.resetAll();
                CalendarActivity.STATUS = CalendarActivity.CHECK_IN;
                CalendarActivity.checkinDate = date;
                CalendarActivity.checkoutDate = "";
                Log.e("JunheeCalendarItem", "check in : " + CalendarActivity.checkinDate + ", check out : " + CalendarActivity.checkoutDate);
                setSelected(tv);
                selectedTvs.add(tv);
                mListener.checkInChanged(date);
                break;
        }
    }

    // booking 완료된 날짜 속성값 변경하는 메소드
    public void setBooked(TextView tv) {
        if (tv.getText() != "" || !"".equals(tv.getText())) {
            tv.setTag(CHECK_BOOKING_TAG, true);
            tv.setClickable(false);
            Log.e("CalendarItem", "CHECK_BOOKING_TAG : " + (boolean) tv.getTag(CHECK_BOOKING_TAG));
            tv.setBackgroundColor(getResources().getColor(R.color.white));
            tv.setTextColor(Color.GRAY);
            tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv.setTypeface(null, Typeface.BOLD_ITALIC);
        }
    }

    public void setRanged(int start, int end) {
        // status에 따라 분기하여 다른 작업을 한다.
        // TODO for문도 메소드 안에 넣을 수 있도록 메소드를 정리한다.
        if (CalendarActivity.STATUS == CalendarActivity.NOTHING) {
            for (int i = start; i <= end; i++) {
                Log.e("CalendarItem", "setRanged : nothing ================= do ");
                setBooked(cols.get(i));
            }
        } else {
            Log.e("CalendarItem", "setRanged : check in & check out ================= do ");
            for (int i = start; i <= end; i++) {
                setSelected(cols.get(i));
            }
        }
    }

    // checkin checkout 선택 시, 속성값 변경하는 메소드
    // TODO for문도 메소드 안에 넣을 수 있도록 메소드를 정리한다.
    public void setSelected(TextView tv) {
        if (tv.getText() != "" || !tv.getText().equals("")) {
            tv.setBackgroundColor(getResources().getColor(R.color.selected_day_background));
            tv.setTextColor(Color.WHITE);
            tv.setTypeface(null, Typeface.BOLD);
        }
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public List<TextView> getCols() {
        return cols;
    }

    public void setCols(List<TextView> cols) {
        this.cols = cols;
    }

    public List<TextView> getSelectedTvs() {
        return selectedTvs;
    }

    public void setSelectedTvs(List<TextView> selectedTvs) {
        this.selectedTvs = selectedTvs;
    }

    public interface OnCalendarChangedListener {
        public void resetAll();

        public void checkInChanged(String date);

        public void checkOutChanged(String date);
    }
}
