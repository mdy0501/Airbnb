package com.android.airbnb.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.android.airbnb.R;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class CustomCalendarAdapter extends RecyclerView.Adapter<CustomCalendarAdapter.Holder> {

    private List<CalendarData> dateList;
    private Context mContext;

    public CustomCalendarAdapter(List<CalendarData> dateList, Context mContext) {
        this.dateList = dateList;
        this.mContext = mContext;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        Log.e("Calendar adapter", "onCreateViewHolder == done!");
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        CalendarData calendarData = dateList.get(position);
        List<String> days = calendarData.getDays();

        holder.setCalendarMonth(calendarData.getMonth() + "월");
        Log.e("Calendar adapter", "onBindViewHolder == done!");

        // holder가 gridView를 품고 있기 때문에, 이곳에서 setAdapter를 해준다.
        holder.getCalendarGridView().setAdapter(new GridAdapter(days, (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)));
        Log.e("Calendar adapter", "onBindViewHolder == set grid adapter done!");
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        private TextView calendarMonth;
        private GridView calendarGridView;

        public Holder(View itemView) {
            super(itemView);
            calendarMonth = (TextView) itemView.findViewById(R.id.calendar_month);
            calendarGridView = (GridView) itemView.findViewById(R.id.calendar_gridView);
        }

        public GridView getCalendarGridView() {
            return calendarGridView;
        }

        public void setCalendarMonth(String month) {
            this.calendarMonth.setText(month);
        }
    }
}
