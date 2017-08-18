package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.adapter.PlaceFirstAdapter;
import com.android.airbnb.adapter.PlaceSecondAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestSearchPlaceFragment extends Fragment implements ITask.totalHouseList{

    private List<House> dataList;
    private GuestMainActivity guestMainActivity;
    private TextView txtTitle1, txtTitle2;
    private PlaceFirstAdapter placeFirstAdapter;
    private PlaceSecondAdapter placeSecondAdapter;
    private ViewPager viewPager1, viewPager2;


    public GuestSearchPlaceFragment() {
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
        View view = inflater.inflate(R.layout.fragment_guest_search_place, container, false);
        setViews(view);
        Loader.getTotalHouse(this);
        return view;
    }

    private void setViews(View view){
        txtTitle1 = (TextView) view.findViewById(R.id.txtTitle1);
        txtTitle2 = (TextView) view.findViewById(R.id.txtTitle2);
        viewPager1 = (ViewPager) view.findViewById(R.id.viewPager1);
        viewPager2 = (ViewPager) view.findViewById(R.id.viewPager2);
    }


    @Override
    public void doTotalHouseList(List<House> houseList) {
        dataList = houseList;
        // Place(장소) 첫번째 ViewPager
        placeFirstAdapter = new PlaceFirstAdapter(guestMainActivity.getBaseContext(), dataList);
        viewPager1.setAdapter(placeFirstAdapter);
        // Place(장소) 두번째 ViewPager
        placeSecondAdapter = new PlaceSecondAdapter(guestMainActivity.getBaseContext(), dataList);
        viewPager2.setAdapter(placeSecondAdapter);
    }
}
