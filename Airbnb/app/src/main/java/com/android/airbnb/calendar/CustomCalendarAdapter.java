package com.android.airbnb.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class CustomCalendarAdapter extends RecyclerView.Adapter<CustomCalendarAdapter.Holder> implements GridAdapter.OnCalendarChangedListener {

    private List<CalendarData> dateList;
    private Context mContext;
    private GridAdapter.OnTextChangedListener mOnTextChangedListener;
    public Map<String, GridAdapter> adapters = new LinkedHashMap<>();
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
        holder.setCalendarMonth(dateList.get(position).getYear() + "년 " + dateList.get(position).getMonth() + "월");
        gridAdapter = new GridAdapter(dateList.get(position), (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE), mOnTextChangedListener, this);
        adapters.put(gridAdapter.getYearMonth(), gridAdapter);
        holder.getCalendarGridView().setAdapter(gridAdapter);
        position++;
        Log.d("CustomCalendar", "adapters : " + adapters.size());
        return holder;
    }

    @Override
    public void resetAll() {
        for (GridAdapter adapter : adapters) {
            adapter.resetAllGridAdapter();
        }
    }

    public Map<String ,GridAdapter> selectedAdapter = new LinkedHashMap<>();

    public void findCheckIn() {
        // selelcted adapter clear 하는 구간 잘 선정하기
        selectedAdapter.clear();
        for (GridAdapter adapter : adapters) {
            if (adapter.selectedCount == 1 || adapter.selectedCount == 2) {
                selectedAdapter.put(adapter.mCalendarData.getYear()+adapter.mCalendarData.getMonth(), adapter);
                Log.d("CustomCalendarAdapter", "selectedAdapter : " + selectedAdapter.size());
            }
        }
        setSelectedAdapter();
    }

    public void setSelectedAdapter() {
        Set<String> keys = selectedAdapter.keySet();
        String arrayKeys[] = keys.toArray(new String[keys.size()]);
        int firstKey = Integer.parseInt(arrayKeys[0]);
        int lastKey = Integer.parseInt(arrayKeys[1]);
        if (arrayKeys.length == 2) {
            Log.e("CustomCalendarAdapter", "firstKey : " + firstKey + ", lastKey : " + lastKey);
            if (lastKey > firstKey) {
                for (int i = firstKey; i <= lastKey; i++) {
                    if (i == firstKey) {
                        GridAdapter adapter = adapters.get(i);
                        Log.e("CustomCalendarAdapter", "adapters : " + adapters.get(i).isEmpty());
                        for (int k = adapter.selectedHolders.get(0).getHolderPosition(); k < adapter.allHolders.size(); k++) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                        }
                    } else if (i == lastKey) {
                        GridAdapter adapter = adapters.get(i);
                        for (int k = adapter.selectedHolders.get(0).getHolderPosition(); k >= 0; k--) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                            Log.e("CustomCalendarAdapter", "adapters : " + adapter.isEmpty());
                        }
                    } else {
                        GridAdapter adapter = adapters.get(i);
                        for (int k = 0; k < adapter.allHolders.size(); k++) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                        }
                    }
                }
            } else if (lastKey < firstKey){
                Toast.makeText(mContext, "firstKey > lastKey 로직 짜라!", Toast.LENGTH_SHORT).show();
                for (int i = firstKey; i < adapters.size(); i++) {
                    adapters.get(firstKey);
                }
            }
        } else /* if (arrayKeys.length == 1) */ {
            GridAdapter adapter = adapters.get(firstKey);
            for (int i = adapter.selectedHolders.get(0).getHolderPosition(); i < adapter.selectedHolders.get(1).getHolderPosition(); i++) {
                adapter.setClickedView(adapter.allConvertviews.get(i), adapter.allHolders.get(i), adapter.allHolders.get(i).getHolderPosition());
            }
        }
        Log.e("CustomCalendarAdapter", "==================== setSelectedAdapter done!");
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
