package com.android.airbnb.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.GoogleMapViewPagerActivity;
import com.android.airbnb.R;
import com.android.airbnb.adapter.RoomsAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestSearchRoomsFragment extends Fragment implements ITask.totalHouseList, View.OnClickListener {

    private List<House> dataList;
    private GuestMainActivity guestMainActivity;
    private RecyclerView recyclerRooms;
    private RoomsAdapter roomsAdapter;
    private FloatingActionButton fabGoogleMapViewPager;


    public GuestSearchRoomsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_guest_search_rooms, container, false);
        setViews(view);
        setListeners();
        Loader.getTotalHouse(this);
        return view;
    }


    private void setViews(View view){
        recyclerRooms = (RecyclerView) view.findViewById(R.id.recyclerRooms);
        fabGoogleMapViewPager = (FloatingActionButton) view.findViewById(R.id.fabGoogleMapViewPager);
    }

    private void setListeners(){
        fabGoogleMapViewPager.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabGoogleMapViewPager:
                Intent intent = new Intent(guestMainActivity.getBaseContext(), GoogleMapViewPagerActivity.class);
                intent.putParcelableArrayListExtra("roomsHouseList", (ArrayList<? extends Parcelable>) dataList);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void doTask(List<House> houseList) {
        // FloatingActionButton을 통해 데이터를 넘겨주기 위해 houseList값을 dataList에 저장한다.
        dataList = houseList;
        roomsAdapter = new RoomsAdapter(guestMainActivity.getBaseContext(), houseList);
        recyclerRooms.setAdapter(roomsAdapter);
        recyclerRooms.setLayoutManager(new LinearLayoutManager(guestMainActivity.getBaseContext()));
    }
}
