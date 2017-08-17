package com.android.airbnb.calendar;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.airbnb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class GridAdapter extends BaseAdapter {

    public CalendarData mCalendarData;
    public List<View> selectedConvertviews = new ArrayList<>();
    public List<Holder> selectedHolders = new ArrayList<>();
    private OnTextChangedListener mOnTextChangedListener;
    private OnCalendarChangedListener mOnCalendarChangedListener;
    public String beginDate = "";
    public String endDate = "";
    public List<Holder> allHolders = new ArrayList<>();
    public List<View> allConvertviews = new ArrayList<>();
    public int selectedCount = 0;
    public final static int IS_BOOKED = 1290712094;
    // key 값 역할을 할 수 있도록 yearMonth

    public GridAdapter(CalendarData mCalendarData, OnTextChangedListener onTextChangedListener, OnCalendarChangedListener onCalendarChangedListener) {
        this.mOnTextChangedListener = onTextChangedListener;
        this.mCalendarData = mCalendarData;
        this.mOnCalendarChangedListener = onCalendarChangedListener;
    }

    @Override
    public int getCount() {
        return mCalendarData.getDays().size();
    }

    @Override
    public Object getItem(int position) {
        return mCalendarData.getDays().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item_gridview, parent, false);
            final View finalConvertView = convertView;
            final Holder holder = new Holder(convertView);
            final List<String> days = mCalendarData.getDays();

            holder.setHolderPosition(position);
            holder.setCalendarOneday(days.get(position));

            allConvertviews.add(finalConvertView);
            allHolders.add(holder);
            // onClick 시 ===
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (days.size() > 0) {
                        switch (CustomCalendarAdapter.status) {
                            case CustomCalendarAdapter.STATUS_CHECKIN:
                                mOnCalendarChangedListener.resetAll();
                                if (selectedConvertviews.size() > 0) {
                                    CustomCalendar.beginDate = "";
                                    CustomCalendar.endDate = "";
                                    for (int i = 0; i < selectedConvertviews.size(); i++) {
                                        resetView(selectedConvertviews.get(i), selectedHolders.get(i), selectedHolders.get(i).getHolderPosition());
                                    }
                                    selectedConvertviews.clear();
                                    selectedHolders.clear();
                                }
                                selectedCount++;
                                setClickedView(finalConvertView, holder, holder.getHolderPosition());
                                addList(finalConvertView, holder);
                                beginDate = mCalendarData.getYear() + "-" + Utils.CalendarUtil.getFormattedForCal(mCalendarData.getMonth()) + "-" + Utils.CalendarUtil.getFormattedForCal(mCalendarData.getDays().get(position));
                                CustomCalendar.beginDate = beginDate;
                                mOnTextChangedListener.checkInChanged("체크인" + "\n" + mCalendarData.getMonth() + "월 " + mCalendarData.getDays().get(position) + "일");
                                CustomCalendarAdapter.status = CustomCalendarAdapter.STATUS_CHECKOUT;
                                break;

                            case CustomCalendarAdapter.STATUS_CHECKOUT:
                                selectedCount++;
                                setClickedView(finalConvertView, holder, holder.getHolderPosition());
                                addList(finalConvertView, holder);
                                endDate = mCalendarData.getYear() + "-" + Utils.CalendarUtil.getFormattedForCal(mCalendarData.getMonth()) + "-" + Utils.CalendarUtil.getFormattedForCal(mCalendarData.getDays().get(position));
                                CustomCalendar.endDate = endDate;
                                mOnTextChangedListener.checkOutChanged("체크아웃" + "\n" + mCalendarData.getMonth() + "월 " + mCalendarData.getDays().get(holder.getHolderPosition()) + "일");
                                CustomCalendarAdapter.status = CustomCalendarAdapter.STATUS_CHECKIN;
                                break;
                        }
                    }
                }
            });
        }
        return convertView;
    }

    // 클릭 시, 변화를 줘야하는 element들에 대해 함수로 빼서 정의
    public void setClickedView(View convertView, Holder holder, int position) {
        Log.e("GridAdapter", "setClickedView ==== start");
        convertView.setBackgroundResource(R.color.DeepGreen);
        holder.calendarOneday.setTextColor(Color.WHITE);
        holder.calendarOneday.setTypeface(null, Typeface.BOLD);
        holder.setCalendarOneday(mCalendarData.getDays().get(position).toString());
        // 리스트에 추가되는 부분 또한 해당 함수에 넣어 놓았다.
        Log.e("GridAdapter", "setClickedView ==== end");

    }

    public void resetAllGridAdapter() {
        Log.e("GridAdapter", "selectedCount : " + selectedCount);
        selectedCount = 0;
        for (View convertView : allConvertviews) {
            if (convertView.getTag() == null)
                convertView.setBackgroundResource(R.color.white);
        }
        for (Holder holder : allHolders) {
            if (holder.isBooked() == false) {
                holder.calendarOneday.setTextColor(Color.BLACK);
                holder.calendarOneday.setTypeface(null, Typeface.NORMAL);
                holder.setCalendarOneday(mCalendarData.getDays().get(holder.getHolderPosition()).toString());
            }
        }
    }

    private void addList(View convertView, Holder holder) {
        selectedConvertviews.add(convertView);
        selectedHolders.add(holder);
    }

    public void setBooked(View convertView, Holder holder, int position) {
        convertView.setBackgroundResource(R.color.white);
        convertView.setClickable(false);
        holder.calendarOneday.setTextColor(Color.GRAY);
        holder.calendarOneday.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.calendarOneday.setTypeface(null, Typeface.BOLD_ITALIC);
        holder.setCalendarOneday(mCalendarData.getDays().get(position).toString());
    }

    public void resetView(View convertView, Holder holder, int position) {
        convertView.setBackgroundResource(R.color.white);
        holder.calendarOneday.setTextColor(Color.BLACK);
        holder.calendarOneday.setTypeface(null, Typeface.NORMAL);
        holder.setCalendarOneday(mCalendarData.getDays().get(position).toString());
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView calendarOneday;
        private int position;
        private boolean isBooked = false;

        public Holder(View itemView) {
            super(itemView);
            calendarOneday = (TextView) itemView.findViewById(R.id.calendar_oneday);
        }

        public void setCalendarOneday(int day) {
            this.calendarOneday.setText(day + "");
        }

        public int getHolderPosition() {
            return this.position;
        }


        public void setCalendarOneday(String calendarOneday) {
            this.calendarOneday.setText(calendarOneday);
        }

        public void setHolderPosition(int position) {
            this.position = position;
        }

        public boolean isBooked() {
            return isBooked;
        }

        public void setBooked(boolean booked) {
            isBooked = booked;
        }
    }

    public interface OnTextChangedListener {
        public void checkInChanged(String selectedCheckInDate);

        public void checkOutChanged(String selectedCheckOutDate);
    }

    public interface OnCalendarChangedListener {
        public void resetAll();
    }
}
