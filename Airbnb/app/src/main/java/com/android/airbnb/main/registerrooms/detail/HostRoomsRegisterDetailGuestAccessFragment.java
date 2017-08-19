package com.android.airbnb.main.registerrooms.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.R;

/**
 * 숙소등록 2-5단계 (숙소편의시설현황 등록)
 */
public class HostRoomsRegisterDetailGuestAccessFragment extends Fragment {


    public HostRoomsRegisterDetailGuestAccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_host_rooms_register_detail_guest_access, container, false);
    }

}
