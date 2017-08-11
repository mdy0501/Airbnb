package com.android.airbnb.calendar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class GridAdapter extends BaseAdapter {

    private List<String> list;
    private LayoutInflater inflater;

    public GridAdapter(List<String> list, LayoutInflater inflater) {
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

        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item_gridview, parent, false);
            holder = new Holder(convertView);
            holder.setCalendarOneday(list.get(position));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), position + " is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }

    private class Holder extends RecyclerView.ViewHolder {
        public void setCalendarOneday(String day) {
            this.calendarOneday.setText(day);
        }

        public TextView calendarOneday;

        public Holder(View itemView) {
            super(itemView);
            calendarOneday= (TextView)itemView.findViewById(R.id.calendar_oneday);
        }
    }
}
