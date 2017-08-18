package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.R;
import com.android.airbnb.adapter.TripAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestSearchTripFragment extends Fragment implements ITask.totalHouseList{

    private List<House> dataList;
    private GuestMainActivity guestMainActivity;
    private RecyclerView recyclerTrip;
    private TripAdapter tripAdapter;


    public GuestSearchTripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        guestMainActivity = (GuestMainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_search_trip, container, false);
        setViews(view);
        Loader.getTotalHouse(this);
        return view;
    }

    private void setViews(View view){
        recyclerTrip = (RecyclerView) view.findViewById(R.id.recyclerTrip);
    }


    @Override
    public void doTotalHouseList(List<House> houseList) {
        dataList = houseList;
        tripAdapter = new TripAdapter(guestMainActivity.getBaseContext(), dataList);
        recyclerTrip.setAdapter(tripAdapter);
        recyclerTrip.setLayoutManager(new GridLayoutManager(guestMainActivity.getBaseContext(), 2));
    }
}
