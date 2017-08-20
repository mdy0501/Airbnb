package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import static com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity.hostingHouse;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicBathFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private ImageButton ImgBtnNext, ImgBtnCancel;
    private RadioButton radioButtonPrivate, radioButtonPublic;
    private TextView txtTitle, txtBathTitle, txtPrivateBath, txtPublicBath;
    private Spinner spinnerBath;
    private View view = null;

    ArrayAdapter bathAdapter;

    public HostRoomsRegisterBasicBathFragment() {
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
            view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_bath, container, false);
        }
        setViews(view);
        setListeners();
        setSpinner();
        return view;
    }

    private void setViews(View view) {
        spinnerBath = (Spinner) view.findViewById(R.id.spinnerBath);
        txtBathTitle = (TextView) view.findViewById(R.id.txtBathTitle);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        txtPrivateBath = (TextView) view.findViewById(R.id.txtPrivateBath);
        txtPublicBath = (TextView) view.findViewById(R.id.txtPublicBath);
        radioButtonPrivate = (RadioButton) view.findViewById(R.id.radioButtonPrivate);
        radioButtonPublic = (RadioButton) view.findViewById(R.id.radioButtonPublic);
        ImgBtnCancel = (ImageButton) view.findViewById(R.id.ImgBtnCancel);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    private void setListeners(){
        ImgBtnCancel.setOnClickListener(this);
        ImgBtnNext.setOnClickListener(this);
    }

    private void setSpinner(){
        // 욕실개수 Adapter 및 Spinner
        bathAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bath, android.R.layout.simple_spinner_item);
        bathAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBath.setAdapter(bathAdapter);
        spinnerBath.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        hostingHouse.setBathrooms("0");
                        Log.e("화장실개수 :: ", hostingHouse.getBathrooms()+"");
                        break;
                    case 1:
                        hostingHouse.setBathrooms("1");
                        Log.e("화장실개수 :: ", hostingHouse.getBathrooms()+"");
                        break;
                    case 2:
                        hostingHouse.setBathrooms("2");
                        Log.e("화장실개수 :: ", hostingHouse.getBathrooms()+"");
                        break;
                    case 3:
                        hostingHouse.setBathrooms("3");
                        Log.e("화장실개수 :: ", hostingHouse.getBathrooms()+"");
                        break;
                    case 4:
                        hostingHouse.setBathrooms("4");
                        Log.e("화장실개수 :: ", hostingHouse.getBathrooms()+"");
                        break;
                    case 5:
                        hostingHouse.setBathrooms("5");
                        Log.e("화장실개수 :: ", hostingHouse.getBathrooms()+"");
                        break;
                    case 6:
                        hostingHouse.setBathrooms("6");
                        Log.e("화장실개수 :: ", hostingHouse.getBathrooms()+"");
                        break;
                    case 7:
                        hostingHouse.setBathrooms("7");
                        Log.e("화장실개수 :: ", hostingHouse.getBathrooms()+"");
                        break;
                    case 8:
                        hostingHouse.setBathrooms("8");
                        Log.e("화장실개수 :: ", hostingHouse.getBathrooms()+"");
                        break;
                }


                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnCancel:
                hostRoomsRegisterBasicActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                goHostRoomsRegisterBasicAddressFragment();
                break;
        }
    }

    private void goHostRoomsRegisterBasicAddressFragment(){
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicAddressFragment)
                .addToBackStack(null)
                .commit();
    }
}
