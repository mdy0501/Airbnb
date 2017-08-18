package com.android.airbnb.main.registerrooms.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.google.android.gms.location.places.Place;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomRegisterAddressFragment extends Fragment implements View.OnClickListener, StreetAddressFragment.OnResultCallBack {


    private ConstraintLayout layoutRoomRegisterNation;
    private ConstraintLayout layoutRoomRegisterSiDo;
    private ConstraintLayout layoutRoomRegisterSiGunGo;
    private ConstraintLayout layoutRoomRegisterStreetAddress;
    private ConstraintLayout layoutRoomRegisterDongHosu;
    private ConstraintLayout layoutRoomRegisterZipCode;
    private Toolbar roomRegisterToolbar;
    private EditText hostRegisterSiDo;
    private EditText hostRegisterSiGunGu;
    private EditText hostRegisterStreetAddress;
    private EditText hostRegisterDongHosu;
    private EditText hostRegisterZipCode;
    public FragmentManager fragmentManager;
    private RelativeLayout clickAddress;
    private TextView resultStreetAddress;
    private StreetAddressFragment streetAddressFragment;
    private HostRoomRegisterLocationFragment hostRoomRegisterLocationFragment;
    private View view = null;
    private FloatingActionButton fabGoNext;

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
        setHostRoomRegisterLocationFragment();
        setStreetAddressFragment();
        return view;
    }

    private void setStreetAddressFragment() {
        streetAddressFragment = new StreetAddressFragment();
        streetAddressFragment.setFm(fragmentManager);
        streetAddressFragment.setmResultListener(this);
    }

    private void setHostRoomRegisterLocationFragment(){
        hostRoomRegisterLocationFragment = new HostRoomRegisterLocationFragment();
        hostRoomRegisterLocationFragment.setFm(fragmentManager);

    }

    private void initView(View view) {
        roomRegisterToolbar = (Toolbar) view.findViewById(R.id.room_register_toolbar);
        layoutRoomRegisterNation = (ConstraintLayout) view.findViewById(R.id.layoutNation);
        layoutRoomRegisterSiDo = (ConstraintLayout) view.findViewById(R.id.layoutSi);
        hostRegisterSiDo = (EditText) view.findViewById(R.id.editSi);
        layoutRoomRegisterSiGunGo = (ConstraintLayout) view.findViewById(R.id.layoutGu);
        hostRegisterSiGunGu = (EditText) view.findViewById(R.id.editGu);
        layoutRoomRegisterStreetAddress = (ConstraintLayout) view.findViewById(R.id.layoutDoromyung);
        layoutRoomRegisterDongHosu = (ConstraintLayout) view.findViewById(R.id.layoutDong);
        hostRegisterDongHosu = (EditText) view.findViewById(R.id.editDong);
        layoutRoomRegisterZipCode = (ConstraintLayout) view.findViewById(R.id.layoutZipCode);
        hostRegisterZipCode = (EditText) view.findViewById(R.id.editZipCode);
        clickAddress = (RelativeLayout) view.findViewById(R.id.layout_room_register_street_address_click);
        clickAddress.setClickable(true);
        clickAddress.setOnClickListener(this);
        resultStreetAddress = (TextView) view.findViewById(R.id.result_street_address);
        fabGoNext = (FloatingActionButton) view.findViewById(R.id.fab_go_next);
        fabGoNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.layout_room_register_street_address_click:
                transaction.add(R.id.start_layout, streetAddressFragment);
                transaction.commit();
                break;

            // 최초 fragment 호출 시에는 정상 작동하는데, back 후 다시 호출하게 되면 fragment를 호출하지 못함..
            // 해결 중...
            case R.id.fab_go_next:
                Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_SHORT).show();
                hostRoomRegisterLocationFragment.setFm(fragmentManager);
                transaction.add(R.id.start_layout, hostRoomRegisterLocationFragment).commit();
                break;
        }
    }

    // StreeAddressFragment(Google Place API)로 부터 결과 객체(Place)받기 위해 callback interface implements
    @Override
    public void resultCallBack(Place place) {
        resultStreetAddress.setText(place.getName());
        hostRoomRegisterLocationFragment.setPlace(place);
    }
}
