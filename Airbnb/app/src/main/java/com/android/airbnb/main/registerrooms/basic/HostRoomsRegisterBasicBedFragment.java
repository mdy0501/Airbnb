package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;

import static com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity.hostingHouse;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicBedFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private TextView txtTitle, txtDetail, txtDetailContent, txtAddBed;
    private ImageButton ImgBtnNext;
    private ImageButton ImgBtnBack;
    private View view = null;

    public HostRoomsRegisterBasicBedFragment() {
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
            view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_bed, container, false);
        }
        setViews(view);
        setListeners();
        txtDetailContent.setText("킹사이즈 침대 " + hostingHouse.getKingBeds() + "대, " + "퀸사이즈 침대 " + hostingHouse.getQueenBeds() + "대, " + "더블사이즈 침대 " + hostingHouse.getDoubleBeds() + "대, " + "싱글사이즈 침대 " + hostingHouse.getSingleBeds() + "대" );
        return view;
    }

    private void setViews(View view) {
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtAddBed = (TextView) view.findViewById(R.id.txtAddBed);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnSave);
        txtDetail = (TextView) view.findViewById(R.id.txtDetail);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnCancel);
        txtDetailContent = (TextView) view.findViewById(R.id.txtDetailContent);
    }

    private void setListeners(){
        txtAddBed.setOnClickListener(this);
        ImgBtnBack.setOnClickListener(this);
        ImgBtnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtAddBed:
                hostRoomsRegisterBasicActivity.getSupportFragmentManager().popBackStack();
                goHostRoomsRegisterBasicBedDetailFragment();
                break;
            case R.id.ImgBtnCancel:
                hostRoomsRegisterBasicActivity.onBackPressed();
                break;
            case R.id.ImgBtnSave:
                goHostRoomsRegisterBasicBathFragment();
                break;
        }
    }

    private void goHostRoomsRegisterBasicBathFragment(){
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicBathFragment)
                .addToBackStack(null)
                .commit();
    }

    private void goHostRoomsRegisterBasicBedDetailFragment(){
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicBedDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}