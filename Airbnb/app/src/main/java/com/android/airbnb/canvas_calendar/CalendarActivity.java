package com.android.airbnb.canvas_calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;

public class CalendarActivity extends AppCompatActivity implements DatePickerController, View.OnClickListener {

    private DayPickerView dayPickerView;
    private ImageView calendarBtnClose;
    private TextView calendarDelete;
    private TextView checkinDateTxt;
    private TextView checkoutDateTxt;
    private TextView calendarResultTxt;
    private Button calendarBtnSave;
    private boolean isCheckIn = true;
    public int scrollPosX= 0;
    public int scrollPosY= 0;


// Restore state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initView();
    }



    @Override
    public int getMaxYear() {
        return 2020;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        if (isCheckIn) {
            checkoutDateTxt.setText("체크아웃");
            checkinDateTxt.setText("체크인" + "\n" + month + "월" + day + "일");
            isCheckIn = false;
        } else {
            checkoutDateTxt.setText("체크아웃" + "\n" + month + "월" + day + "일");
            isCheckIn = true;
        }
        Log.e("Day Selected", year + "-" + month + "-" + day);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
        checkinDateTxt.setText("체크인" + "\n" + selectedDays.getFirst().month + "월" + selectedDays.getFirst().day + "일");
        checkoutDateTxt.setText("체크아웃" + "\n" + selectedDays.getLast().month + "월" + selectedDays.getLast().day + "일");
    }

    private void initView() {
        calendarBtnClose = (ImageView) findViewById(R.id.calendar_btn_close);
        calendarDelete = (TextView) findViewById(R.id.calendar_delete);
        checkinDateTxt = (TextView) findViewById(R.id.checkin_date_txt);
        checkoutDateTxt = (TextView) findViewById(R.id.checkout_date_txt);
        calendarResultTxt = (TextView) findViewById(R.id.calendar_result_txt);
        calendarBtnSave = (Button) findViewById(R.id.calendar_btn_Save);

        calendarBtnClose.setClickable(true);
        calendarDelete.setClickable(true);

        calendarBtnClose.setOnClickListener(this);
        calendarDelete.setOnClickListener(this);
        calendarBtnSave.setOnClickListener(this);

        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setController(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calendar_btn_close:
                onBackPressed();
                break;

            case R.id.calendar_delete:
                scrollPosY = dayPickerView.computeVerticalScrollOffset();
                Log.e("calendaract", "scrollpos(1) :: " + "scroll x : " + scrollPosX + ", scroll y : " + scrollPosY);
                checkoutDateTxt.setText("체크아웃");
                checkinDateTxt.setText("체크인");
                dayPickerView.init(dayPickerView.mContext);
                dayPickerView.getSelectedDays().getFirst().setDay(-1, -1, -1);
                dayPickerView.getSelectedDays().getLast().setDay(-1, -1, -1);
                dayPickerView.mAdapter.notifyDataSetChanged();
                Log.e("calendaract", "scrollpos(2) :: " + "scroll x : " + scrollPosX + ", scroll y : " + scrollPosY);
                dayPickerView.scrollBy(scrollPosX, scrollPosY);
                break;

            case R.id.calendar_btn_Save:
                Toast.makeText(v.getContext(), "Post resevation info", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
