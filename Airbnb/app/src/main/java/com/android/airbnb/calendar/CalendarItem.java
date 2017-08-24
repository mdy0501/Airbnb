package com.android.airbnb.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        setCalendarItemParams(this);
        setTitle(year, month);
        setDays();
    }

    // CalendarItem의 1달 달력의 가장 외곽 Layout의 설정값을 셋팅하는 메소드
    private void setCalendarItemParams(CalendarItem layout) {
        calendarLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        calendarLayoutParams.setMargins(0, 0, 0, 30);
        layout.setLayoutParams(calendarLayoutParams);
    }

    // CalendarItem의 1주씩의 Layout의 설정값을 셋팅하는 메소드
    private void setRowParams(LinearLayout row) {
        rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rowParams.height = 150;
        rowParams.gravity = Gravity.CENTER;
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(rowParams);
    }

    // CalendarItem의 하루하루의 Layout의 설정값을 셋팅하는 메소드
    private void setTextViewParams(TextView tv) {
        textParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        textParams.weight = 1;
        textParams.gravity = Gravity.CENTER;
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(textParams);
    }

    // CalendarItem의 title을 셋팅하는 메소드
    private void setTitle(String year, String month) {
        TextView title = new TextView(context);
        title.setText(String.format("%s년 %s월", year, month));
        title.setTextColor(Color.BLACK);
        title.setTextSize(30f);
        addView(title);
    }

    // CalendarItem의 Rows 하루하루를 셋팅하는 메소드
    private void setDays() {

        LinearLayout row = null;

        // 1. 1주차 날짜를 적용하기 위해 LinearLayout을 new한다.
        for (int i = 0; i < days.size(); i++) {
            // 1.1. 아래 조건문의 경우 한주 단위로 row를 생성하기 위해 작성했다.
            if (((i != 0) && i % 7 == 0 || i == 0)) {
                row = new LinearLayout(context);
                row.setOrientation(HORIZONTAL);
                setRowParams(row);

                // 2. 완성된 row를 parent view에 addView(); 한다.
                addView(row);
            }

            final TextView col = new TextView(context);

            // 2. TextView를 셋팅한다.
            setTextViewParams(col);
            col.setText(days.get(i) + "");
            col.setBackgroundResource(R.color.white);
            col.setTextColor(Color.BLACK);
            col.setTextSize(20f);

            // 3. 추후 날짜 검색을 쉽게 하기 위해 tag를 달아준다.
            // 3.1. 날짜가 없는 것("")은 tag를 달지 않기 위해 아래와 같은 조건문을 작성했다.
            if (days.get(i) != "" || !days.get(i).equals("")) {
                col.setTag(DATE_TAG, year + "-" + Utils.CalendarUtil.getFormattedForCal(month) +
                        "-" + Utils.CalendarUtil.getFormattedForCal(days.get(i) + ""));
                col.setTag(CHECK_BOOKING_TAG, false);
            }

            // 3.2. 달력을 그리기 위해 공란("")으로 둔 textView의 경우, 온클릭 이벤트가 발생되지 않도록 설정했다.
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

    public void setCompleted(String date, String status, TextView tv) {
        switch (status) {
            // 1. 초기 아무것도 선택하지 않았을 때
            case CalendarActivity.NOTHING:
                CalendarActivity.STATUS = CalendarActivity.CHECK_IN;
                CalendarActivity.checkinDate = date;
                setSelected(tv);
                selectedTvs.add(tv);
                mListener.checkInChanged(date);
                break;

            // 2. CHECK IN date가 선택된 상황일 때
            case CalendarActivity.CHECK_IN:
                CalendarActivity.STATUS = CalendarActivity.CHECK_OUT;
                CalendarActivity.checkoutDate = date;
                setSelected(tv);
                selectedTvs.add(tv);
                mListener.checkOutChanged(date);
                break;

            // 3. CHECK OUT date가 선택된 상황일 때
            case CalendarActivity.CHECK_OUT:
                mListener.resetAll();
                CalendarActivity.STATUS = CalendarActivity.CHECK_IN;
                CalendarActivity.checkinDate = date;
                CalendarActivity.checkoutDate = "";
                setSelected(tv);
                selectedTvs.add(tv);
                mListener.checkInChanged(date);
                break;
        }
    }

    // 예약된 날짜를 제외하고 1개월의 캘린더를 초기화하는 메소드
    public void resetAll() {
        for (int i = 0; i < cols.size(); i++) {
            TextView tv = cols.get(i);
            if ((boolean) tv.getTag(CHECK_BOOKING_TAG) == false) {
                tv.setText(days.get(i + startIndex));
                tv.setTextColor(Color.BLACK);
                tv.setTypeface(null, Typeface.NORMAL);
                tv.setBackgroundResource(R.color.white);
                selectedTvs.clear();
            }
        }
    }

    // reservation 완료된 날짜 속성값 변경하는 메소드
    public void setBooked(TextView tv) {
        if (tv.getText() != "" || !"".equals(tv.getText())) {
            tv.setTag(CHECK_BOOKING_TAG, true);
            tv.setClickable(false);
            tv.setBackgroundColor(getResources().getColor(R.color.white));
            tv.setTextColor(Color.GRAY);
            tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv.setTypeface(null, Typeface.BOLD_ITALIC);
        }
    }

    // check in & check out 선택 완료 시, 해당 기간 내의 날짜 resource 바꾸는 메소드
    public void setRanged(int start, int end) {
        // status에 따라 다른 작업을 하도록 분기한다
        if (CalendarActivity.STATUS == CalendarActivity.NOTHING) {
            for (int i = start; i <= end; i++) {
                setBooked(cols.get(i));
            }
        } else {
            for (int i = start; i <= end; i++) {
                setSelected(cols.get(i));
            }
        }
    }

    // checkin checkout 선택 시, 속성값 변경하는 메소드
    public void setSelected(TextView tv) {
        if (tv.getText() != "" || !tv.getText().equals("")) {
            tv.setBackgroundColor(getResources().getColor(R.color.selected_day_background));
            tv.setTextColor(Color.WHITE);
            tv.setTypeface(null, Typeface.BOLD);
        }
    }
    public List<TextView> getCols() {
        return cols;
    }

    public List<TextView> getSelectedTvs() {
        return selectedTvs;
    }

    public interface OnCalendarChangedListener {
        public void resetAll();
        public void checkInChanged(String date);
        public void checkOutChanged(String date);
    }
}
