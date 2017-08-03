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
public class SearchFragment extends Fragment {

    private Main2Activity main2Activity;
    private TextView txtTitle;
    private TabLayout searchTabLayout;

    private SearchRecommendFragment searchRecommendFragment;
    private SearchRoomsFragment searchRoomsFragment;
    private SearchTripFragment searchTripFragment;
    private SearchPlaceFragment searchPlaceFragment;

    private static final int RECOMMEND = 0;
    private static final int ROOMS = 1;
    private static final int TRIP = 2;
    private static final int PLACE = 3;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        main2Activity = (Main2Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setFragments();
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        searchTabLayout = (TabLayout) view.findViewById(R.id.searchTabLayout);
        main2Activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.search_container, searchRecommendFragment)
                .commit();
    }

    private void setListeners(){
        searchTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.w("test============" , tab.getPosition()+"");
                switch (tab.getPosition()){
                    case RECOMMEND :
                        main2Activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.search_container, searchRecommendFragment)
                                .commit();
                        break;
                    case ROOMS :
                        main2Activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.search_container, searchRoomsFragment)
                                .commit();
                        break;
                    case TRIP :
                        main2Activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.search_container, searchTripFragment)
                                .commit();
                        break;
                    case PLACE :
                        main2Activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.search_container, searchPlaceFragment)
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
        searchRecommendFragment = new SearchRecommendFragment();
        searchRoomsFragment = new SearchRoomsFragment();
        searchTripFragment = new SearchTripFragment();
        searchPlaceFragment = new SearchPlaceFragment();
    }

}
