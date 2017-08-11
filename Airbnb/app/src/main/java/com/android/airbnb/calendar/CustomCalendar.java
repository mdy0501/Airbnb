package com.android.airbnb.calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;

import java.util.ArrayList;
import java.util.List;

public class CustomCalendar extends AppCompatActivity implements View.OnClickListener {

    private ImageView calendarBtnClose;
    private TextView calendarDelete;
    private TextView checkinDateTxt;
    private TextView checkoutDateTxt;
    private RecyclerView calendarList;
    private Button calendarBtnSave;
    private CalendarData data;
    private List<CalendarData> calendarDatas;
    int result = 0;
    private CustomCalendarAdapter calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_calendar);
        calendarDatas = new ArrayList<>();
        setDummy();
        initView();
        setClikable();
        setAdapter();
    }

    private void setDummy() {

        List<String> days = new ArrayList<>();
        int dayCount = 0;
        int currentYear = Integer.parseInt(Utils.DateUtil.getCurrentYear());
        for (int i = 1; i <= 12; i++) {
            data = new CalendarData();
            dayCount = Utils.CalendarUtil.getDaysInMonth(i, currentYear);
            Log.e("CustomCalendar", dayCount + "");
            days.clear();
            for (int j = 1; j <= dayCount; j++) {
                days.add(j + "");
                Log.e("CustomCalendar", "add :: " + j + "");
            }
            data.setDays(days);
            data.setMonth(i + "");
            data.setWeekDaysCount(Utils.CalendarUtil.getWeekCount(currentYear, i) + "");
            Log.e("CustomCalendar", data.toString());
            calendarDatas.add(data);
        }

    }

    private void setAdapter() {
        calendarAdapter = new CustomCalendarAdapter(calendarDatas, this);
        calendarList.setAdapter(calendarAdapter);
        calendarList.setLayoutManager(new LinearLayoutManager(this));
        calendarList.scrollToPosition(Integer.parseInt(Utils.DateUtil.getCurrentMonth())-1);
        calendarList.setItemViewCacheSize(5);
    }

    private void initView() {
        calendarBtnClose = (ImageView) findViewById(R.id.calendar_btn_close);
        calendarDelete = (TextView) findViewById(R.id.calendar_delete);
        checkinDateTxt = (TextView) findViewById(R.id.checkin_date_txt);
        checkoutDateTxt = (TextView) findViewById(R.id.checkout_date_txt);
        calendarList = (RecyclerView) findViewById(R.id.calendar_list);
        calendarBtnSave = (Button) findViewById(R.id.calendar_btn_Save);
    }

    private void setClikable() {
        calendarBtnClose.setClickable(true);
        calendarDelete.setClickable(true);
        calendarDelete.setClickable(true);

        calendarBtnClose.setOnClickListener(this);
        calendarBtnSave.setOnClickListener(this);
        calendarDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calendar_btn_close:
                Toast.makeText(v.getContext(), "close", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;

            case R.id.calendar_btn_Save:
                Toast.makeText(v.getContext(), "save", Toast.LENGTH_SHORT).show();
                break;

            case R.id.calendar_delete:
                Toast.makeText(v.getContext(), "del", Toast.LENGTH_SHORT).show();
        }
    }
}
