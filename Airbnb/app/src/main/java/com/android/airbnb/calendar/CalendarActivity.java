package com.android.airbnb.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.airbnb.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.reservation.Reservation;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.Loader;
import com.google.gson.Gson;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements CalendarItem.OnCalendarChangedListener, View.OnClickListener {

    private List<CalendarData> calendarDatas;
    private ImageView calendarBtnClose;
    private TextView calendarDelete;
    private ConstraintLayout checkinCheckout;
    private TextView checkinDateTxt;
    private TextView checkoutDateTxt;
    private ConstraintLayout calendarSaveLayout;
    private TextView calendarResultTxt;
    private Button calendarBtnSave;
    private ScrollView calendarList;
    private LinearLayout calendarContainer;
    private LinearLayout.LayoutParams params;
    public static final String CHECK_IN = "com.android.airbnb.calendar.CalendarData.checkin";
    public static final String CHECK_OUT = "com.android.airbnb.calendar.CalendarData.checkout";
    public static final String NOTHING = "com.android.airbnb.calendar.CalendarData.nothing";
    public static String STAUS = NOTHING;

    public static String checkinDate = "";
    public static String checkoutDate = "";

    private List<CalendarItem> items;
    private House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        items = new ArrayList<>();
        getCalendarDate();
        getData();
        initView();
        setOnClick();
        setCalendar();
    }

    private void getData(){
        Intent intent = getIntent();
        house = intent.getParcelableExtra(DetailHouseActivity.DETAIL_HOUSE);
    }

    private void resetAllCalendar() {
        for (CalendarItem item : items) {
            item.resetAll();
        }
    }

    private void setRange(String startDate, String endDate) {

        String subCheckin = startDate.substring(0, 7);
        String subCheckout = endDate.substring(0, 7);

        int startIndex = 0;
        int endIndex = 0;

        for (CalendarItem item : items) {
            String itemKey = (String) item.getTag();
            Log.e("CustomCalendar", "subCheckin : " + subCheckin + ", checkout : " + subCheckout + ", item tag : " + (String) item.getTag());

            if ((subCheckin).equals(itemKey) && (subCheckout).equals(itemKey)) {
                int startTextview = item.getCols().indexOf(item.getSelectedTvs().get(0));
                int endTextView = item.getCols().indexOf(item.getSelectedTvs().get(1));

                for (int i = startTextview; i <= endTextView; i++) {
                    item.setSelected(item.getCols().get(i));
                }
            } else if ((subCheckin).equals(itemKey) || (subCheckout).equals(itemKey)) {
                if ((subCheckin).equals(itemKey))
                    startIndex = items.indexOf(item);
                else if ((subCheckout).equals(itemKey))
                    endIndex = items.indexOf(item);

                setCalendarRange(startIndex, endIndex);
            }
        }
        Log.e("CustomCalendar", "startIndex : " + startIndex + ", endIndex : " + endIndex);
    }

    private void setCalendarRange(int startIndex, int endIndex) {

        for (int i = startIndex; i <= endIndex; i++) {

            CalendarItem item = items.get(i);
            List<TextView> cols = item.getCols();
            List<TextView> selectedTvs = item.getSelectedTvs();

            if (i == startIndex) {
                item.setRanged(cols.indexOf(item.getSelectedTvs().get(0)), cols.size() - 1);
            } else if (i == endIndex) {
                item.setRanged(0, cols.indexOf(selectedTvs.get(0)));
            } else {
                item.setRanged(0, cols.size()-1);
            }
        }
    }

    private void getCalendarDate() {
        calendarDatas = Utils.CalendarUtil.getCalendarData();
        Log.e("CustomCalendar", "size : " + calendarDatas.size());
    }

    private void setCalendar() {
        for (int i = 0; i < calendarDatas.size(); i++) {
            CalendarData calendarData = calendarDatas.get(i);
            Log.e("CalendarActivity", "month : " + calendarData.getMonth());
            Log.e("CalendarActivity", " start : " + calendarData.getFirstWeekDay());
            CalendarItem calendarItem = new CalendarItem(calendarData, this, this);

            // back-end에서 넘어오는 날짜 데이터와 형식을 맞춰 객체에 .setTag를 한다.
            String tag = calendarData.getYear() + "-" + Utils.CalendarUtil.getFormattedForCal(calendarData.getMonth());
            calendarItem.setTag(tag);
            Log.e("CustomCalendar", "tag : " + tag);
            items.add(calendarItem);
            calendarContainer.addView(calendarItem);
        }
    }

    private void initView() {
        calendarBtnClose = (ImageView) findViewById(R.id.calendar_btn_close);
        calendarDelete = (TextView) findViewById(R.id.calendar_delete);
        checkinCheckout = (ConstraintLayout) findViewById(R.id.checkin_checkout);
        checkinDateTxt = (TextView) findViewById(R.id.checkin_date_txt);
        checkoutDateTxt = (TextView) findViewById(R.id.checkout_date_txt);
        calendarSaveLayout = (ConstraintLayout) findViewById(R.id.calendar_save_layout);
        calendarResultTxt = (TextView) findViewById(R.id.calendar_result_txt);
        calendarBtnSave = (Button) findViewById(R.id.calendar_btn_Save);
        calendarList = (ScrollView) findViewById(R.id.calendar_list);
        calendarContainer = (LinearLayout) findViewById(R.id.calendar_container);
    }

    private void setOnClick(){
        calendarBtnClose.setClickable(true);
        calendarBtnClose.setOnClickListener(this);

        calendarDelete.setClickable(true);
        calendarDelete.setOnClickListener(this);

        calendarBtnSave.setClickable(true);
        calendarBtnSave.setOnClickListener(this);
    }

    @Override
    public void resetAll() {
        resetAllCalendar();
    }

    @Override
    public void checkInChanged(String date) {
        checkinDateTxt.setText("Check in" + "\n" + date);
    }

    @Override
    public void checkOutChanged(String date) {
        checkoutDateTxt.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        checkoutDateTxt.setText("Check out" + "\n" + date);
        calendarResultTxt.setText(calculatePeriod(checkinDate, checkoutDate));
        setRange(checkinDate, checkoutDate);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private String calculatePeriod(String beginDate, String endDate) {
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
        return result + "박을 선택했습니다.";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calendar_btn_close:
                onBackPressed();
                break;

            case R.id.calendar_btn_Save:
                Log.e("CalendarActivity", "house pk : " + house.getPk());
                Reservation reservation = new Reservation();
                reservation.setCheckout_date(checkoutDate);
                reservation.setCheckin_date(checkinDate);
                Gson gson = new Gson();
                String jsonString = gson.toJson(reservation);
                String queryString = "?house=" + house.getPk();
                Log.e("CalendarAct", "query : " + queryString);
                try {
                    Loader.postReservation("Token" + PreferenceUtil.getToken(this), queryString, checkinDate, checkoutDate);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.calendar_delete:
                resetAllCalendar();
                break;
        }
    }

}
