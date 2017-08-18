package com.android.airbnb.main.registerrooms;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicBedFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private TextView txtDetailTitle, txtDetailContent, txtAddBed, txtContent;
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
        return view;
    }

    private void setViews(View view) {
        txtContent = (TextView) view.findViewById(R.id.txtDetailContent);
        txtAddBed = (TextView) view.findViewById(R.id.txtAddBed);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnSave);
        txtDetailTitle = (TextView) view.findViewById(R.id.txtKingTitle);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnCancel);
        txtDetailTitle = (TextView) view.findViewById(R.id.txtKingTitle);
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