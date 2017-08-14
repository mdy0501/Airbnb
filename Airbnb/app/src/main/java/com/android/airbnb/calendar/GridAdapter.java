package com.android.airbnb.calendar;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class GridAdapter extends BaseAdapter {

    private CalendarData dataList;
    private LayoutInflater inflater;
    private List<View> selectedConvertviews = new ArrayList<>();
    private List<Holder> selectedHolders = new ArrayList<>();
    private OnTextChangedListener mOnTextChangedListener;
    private OnCalendarChangedListener mOnCalendarChangedListener;
    public String beginDate = "";
    public String endDate = "";
    public List<Holder> allHolders = new ArrayList<>();
    public List<View> allConvertviews = new ArrayList<>();

    public GridAdapter(CalendarData dataList, LayoutInflater inflater, OnTextChangedListener onTextChangedListener, OnCalendarChangedListener onCalendarChangedListener) {
        this.mOnTextChangedListener = onTextChangedListener;
        this.dataList = dataList;
        this.inflater = inflater;
        this.mOnCalendarChangedListener = onCalendarChangedListener;
    }

    @Override
    public int getCount() {
        return dataList.getDays().size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.getDays().get(position);
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
            final List<String> days = dataList.getDays();

            holder.setHolderPosition(position);
            holder.setCalendarOneday(days.get(position));

            allConvertviews.add(finalConvertView);
            allHolders.add(holder);
            // onClick 시 ===
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (days.size() > 0) {
                        Toast.makeText(v.getContext(), dataList.getYear() + "년 " + dataList.getMonth() + "월 " + days.get(position) + "일 is clicked", Toast.LENGTH_SHORT).show();
                        switch (CustomCalendarAdapter.status) {
                            case CustomCalendarAdapter.STATUS_CHECKIN:
                                mOnCalendarChangedListener.resetAll();
                                if (selectedConvertviews.size() > 0) {
                                    mOnCalendarChangedListener.resetAll();
                                    CustomCalendar.beginDate = "";
                                    CustomCalendar.endDate = "";
                                    for (int i = 0; i < selectedConvertviews.size(); i++) {
                                        resetView(selectedConvertviews.get(i), selectedHolders.get(i), selectedHolders.get(i).getHolderPosition());
                                    }
                                    selectedConvertviews.clear();
                                    selectedHolders.clear();
                                }
                                mOnTextChangedListener.checkInChanged("체크인" + "\n" + dataList.getMonth() + "월 " + dataList.getDays().get(position) + "일");
                                setClickedView(finalConvertView, holder, holder.getHolderPosition());
                                addList(finalConvertView, holder);
                                CustomCalendar.beginDate = dataList.getYear() + "-" + Utils.CalendarUtil.getFormattedForCal(dataList.getMonth()) + "-" + Utils.CalendarUtil.getFormattedForCal(dataList.getDays().get(position)) ;
                                Log.e("GridAdapter", "beginDate :: " + CustomCalendar.beginDate);
                                CustomCalendarAdapter.status = CustomCalendarAdapter.STATUS_CHECKOUT;
                                Log.e("GridAdapter", "status :: " + CustomCalendarAdapter.status);
                                break;

                            case CustomCalendarAdapter.STATUS_CHECKOUT:
                                setClickedView(finalConvertView, holder, holder.getHolderPosition());
                                addList(finalConvertView, holder);
                                CustomCalendar.endDate = dataList.getYear() + "-" + Utils.CalendarUtil.getFormattedForCal(dataList.getMonth()) + "-" + Utils.CalendarUtil.getFormattedForCal(dataList.getDays().get(position)) ;
                                mOnTextChangedListener.checkOutChanged("체크아웃" + "\n" + dataList.getMonth() + "월 " + dataList.getDays().get(holder.getHolderPosition()) + "일");
                                Log.e("GridAdapter", "endDate :: " + CustomCalendar.beginDate);
                                Log.e("GridAdapter", "begin date :: " + CustomCalendar.beginDate + ", end date :: " + CustomCalendar.endDate);
                                CustomCalendarAdapter.status = CustomCalendarAdapter.STATUS_CHECKIN;
                                Log.e("GridAdapter", "status :: " + CustomCalendarAdapter.status);
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
        convertView.setBackgroundResource(R.color.DeepGreen);
        holder.calendarOneday.setTextColor(Color.WHITE);
        holder.calendarOneday.setTypeface(null, Typeface.BOLD);
        holder.setCalendarOneday(dataList.getDays().get(position).toString());
        // 리스트에 추가되는 부분 또한 해당 함수에 넣어 놓았다.
    }

    public void resetAll() {
        Log.e("GridAdapter", "all holders size :: " + allHolders.size());
        Log.e("GridAdapter", "all convertviews size :: " + allConvertviews.size());

        for (View convertView : allConvertviews) {
            convertView.setBackgroundResource(R.color.white);
            Log.e("GridAdapter", "=== reset converviews done");
        }
        for (Holder holder : allHolders) {
            holder.calendarOneday.setTextColor(Color.BLACK);
            holder.calendarOneday.setTypeface(null, Typeface.NORMAL);
            holder.setCalendarOneday(dataList.getDays().get(holder.getHolderPosition()).toString());
            Log.e("GridAdapter", "=== reset all holders done");
        }
        Log.e("GridAdapter", "=== reset all  done");
    }

    private void addList(View convertView, Holder holder) {
        selectedConvertviews.add(convertView);
        selectedHolders.add(holder);
    }

    public void resetView(View convertView, Holder holder, int position) {
        convertView.setBackgroundResource(R.color.white);
        holder.calendarOneday.setTextColor(Color.BLACK);
        holder.calendarOneday.setTypeface(null, Typeface.NORMAL);
        holder.setCalendarOneday(dataList.getDays().get(position).toString());
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView calendarOneday;
        private int position;

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
    }

    public interface OnTextChangedListener {
        public void checkInChanged(String selectedCheckInDate);
        public void checkOutChanged(String selectedCheckOutDate);
        public void calculatedDate(String result);
    }

    public interface OnCalendarChangedListener {
        public void resetAll();
    }
}
