package com.android.airbnb.main.registerrooms.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.R;

/**
 * 숙도등록 2-2단계 (숙소소개 등록)
 */
public class HostRoomsRegisterDetailIntroduceFragment extends Fragment {


    public HostRoomsRegisterDetailIntroduceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_host_rooms_register_detail_introduce, container, false);
    }

}
