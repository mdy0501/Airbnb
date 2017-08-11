package com.android.airbnb.main.registerrooms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicLocationFragment extends Fragment {


    public HostRoomsRegisterBasicLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_host_rooms_register_basic_location, container, false);
    }

}
