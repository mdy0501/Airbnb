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
import android.widget.Toast;

import com.android.airbnb.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.reservation.Reservation;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements CalendarItem.OnCalendarChangedListener, View.OnClickListener, ITask.oneReservation {

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
    public static String STATUS = NOTHING;

    public static String checkinDate = "";
    public static String checkoutDate = "";

    private List<CalendarItem> items;
    private List<Reservation> reservations;
    private House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        items = new ArrayList<>();
        getCalendarDate();
        getData();
        Loader.getReservation(house.getPk(), this);
//        initView();
//        setOnClick();
//        setCalendar();
    }

    private void getData() {
        Intent intent = getIntent();
        house = intent.getParcelableExtra(DetailHouseActivity.DETAIL_HOUSE);
    }

    private void resetAllCalendar() {
        for (CalendarItem item : items) {
            item.resetAll();
        }
    }

    private void setBookedDates(String checkin, String checkout) {

        String subCheckin = checkin.substring(0, 7);
        String subCheckout = checkout.substring(0, 7);

        int subCheckinDd = Integer.parseInt(checkin.substring(9));
        int subCheckoutDd = Integer.parseInt(checkout.substring(9));

        Log.e("CustomCalendar", "subCheckin : " + subCheckin + ", checkout : " + subCheckout);
        Log.e("CustomCalendar", "subCheckinDd : " + subCheckinDd + ", subCheckoutDd : " + subCheckoutDd);

        for (CalendarItem item : items) {

            String itemKey = (String) item.getTag();

            if ((subCheckin).equals(itemKey) && (subCheckout).equals(itemKey)) {
                for (int i = subCheckinDd; i <= subCheckoutDd; i++) {
                    item.setBooked(item.getCols().get(i));
                }

            } else if ((subCheckin).equals(itemKey) || (subCheckout).equals(itemKey)) {

                if ((subCheckin).equals(itemKey))
                    subCheckinDd = items.indexOf(item);
                else if ((subCheckout).equals(itemKey))
                    subCheckoutDd = items.indexOf(item);

                setCalendarRange(subCheckinDd, subCheckoutDd);
            }
        }
        Log.e("CustomCalendar", "startIndex : " + subCheckinDd + ", endIndex : " + subCheckoutDd);


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
                item.setRanged(0, cols.size() - 1);
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

    private void setOnClick() {
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
        calendarResultTxt.setText(Utils.CalendarUtil.calculatePeriod(checkinDate, checkoutDate) + "박을 선택했습니다.");
        setRange(checkinDate, checkoutDate);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calendar_btn_close:
                onBackPressed();
                break;

            case R.id.calendar_btn_Save:
                checkDate(checkinDate, checkoutDate);
                break;

            case R.id.calendar_delete:
                resetAllCalendar();
                break;
        }
    }

    private void checkDate(String checkinDate, String checkoutDate){
        if (checkinDate.length() == checkoutDate.length()) {
            Intent intent = new Intent();
            intent.putExtra("checkin", checkinDate);
            intent.putExtra("checkout", checkoutDate);
            setResult(200, intent);
            finish();
        } else {
            Toast.makeText(CalendarActivity.this, "날짜를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doOneReservation(List<Reservation> reservations) {
        this.reservations = reservations;
        initView();
        setOnClick();
        setCalendar();

        for (Reservation item : reservations) {
            setBookedDates(item.getCheckin_date(), item.getCheckout_date());
        }
    }
}
