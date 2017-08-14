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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class CustomCalendarAdapter extends RecyclerView.Adapter<CustomCalendarAdapter.Holder> implements GridAdapter.OnCalendarChangedListener {

    private List<CalendarData> dateList;
    private Context mContext;
    private GridAdapter.OnTextChangedListener mOnTextChangedListener;
    public List<GridAdapter> adapters = new ArrayList<>();
    private int position = 0;
    GridAdapter gridAdapter = null;

    public final static String STATUS_CHECKIN = "status_check_in";
    public final static String STATUS_CHECKOUT = "status_check_out";
    public final static String STATUS_NOTHING = "status_check_nothing";
    public static String status = STATUS_CHECKIN;

    public CustomCalendarAdapter(List<CalendarData> dateList, GridAdapter.OnTextChangedListener mOnTextChangedListener, Context mContext) {
        this.mOnTextChangedListener = mOnTextChangedListener;
        this.dateList = dateList;
        this.mContext = mContext;
    }

    /**
     * 카운트(일종의 flag) 값을 만들어서 position을 돌 때마다 position 값이 증가되어 onCreateViewHolder를 통해 증가된 인덱스 값의 데이터를 꺼내어 데이터를 셋팅 후, holder 생성
     */
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        // 홀더 셋팅 후, 리턴하게 로직을 만듬
        Holder holder = new Holder(view);
        holder.setCalendarMonth(dateList.get(position).getMonth() + "월");
        gridAdapter = new GridAdapter(dateList.get(position), (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE), mOnTextChangedListener, this);
        adapters.add(gridAdapter);
        holder.getCalendarGridView().setAdapter(gridAdapter);
        position++;
        return holder;
    }

    @Override
    public void resetAll() {
        Log.d("CustomCalendar", "adapter size === " + adapters.size());
        for (GridAdapter adapter : adapters) {
            adapter.resetAll();
        }
        Log.d("CustomCalendar", "checkInChanged === done ");
    }

    // TODO 완성시키기
    private void findCheckIn() {
//        for(GridAdapter adapter : adapters){
//            if (adapter.getStaus() == STATUS_CHECKOUT){
//                adapter.get
//            }
//        }
    }

    // ==== 틀린 코드 ====
    // 로그 한 번 찍어보면 알겠지만 adapter가 계속 생기는 건 상식적으로 말이 안됨...
    @Override
    public void onBindViewHolder(Holder holder, int position) {
//        this.position = position;
//        holder.setCalendarMonth(dateList.get(position).getMonth() + "월");
//        gridAdapter = new GridAdapter(dateList.get(position).getDays(), (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE), mOnTextChangedListener);
//        adapters.add(gridAdapter);
//        Log.d("CustomCalendar", "grid adapter size :: " + adapters.size());
//        holder.getCalendarGridView().setAdapter(gridAdapter);
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
