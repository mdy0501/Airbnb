package com.android.airbnb.main.registerrooms;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicTypeFragment extends Fragment implements View.OnClickListener {

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private TextView txtTypeDescription, txtTypeTitle, txtAllHouse, txtPrivateRoom, txtMultiRoom, txtTitle;
    private Spinner spinnerType;
    private RadioButton radioButtonAll, radioButtonPrivateRoom, radioButtonMultiRoom;
    private ConstraintLayout layoutTitle, layoutDescription, layoutTypeOfBuilding;

    /*String spinnerData[] = {"아파트", "주택", "베드 앤 브랙퍼스트(B&B", "로프트", "오두막", "별장", "성", "기숙사", "트리하우스",
            "보트", "비행기", "캠핑카", "이글루", "등대", "유르트 (몽골의 전통 텐트)", "티피 (원뿔형 천막)", "동굴", "섬",
            "스위스식 오두막", "어스하우스", "기차", "텐트", "기타"};*/

    ArrayAdapter<String> spinnerAdapter;


    public HostRoomsRegisterBasicTypeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_type, container, false);
        setViews(view);
        setListeners();
        setSpinner();
        return view;
    }

    private void setViews(View view) {
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        layoutTypeOfBuilding = (ConstraintLayout) view.findViewById(R.id.layoutTypeOfBuilding);
        txtTypeDescription = (TextView) view.findViewById(R.id.txtTypeDescription);
        txtTypeTitle = (TextView) view.findViewById(R.id.txtTypeTitle);
        layoutDescription = (ConstraintLayout) view.findViewById(R.id.layoutDescription);
        txtAllHouse = (TextView) view.findViewById(R.id.txtAllHouse);
        txtPrivateRoom = (TextView) view.findViewById(R.id.txtPrivateRoom);
        txtMultiRoom = (TextView) view.findViewById(R.id.txtMultiRoom);
        radioButtonAll = (RadioButton) view.findViewById(R.id.radioButtonAll);
        radioButtonPrivateRoom = (RadioButton) view.findViewById(R.id.radioButtonPrivateRoom);
        radioButtonMultiRoom = (RadioButton) view.findViewById(R.id.radioButtonMultiRoom);
        layoutTitle = (ConstraintLayout) view.findViewById(R.id.layoutTitle);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnCancel);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        spinnerType = (Spinner) view.findViewById(R.id.spinnerType);
    }

    private void setListeners() {
        ImgBtnNext.setOnClickListener(this);
        ImgBtnBack.setOnClickListener(this);
    }

    private void setSpinner(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.typeOfBuilding, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("position ", position + "");
                Log.e("id ", id + "");
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ImgBtnCancel:   // move Previous
                hostRoomsRegisterBasicActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:   // move NextStep
                goHostRoomsRegisterBasicAvailableFragment();
                break;
        }
    }


    // move HostRoomsRegisterBasicAvailableFragment
    private void goHostRoomsRegisterBasicAvailableFragment() {
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicAvailableFragment)
                .addToBackStack(null)
                .commit();
    }
}
