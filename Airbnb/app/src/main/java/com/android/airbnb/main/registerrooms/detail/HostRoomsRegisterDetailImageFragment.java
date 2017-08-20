package com.android.airbnb.main.registerrooms.detail;


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
 * 숙소등록 2-1단계 (숙소사진 등록)
 */
public class HostRoomsRegisterDetailImageFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterDetailActivity hostRoomsRegisterDetailActivity;
    private TextView txtTitle, txtLimit;
    private ImageButton ImgBtnRegisterPicture, ImgBtnNext, ImgBtnBack;
    private View view = null;

    public HostRoomsRegisterDetailImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hostRoomsRegisterDetailActivity = (HostRoomsRegisterDetailActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_host_rooms_register_detail_image, container, false);
        }
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view) {
        ImgBtnRegisterPicture = (ImageButton) view.findViewById(R.id.ImgBtnRegisterPicture);
        txtLimit = (TextView) view.findViewById(R.id.txtLimit);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnBack);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    private void setListeners(){
        ImgBtnBack.setOnClickListener(this);
        ImgBtnNext.setOnClickListener(this);
        ImgBtnRegisterPicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnBack:
                hostRoomsRegisterDetailActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                goHostRoomsRegisterDetailIntroduceFragment();
                break;
            case R.id.ImgBtnRegisterPicture:

                break;
        }
    }

    private void goHostRoomsRegisterDetailIntroduceFragment() {
        hostRoomsRegisterDetailActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.detailRoomsRegisterContainer, hostRoomsRegisterDetailActivity.hostRoomsRegisterDetailIntroduceFragment)
                .addToBackStack(null)
                .commit();
    }
}
