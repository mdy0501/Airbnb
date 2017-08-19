package com.android.airbnb.main.registerrooms.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.R;

/**
 * 숙소등록 2-1단계 (숙소사진 등록)
 */
public class HostRoomsRegisterDetailImageFragment extends Fragment {


    public HostRoomsRegisterDetailImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_host_rooms_register_detail_image, container, false);
    }

}
