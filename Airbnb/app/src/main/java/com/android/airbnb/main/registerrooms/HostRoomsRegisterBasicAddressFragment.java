package com.android.airbnb.main.registerrooms;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;
import com.google.android.gms.location.places.Place;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicAddressFragment extends Fragment implements View.OnClickListener, HostRoomsRegisterBasicStreetAddressFragment.OnStreetAddressCallBack{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private TextView txtTitle, txtNation, txtNationContent, txtSi, txtGu, txtDoromyung, txtDoromyungContent, txtDong, txtZipCode;
    private EditText editSi, editGu, editDong, editZipCode;
    private ConstraintLayout layoutDoromyung;
    private View view = null;

    public HostRoomsRegisterBasicAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hostRoomsRegisterBasicActivity = (HostRoomsRegisterBasicActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_address, container, false);
        }
        setViews(view);
        setListeners();
        hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicStreetAddressFragment.setmStreetAddressListener(this);
        return view;
    }

    private void setViews(View view) {
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnBack);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtNation = (TextView) view.findViewById(R.id.txtNation);
        txtNationContent = (TextView) view.findViewById(R.id.txtNationContent);
        editSi = (EditText) view.findViewById(R.id.editSi);
        txtSi = (TextView) view.findViewById(R.id.txtSi);
        editGu = (EditText) view.findViewById(R.id.editGu);
        txtGu = (TextView) view.findViewById(R.id.txtGu);
        txtDoromyung = (TextView) view.findViewById(R.id.txtDoromyung);
        txtDoromyungContent = (TextView) view.findViewById(R.id.txtDoromyungContent);
        editDong = (EditText) view.findViewById(R.id.editDong);
        txtDong = (TextView) view.findViewById(R.id.txtDong);
        editZipCode = (EditText) view.findViewById(R.id.editZipCode);
        txtZipCode = (TextView) view.findViewById(R.id.txtZipCode);
        layoutDoromyung = (ConstraintLayout) view.findViewById(R.id.layoutDoromyung);
    }

    private void setListeners(){
        ImgBtnBack.setOnClickListener(this);
        ImgBtnNext.setOnClickListener(this);
        layoutDoromyung.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnBack:
                hostRoomsRegisterBasicActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                goHostRoomsRegisterBasicLocationFragment();
                break;
            case R.id.layoutDoromyung:
                goStreetAddressFragment();
                break;
        }
    }

    // move StreetAddressFragment
    private void goStreetAddressFragment(){
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicStreetAddressFragment)
                .addToBackStack(null)
                .commit();
    }

    // move HostRoomsRegisterBasicLocationFragment
    private void goHostRoomsRegisterBasicLocationFragment(){
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicLocationFragment)
                .addToBackStack(null)
                .commit();
    }

    // HostRoomsRegisterBasicStreetAddressFragment(Google Place API)로 부터 결과 객체(Place)를 받음.
    @Override
    public void streetAddressCallBack(Place place) {
        txtDoromyungContent.setText(place.getName());
        hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicLocationFragment.setPlace(place);
    }
}
