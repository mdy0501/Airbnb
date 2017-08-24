package com.android.airbnb.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.googleMap.GoogleMapViewPagerActivity;
import com.android.airbnb.R;
import com.android.airbnb.adapter.RoomsAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestSearchRoomsFragment extends Fragment implements ITask.totalHouseList, View.OnClickListener, ITask.allWishList {

    private List<House> dataList;
    private GuestMainActivity guestMainActivity;
    private RecyclerView recyclerRooms;
    private RoomsAdapter roomsAdapter;
    private FloatingActionButton fabGoogleMapViewPager;
    private Context context;
    private SwipeRefreshLayout refreshLayout;
    public static final String GUEST_SEARCH_ROOMS_FRAGMENT = "com.android.airbnb.main.GUEST_SEARCH_ROOMS_FRAGMENT";

    public GuestSearchRoomsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        guestMainActivity = (GuestMainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_search_rooms, container, false);
        setViews(view);
        Loader.getTotalHouse(this);
        setListeners();
        setRefreshLayout();
        return view;
    }

    private void setWishlist() {
        Loader.getWishList("Token " + PreferenceUtil.getToken(getActivity().getBaseContext()), this);
    }

    private void setViews(View view) {
        recyclerRooms = (RecyclerView) view.findViewById(R.id.recyclerRooms);
        fabGoogleMapViewPager = (FloatingActionButton) view.findViewById(R.id.fabGoogleMapViewPager);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.search_refresh);
    }

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Loader.getTotalHouse(GuestSearchRoomsFragment.this);
            }
        });

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void setListeners() {
        fabGoogleMapViewPager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabGoogleMapViewPager:
                Intent intent = new Intent(guestMainActivity.getBaseContext(), GoogleMapViewPagerActivity.class);
                intent.putExtra("key", GUEST_SEARCH_ROOMS_FRAGMENT);
                intent.putParcelableArrayListExtra(GUEST_SEARCH_ROOMS_FRAGMENT, (ArrayList<? extends Parcelable>) dataList);
                startActivity(intent);
                break;
        }
    }

    private List<House> wishlist = new ArrayList<>();

    private void findWishHouses() {
        for (House house : dataList) {
            for (House wishHouse : wishlist) {
                if (house.getPk().equals(wishHouse.getPk())) {
                    house.setWished(true);
                }
            }
        }
    }

    @Override
    public void doAllWishList(List<House> houses) {
        wishlist = houses;
        findWishHouses();
        setAdapter();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void doTotalHouseList(List<House> houseList) {
        // FloatingActionButton을 통해 데이터를 넘겨주기 위해 houseList값을 dataList에 저장한다.
        dataList = houseList;
        Loader.getWishList("Token " + PreferenceUtil.getToken(context), this);
    }

    private void setAdapter() {
        roomsAdapter = new RoomsAdapter(guestMainActivity.getBaseContext(), dataList);
        recyclerRooms.setAdapter(roomsAdapter);
        recyclerRooms.setLayoutManager(new LinearLayoutManager(guestMainActivity.getBaseContext()));
    }
}
