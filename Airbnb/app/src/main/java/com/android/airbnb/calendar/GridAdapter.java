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
import com.android.airbnb.domain.airbnb.ReservationDummy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class GridAdapter extends BaseAdapter {

    private List<Integer> list;
    private LayoutInflater inflater;
    private List<View> convertViews = new ArrayList<>();
    private List<Holder> holderList = new ArrayList<>();
    private View convertView = null;
    private OnTextChangedListener mOnTextChangedListener;
    private final int STATUS_CHECKIN = 21312555;
    private final int STATUS_CHECKOUT = 21555;
    private int staus = STATUS_CHECKIN;
    private String beginDate = "";
    private String endDate = "";


    public GridAdapter(List<Integer> list, LayoutInflater inflater, OnTextChangedListener mOnTextChangedListener) {
        this.mOnTextChangedListener = mOnTextChangedListener;
        this.list = list;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item_gridview, parent, false);
            this.convertView = convertView;
            final View finalConvertView = convertView;
            final Holder holder = new Holder(convertView);
            holder.setHolderPosition(position);
            holder.setCalendarOneday(list.get(position));
            Log.e("GridAdpater", "pos :: " + holder.getHolderPosition());
            // onClick 시 ===
            ReservationDummy reservationDummy = new ReservationDummy();
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position) != 0) {
                        Toast.makeText(v.getContext(), position + " is clicked", Toast.LENGTH_SHORT).show();
                        switch (staus) {
                            case STATUS_CHECKIN:
                                if (convertViews.size() > 1 ) {
                                    resetView(convertViews.get(1), holderList.get(1), holderList.get(1).getHolderPosition());
                                    resetView(convertViews.get(0), holderList.get(0), holderList.get(0).getHolderPosition());
                                    convertViews.clear();
                                    holderList.clear();
                                }
                                setClickedView(finalConvertView, holder, holder.getHolderPosition());
                                convertViews.add(finalConvertView);
                                holderList.add(holder);
                                beginDate = position + "";
                                Log.e("Grid Adapter", "begin date : " + GridAdapter.this.beginDate);
                                mOnTextChangedListener.checkInChanged("체크인" + "\n" + position);
                                staus = STATUS_CHECKOUT;
                                break;

                            case STATUS_CHECKOUT:
                                convertViews.add(finalConvertView);
                                holderList.add(holder);
                                setClickedView(finalConvertView, holder, holder.getHolderPosition());
                                endDate = position + "";
                                Log.e("Grid Adapter", "begin date : " + GridAdapter.this.endDate);
                                mOnTextChangedListener.checkOutChanged("체크아웃" + "\n" + position);
                                staus = STATUS_CHECKIN;
                                break;
                        }
                    }
                }
            });
        }
        return convertView;
    }

    private void setClickedView(View convertView, Holder holder, int position) {
        convertView.setBackgroundResource(R.color.DeepGreen);
        holder.calendarOneday.setTextColor(Color.WHITE);
        holder.calendarOneday.setTypeface(null, Typeface.BOLD);
        holder.setCalendarOneday(list.get(position).toString());
    }

    private void resetView(View convertView, Holder holder, int position) {
        Log.e("GridAdapter", "=== done");
        convertView.setBackgroundResource(R.color.white);
        holder.calendarOneday.setTextColor(Color.BLACK);
        holder.calendarOneday.setTypeface(null, Typeface.NORMAL);
        holder.setCalendarOneday(list.get(position).toString());
    }

    private class Holder extends RecyclerView.ViewHolder {

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
    }
}
