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
import com.android.airbnb.domain.reservation.Reservation;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by JunHee on 2017. 8. 10..
 */

public class CustomCalendarAdapter extends RecyclerView.Adapter<CustomCalendarAdapter.Holder> implements GridAdapter.OnCalendarChangedListener, ITask.oneReservation {

    private List<CalendarData> dateList;
    private List<Reservation> reservations;
    private Context mContext;
    private GridAdapter.OnTextChangedListener mOnTextChangedListener;
    public Map<String, GridAdapter> adapters = new LinkedHashMap<>();
    private int position = 0;
    private String housePk = "";
    private GridAdapter gridAdapter = null;

    public final static String STATUS_CHECKIN = "status_check_in";
    public final static String STATUS_CHECKOUT = "status_check_out";
    public static String status = STATUS_CHECKIN;

    public CustomCalendarAdapter(List<CalendarData> dateList, String housePk, GridAdapter.OnTextChangedListener mOnTextChangedListener, Context mContext) {
        this.mOnTextChangedListener = mOnTextChangedListener;
        this.dateList = dateList;
        this.mContext = mContext;
        this.housePk = housePk;

        // reservation 통신
        Loader.getReservation(housePk, this);
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
        gridAdapter = new GridAdapter(dateList.get(position), mOnTextChangedListener, this);
        // key 수정
        adapters.put(Utils.CalendarUtil.getFormattedForCal(gridAdapter.mCalendarData.getMonth()), gridAdapter);
        holder.getCalendarGridView().setAdapter(gridAdapter);
        position++;
        return holder;
    }

    private void setReservedDates(){
        for(int i=0; i < reservations.size(); i++){
            Reservation reservation = reservations.get(i);
            String[] splittedCheckin = reservation.getCheckin_date().split("-");
            String[] splittedCheckout = reservation.getCheckout_date().split("-");
            if(splittedCheckin[1].equals(splittedCheckout[1])){
                GridAdapter adapter = adapters.get(splittedCheckin[1]);
                int start = Integer.parseInt(splittedCheckin[2]) + adapter.mCalendarData.getFirstWeekDay()-2;
                int end = Integer.parseInt(splittedCheckout[2]) + adapter.mCalendarData.getFirstWeekDay()-2;
                for (int k = start ; k <= end; k++){
                    View convertview = adapter.allConvertviews.get(k);
                    GridAdapter.Holder holder = adapter.allHolders.get(k);
                    convertview.setTag(GridAdapter.IS_BOOKED);
                    holder.setBooked(true);
                    adapter.setBooked(convertview, holder, holder.getHolderPosition());
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void resetAll() {
        for (String key : adapters.keySet()) {
            adapters.get(key).resetAllGridAdapter();
        }
    }

    public Map<String, GridAdapter> selectedAdapter = new LinkedHashMap<>();
    public void findSelectedGridAdapter() {
        // selelcted adapter clear 하는 구간 잘 선정하기
        selectedAdapter.clear();
        for (String key : adapters.keySet()) {
            GridAdapter adapter = adapters.get(key);
            if (adapter.selectedCount == 1 || adapter.selectedCount == 2) {
                selectedAdapter.put(adapter.mCalendarData.getMonth(), adapter);
            }
        }
    }


    // TODO 코드 정리하기
    // 해 넘어가는 부분 오류났던 logic 해결 ok
    public void setSelectedHolders() {
        Set<String> keys = selectedAdapter.keySet();
        String arrayKeys[] = keys.toArray(new String[keys.size()]);
        if (arrayKeys.length == 2) {
            int firstKey = Integer.parseInt(arrayKeys[0]);
            int lastKey = Integer.parseInt(arrayKeys[1]);
            Log.e("CustomCalendarAdapter", "firstKey : " + firstKey + ", lastKey : " + lastKey);
            if (lastKey > firstKey) {
                for (int i = firstKey; i <= lastKey; i++) {
                    String formatted = i+"";
                    if(formatted.length() != 2){
                        formatted = "0" + i;
                    }
                    if (i == firstKey) {
                        GridAdapter adapter = adapters.get(formatted);
                        for (int k = adapter.selectedHolders.get(0).getHolderPosition(); k < adapter.allHolders.size(); k++) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                        }
                    } else if (i == lastKey) {
                        GridAdapter adapter = adapters.get(formatted);
                        for (int k = adapter.selectedHolders.get(0).getHolderPosition(); k >= 0; k--) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                            Log.e("CustomCalendarAdapter", "adapters : " + adapter.isEmpty());
                        }
                    } else {
                        GridAdapter adapter = adapters.get(formatted);
                        for (int k = 0; k < adapter.allHolders.size(); k++) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                        }
                    }
                }
            } else if (lastKey < firstKey) {
                for (int i = firstKey; i <= 12; i++) {
                    String formatted = i+"";
                    if(formatted.length() != 2){
                        formatted = "0" + i;
                    }
                    GridAdapter adapter = adapters.get(formatted);
                    if (i == firstKey) {
                        for (int k = adapter.selectedHolders.get(0).getHolderPosition(); k < adapter.allHolders.size(); k++) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                        }
                    } else {
                        for (int k = 0; k < adapter.allHolders.size(); k++) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                        }
                    }
                }
                for (int i = 1; i <= lastKey; i++) {
                    String formatted = i+"";
                    if(formatted.length() != 2){
                        formatted = "0" + i;
                    }
                    GridAdapter adapter = adapters.get(formatted);
                    if (i == lastKey) {
                        for (int k = 0; k < adapter.selectedHolders.get(0).getHolderPosition(); k++) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                        }
                    } else {
                        for (int k = 0; k < adapter.allHolders.size(); k++) {
                            adapter.setClickedView(adapter.allConvertviews.get(k), adapter.allHolders.get(k), adapter.allHolders.get(k).getHolderPosition());
                        }
                    }

                }
            }
        } else if (arrayKeys.length == 1) {
            int firstKey = Integer.parseInt(arrayKeys[0]);
            String formatted = firstKey+"";
            if(formatted.length() != 2){
                formatted = "0" + firstKey;
            }
            GridAdapter adapter = adapters.get(formatted);
            for (int i = adapter.selectedHolders.get(0).getHolderPosition(); i < adapter.selectedHolders.get(1).getHolderPosition(); i++) {
                adapter.setClickedView(adapter.allConvertviews.get(i), adapter.allHolders.get(i), adapter.allHolders.get(i).getHolderPosition());
            }
        }
        Log.e("CustomCalendarAdapter", "==================== setSelectedHolders done!");
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

    @Override
    public void doTask(List<Reservation> reservations) {
        if(reservations != null){
            this.reservations = reservations;
            Log.e("Custom calendar","reservation size : " + reservations.size());
            setReservedDates();

        }
    }
}
