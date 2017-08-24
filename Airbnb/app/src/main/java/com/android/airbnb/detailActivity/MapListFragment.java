package com.android.airbnb.detailActivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapListFragment extends Fragment {

    private TextView title;
    private int index;
    private List<House> houseList;


    public MapListFragment() {
        // Required empty public constructor
    }

    public static MapListFragment newInstance(int i) {
        MapListFragment mf = new MapListFragment();
        Bundle args = new Bundle();
        args.putInt("INDEX", i);
        mf.setArguments(args);
        return mf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.googlemap_viewpager_item, container, false);
        title = (TextView) view.findViewById(R.id.txt_1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) index = args.getInt("INDEX", 0);


    }
}
