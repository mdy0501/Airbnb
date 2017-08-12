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

    private List<Integer> list;
    private LayoutInflater inflater;
    private View[] convertViews = new View[2];
    private List<Holder> holderList = new ArrayList<>();
    private View convertView = null;
    private OnTextChangedListener mOnTextChangedListener;
    private final int STATUS_CHECKIN = 21312555;
    private final int STATUS_CHECKOUT = 21555;
    private int staus = STATUS_CHECKIN;


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
            final Holder holder = new Holder(convertView);
            holderList.add(holder);
            holder.setCalendarOneday(list.get(position));
            holder.setHolderPosition(position);
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position) != 0 ) {
                        Toast.makeText(v.getContext(), position + " is clicked", Toast.LENGTH_SHORT).show();

                        switch (staus){
                            case STATUS_CHECKIN:
                                if (convertViews[0] != null){
                                    Log.e("GridAdapter", "=========================== start");
                                    resetView(convertViews[0], holderList.get(position), holder.getHolderPosition());
                                    Log.e("GridAdapter", "=========================== start[1]");
                                    resetView(convertViews[1], holderList.get(position), holder.getHolderPosition());
                                    Log.e("GridAdapter", "=========================== start[2]");
                                    Log.e("GridAdapter", "=========================== finish");
                                }
                                convertViews[0] = finalConvertView;
                                setClickedView(finalConvertView, holder, holder.getHolderPosition());
                                mOnTextChangedListener.checkInChanged("체크인" + "\n" + position);
                                staus = STATUS_CHECKOUT;
                                break;

                            case STATUS_CHECKOUT:
                                convertViews[1] = finalConvertView;
                                setClickedView(finalConvertView, holder, holder.getHolderPosition());
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

    private void setClickedView(View convertView, Holder holder, int position){
        convertView.setBackgroundResource(R.color.DeepGreen);
        holder.calendarOneday.setTextColor(Color.WHITE);
        holder.calendarOneday.setTypeface(null, Typeface.BOLD);
        holder.setCalendarOneday(list.get(position));
    }

    private void resetView(View convertView, Holder holder, int position){
        convertView.setBackgroundResource(R.color.white);
        holder.calendarOneday.setText(list.get(position));
        holder.calendarOneday.setTextColor(Color.BLUE);
        holder.calendarOneday.setTypeface(null, Typeface.BOLD);
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

        public int getHolderPosition(){
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
