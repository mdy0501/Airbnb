package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.google.android.gms.location.places.Place;

import static com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity.hostingHouse;

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
    private String address = null;

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
                // TODO - 주소 입력안하고 '다음'버튼 누를 경우, 예외처리 해줘야함.
                if(address.equals("")){
                    Toast.makeText(hostRoomsRegisterBasicActivity, "주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 숙소 주소 저장
                    address = address + " " + editDong.getText().toString();
                    hostingHouse.setAddress(address);
                    Log.e("주소 111 ", address);
                    goHostRoomsRegisterBasicLocationFragment();
                }
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
        Log.e("주소 Fragment", "주소 Fragment");

        Log.i("StreetAddressFragment", "Place name : " + place.getName());          // 도로명 주소
        Log.i("StreetAddressFragment", "Place latlng : " + place.getLatLng());      // 위도, 경도
        Log.i("StreetAddressFragment", "Place lat : " + place.getLatLng().latitude);      // 위도
        Log.i("StreetAddressFragment", "Place lng : " + place.getLatLng().longitude);      // 경도
        Log.i("StreetAddressFragment", "Place id : " + place.getId());
        Log.i("StreetAddressFragment", "Place address : " + place.getAddress());    // 전체 주소
        Log.i("StreetAddressFragment", "Place locale : " + place.getLocale());
        Log.i("StreetAddressFragment", "Place attributions : " + place.getViewport());
        Log.i("StreetAddressFragment", "Place place price : " + place.getPriceLevel());

        // 숙소 주소 저장
        address = place.getAddress()+"";
        // 숙소 위도 저장
        hostingHouse.setLatitude(place.getLatLng().latitude+"");
        // 숙소 경도 저장
        hostingHouse.setLongitude(place.getLatLng().longitude+"");
    }
}
