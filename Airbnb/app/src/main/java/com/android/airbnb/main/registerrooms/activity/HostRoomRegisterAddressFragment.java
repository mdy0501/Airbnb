package com.android.airbnb.main.registerrooms.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomRegisterAddressFragment extends Fragment implements View.OnClickListener {


    private Toolbar roomRegisterToolbar;
    private ConstraintLayout layoutRoomRegisterNation;
    private ConstraintLayout layoutRoomRegisterSiDo;
    private EditText hostRegisterSiDo;
    private ConstraintLayout layoutRoomRegisterSiGunGo;
    private EditText hostRegisterSiGunGu;
    private ConstraintLayout layoutRoomRegisterStreetAddress;
    private EditText hostRegisterStreetAddress;
    private ConstraintLayout layoutRoomRegisterDongHosu;
    private EditText hostRegisterDongHosu;
    private ConstraintLayout layoutRoomRegisterZipCode;
    private EditText hostRegisterZipCode;
    private View view = null;
    private StreetAddressFragment streetAddressFragment;
    public FragmentManager fragmentManager;
    private RelativeLayout clickAddress;

    public HostRoomRegisterAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_host_room_register_address, container, false);
        initView(view);
        streetAddressFragment = new StreetAddressFragment();
        streetAddressFragment.fm = fragmentManager;
        return view;
    }

    private void initView(View view) {
        roomRegisterToolbar = (Toolbar) view.findViewById(R.id.room_register_toolbar);
        layoutRoomRegisterNation = (ConstraintLayout) view.findViewById(R.id.layout_room_register_nation);
        layoutRoomRegisterSiDo = (ConstraintLayout) view.findViewById(R.id.layout_room_register_si_do);
        hostRegisterSiDo = (EditText) view.findViewById(R.id.host_register_si_do);
        layoutRoomRegisterSiGunGo = (ConstraintLayout) view.findViewById(R.id.layout_room_register_si_gun_go);
        hostRegisterSiGunGu = (EditText) view.findViewById(R.id.host_register_si_gun_gu);
        layoutRoomRegisterStreetAddress = (ConstraintLayout) view.findViewById(R.id.layout_room_register_street_address);
        layoutRoomRegisterDongHosu = (ConstraintLayout) view.findViewById(R.id.layout_room_register_dong_hosu);
        hostRegisterDongHosu = (EditText) view.findViewById(R.id.host_register_dong_hosu);
        layoutRoomRegisterZipCode = (ConstraintLayout) view.findViewById(R.id.layout_room_register_zip_code);
        hostRegisterZipCode = (EditText) view.findViewById(R.id.host_register_zip_code);
        clickAddress = (RelativeLayout) view.findViewById(R.id.layout_room_register_street_address_click);
        clickAddress.setClickable(true);
        clickAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_room_register_street_address_click:
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.start_layout, streetAddressFragment);
                transaction.commit();
                break;

        }

    }
}
