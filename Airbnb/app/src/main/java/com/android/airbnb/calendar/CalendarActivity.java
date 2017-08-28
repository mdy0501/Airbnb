package com.android.airbnb.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.detailActivity.DetailHouseActivity;
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
    private List<Reservation> reservations = null;
    private House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        items = new ArrayList<>();
        initView();
        getCalendarDate();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        house = (House) intent.getParcelableExtra(DetailHouseActivity.DETAIL_HOUSE);
    }

    private void resetAllCalendar() {
        for (CalendarItem item : items) {
            item.resetAll();
        }
    }

    // 생성 후, onResume 될 때마다 update된 reservation data 서버로부터 Reservation list를 request한다.
    @Override
    protected void onResume() {
        super.onResume();
        Loader.getReservation(house.getPk(), this);
    }

    private void setBookedDates(String checkin, String checkout) {

        String subCheckin = checkin.substring(0, 7);
        String subCheckout = checkout.substring(0, 7);

        int subCheckinDd = Integer.parseInt(checkin.substring(8));
        int subCheckoutDd = Integer.parseInt(checkout.substring(8));

        for (CalendarItem item : items) {

            String itemKey = (String) item.getTag();

            if ((subCheckin).equals(itemKey) && (subCheckout).equals(itemKey)) {
                for (int i = subCheckinDd - 1; i < subCheckoutDd; i++) {
                    item.setBooked(item.getCols().get(i));
                }

            } else if ((subCheckin).equals(itemKey) || (subCheckout).equals(itemKey)) {
                int checkInDate = 0;
                int checkOutDate = 0;

                if ((subCheckin).equals(itemKey))
                    checkInDate = items.indexOf(item);
                else if ((subCheckout).equals(itemKey))
                    checkOutDate = items.indexOf(item);

                setBookedRange(checkInDate, checkOutDate);
            }
        }
    }

    private void setBookedRange(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {

            CalendarItem item = items.get(i);
            List<TextView> cols = item.getCols();

            if (i == startIndex) {
                item.setRanged(cols.indexOf(cols.get(startIndex)), cols.size() - 1);
            } else if (i == endIndex) {
                item.setRanged(0, cols.indexOf(cols.get(endIndex)));
            } else {
                item.setRanged(0, cols.size() - 1);
            }
        }
    }

    // check in 날짜와 check out 날짜 선택 시, 날짜 범위 체크하는 메소드
    private void setRange(String startDate, String endDate) {

        // 'yyyy-MM-dd' 형태로 넘어오는 String data를 잘라서 분기에 사용한다.
        String subCheckin = startDate.substring(0, 7);
        String subCheckout = endDate.substring(0, 7);

        int startIndex = 0;
        int endIndex = 0;

        for (CalendarItem item : items) {
            String itemTag = (String) item.getTag();

            // 1. checkIn date와 checkOut date가 같은 달에 있을 경우
            if ((subCheckin).equals(itemTag) && (subCheckout).equals(itemTag)) {
                // 1.1. CalendarItem 마다 전체 날짜 holder를 List에 저장했다.
                // 1.2. 해당되는 indext를 찾아 사용자가 선택한 표기하도록 한다.
                int startTextview = item.getCols().indexOf(item.getSelectedTvs().get(0));
                int endTextView = item.getCols().indexOf(item.getSelectedTvs().get(1));

                for (int i = startTextview; i <= endTextView; i++) {
                    item.setSelected(item.getCols().get(i));
                }

            // 2. checkIn date와 checkout date가 다른 달에 있을 경우
            } else if ((subCheckin).equals(itemTag) || (subCheckout).equals(itemTag)) {

                // 2.1. itemTag와 subCheckin을 비교한다.
                // 2.2. 분기별로 걸러진 index를 각각의 item index를 startIndex와 endIndex에 저장한다.
                if ((subCheckin).equals(itemTag))
                    startIndex = items.indexOf(item);
                else if ((subCheckout).equals(itemTag))
                    endIndex = items.indexOf(item);

                // 2.3. 위에서 찾은 index로 아래 작업을 수행한다.
                setCalendarRange(startIndex, endIndex);
            }
        }
    }

    private void setCalendarRange(int startIndex, int endIndex) {

        for (int i = startIndex; i <= endIndex; i++) {

            CalendarItem item = items.get(i);
            List<TextView> cols = item.getCols();

            List<TextView> selectedTvs = item.getSelectedTvs();

                // 파라미터로 넘겨 받은 index의 경우 선택된 index가 무조건 1개 존재한다.
                // 1. checkin data가 있는 월의 경우, checkindata 월부터 말일까지 선택되면 된다.
            if (i == startIndex) {
                item.setRanged(cols.indexOf(selectedTvs.get(0)), cols.size() - 1);
            } else if (i == endIndex) {
                // 2. checkOut data가 있는 월의 경우, checkOut 일부터 1일까지 선택되면 된다.
                item.setRanged(0, cols.indexOf(selectedTvs.get(0)));
            } else {

                // 3. 위의 월 사이에 있는 월의 경우, 해당 월 전체가 선택되면 된다.
                item.setRanged(0, cols.size() - 1);
            }
        }
    }


    private void setCalendar() {
        if (items.size() > 0)
            items.clear();
        for (int i = 0; i < calendarDatas.size(); i++) {
            CalendarData calendarData = calendarDatas.get(i);
            CalendarItem calendarItem = new CalendarItem(calendarData, this, this);

            // 서버에서 받아오는 날짜 데이터와 형식을 맞춰 객체에 .setTag()를 한다.
            String tag = calendarData.getYear() + "-" + Utils.CalendarUtil.getFormattedForCal(calendarData.getMonth());
            calendarItem.setTag(tag);
            items.add(calendarItem);
            calendarContainer.addView(calendarItem);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.STATUS = NOTHING;
    }

    private void getCalendarDate() {
        calendarDatas = Utils.CalendarUtil.getCalendarData();
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

    private void checkDate(String checkinDate, String checkoutDate) {
        if (checkinDate != null && !checkinDate.equals("") && checkoutDate != null && !checkoutDate.equals("")) {
            Intent intent = new Intent();
            intent.putExtra("checkin", checkinDate);
            intent.putExtra("checkout", checkoutDate);
            setResult(200, intent);
            finish();
        } else {
            Toast.makeText(this, "체크인 날짜와 체크아웃 날짜를 다시 한번 확인해주세요!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doOneReservation(List<Reservation> reservations) {
        if (this.reservations == null) {
            this.reservations = reservations;
        } else {
            this.reservations.clear();
            this.reservations = reservations;
        }

        initView();
        setOnClick();
        setCalendar();

        for (Reservation item : reservations) {
            setBookedDates(item.getCheckin_date(), item.getCheckout_date());
        }
    }
}
