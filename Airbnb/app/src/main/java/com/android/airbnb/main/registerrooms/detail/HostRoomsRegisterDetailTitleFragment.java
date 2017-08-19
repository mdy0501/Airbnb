package com.android.airbnb.main.registerrooms.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.R;

/**
 * 숙소등록 2-3단계 (숙소제목 등록)
 */
public class HostRoomsRegisterDetailTitleFragment extends Fragment {


    public HostRoomsRegisterDetailTitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_host_rooms_register_detail_title, container, false);
    }

}
