package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.airbnb.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuestSearchFragment extends Fragment {

    private GuestMainActivity guestMainActivity;
    private TextView txtTitle;
    private TabLayout searchTabLayout;

    private GuestSearchRecommendFragment guestSearchRecommendFragment;
    private GuestSearchRoomsFragment guestSearchRoomsFragment;
    private GuestSearchTripFragment guestSearchTripFragment;
    private GuestSearchPlaceFragment guestSearchPlaceFragment;

    private static final int RECOMMEND = 0;
    private static final int ROOMS = 1;
    private static final int TRIP = 2;
    private static final int PLACE = 3;

    public GuestSearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_guest_search, container, false);
        setFragments();
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        searchTabLayout = (TabLayout) view.findViewById(R.id.searchTabLayout);
        guestMainActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.search_container, guestSearchRecommendFragment)
                .commit();
    }

    private void setListeners(){
        searchTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.w("test============" , tab.getPosition()+"");
                switch (tab.getPosition()){
                    case RECOMMEND :
                        guestMainActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.search_container, guestSearchRecommendFragment)
                                .commit();
                        break;
                    case ROOMS :
                        guestMainActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.search_container, guestSearchRoomsFragment)
                                .commit();
                        break;
                    case TRIP :
                        guestMainActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.search_container, guestSearchTripFragment)
                                .commit();
                        break;
                    case PLACE :
                        guestMainActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.search_container, guestSearchPlaceFragment)
                                .commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setFragments(){
        guestSearchRecommendFragment = new GuestSearchRecommendFragment();
        guestSearchRoomsFragment = new GuestSearchRoomsFragment();
        guestSearchTripFragment = new GuestSearchTripFragment();
        guestSearchPlaceFragment = new GuestSearchPlaceFragment();
    }

}
