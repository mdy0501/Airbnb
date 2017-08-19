package com.android.airbnb.calendar;

import android.content.Context;
import android.graphics.Color;
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
        Log.e("sdfsf", "year month : " + yearMonth);
        initView();
    }

    private void setCalendarItemParams(CalendarItem layout) {
        calendarLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(calendarLayoutParams);
    }

    private void setRowParams(LinearLayout row) {
        rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(rowParams);
    }

    private void setTextViewParams(TextView tv, String text) {
        textParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        textParams.weight = 1;
        textParams.gravity = Gravity.CENTER;
        tv.setTextAlignment(TEXT_ALIGNMENT_VIEW_END);
        tv.setLayoutParams(textParams);
    }

    // 메소드 분리 및 정리하기
    private void initView() {
        // 년월 세팅
        TextView title = new TextView(context);
        title.setText(String.format("%s년 %s월", year, month));
        title.setTextColor(Color.BLACK);
        title.setTextSize(20f);
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
            setTextViewParams(col, days.get(i));
            col.setText(days.get(i) + "");
            col.setBackgroundResource(R.color.white);
            col.setTextColor(Color.BLACK);
            col.setTextSize(20f);
            if (days.get(i) != "" || !days.get(i).equals("")) {
                col.setTag(year + "-" + Utils.CalendarUtil.getFormattedForCal(month) +
                        "-" + Utils.CalendarUtil.getFormattedForCal(days.get(i) + ""));
            }
            col.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = (String) v.getTag();
                    if (key != null) {
                        Toast.makeText(v.getContext(), key, Toast.LENGTH_SHORT).show();
                    }
                    setCompleted(key, CalendarActivity.STAUS, col);
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
            tv.setText(days.get(i + startIndex));
            tv.setTextColor(Color.BLACK);
            tv.setTypeface(null, Typeface.NORMAL);
            tv.setBackgroundResource(R.color.white);
            selectedTvs.clear();
        }
    }

    public void setCompleted(String date, String status, TextView tv) {
        switch (status) {
            case CalendarActivity.NOTHING:
                Log.e("JunheeCalendarItem", " 1 ==================");
                CalendarActivity.STAUS = CalendarActivity.CHECK_IN;
                CalendarActivity.checkinDate = date;
                Log.e("JunheeCalendarItem", "check in : " + CalendarActivity.checkinDate + ", check out : " + CalendarActivity.checkoutDate);
                setSelected(tv);
                selectedTvs.add(tv);
                mListener.checkInChanged(date);
                break;

            case CalendarActivity.CHECK_IN:
                Log.e("JunheeCalendarItem", " 2 ==================");
                CalendarActivity.STAUS = CalendarActivity.CHECK_OUT;
                CalendarActivity.checkoutDate = date;
                Log.e("JunheeCalendarItem", "check in : " + CalendarActivity.checkinDate + ", check out : " + CalendarActivity.checkoutDate);
                setSelected(tv);
                selectedTvs.add(tv);
                mListener.checkOutChanged(date);
                break;

            case CalendarActivity.CHECK_OUT:
                Log.e("JunheeCalendarItem", " 3 ==================");
                mListener.resetAll();
                CalendarActivity.STAUS = CalendarActivity.CHECK_IN;
                CalendarActivity.checkinDate = date;
                CalendarActivity.checkoutDate = "";
                Log.e("JunheeCalendarItem", "check in : " + CalendarActivity.checkinDate + ", check out : " + CalendarActivity.checkoutDate);
                setSelected(tv);
                selectedTvs.add(tv);

                break;
        }
    }

    public void setRanged(int start, int end){
        for(int i= start; i <= end; i++){
            setSelected(cols.get(i));
        }
    }

    public void setSelected(TextView tv) {
        if(tv.getText() != "" || !tv.getText().equals("")){
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
