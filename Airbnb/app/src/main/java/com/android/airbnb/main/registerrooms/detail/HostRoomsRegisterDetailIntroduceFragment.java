package com.android.airbnb.main.registerrooms.detail;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;

import static com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity.hostingHouse;

/**
 * 숙도등록 2-2단계 (숙소소개 등록)
 */
public class HostRoomsRegisterDetailIntroduceFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterDetailActivity hostRoomsRegisterDetailActivity;
    private TextView txtTitle, txtLimit;
    private EditText editIntroduce;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private View view = null;

    public HostRoomsRegisterDetailIntroduceFragment() {
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
            view = inflater.inflate(R.layout.fragment_host_rooms_register_detail_introduce, container, false);
        }
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view) {
        editIntroduce = (EditText) view.findViewById(R.id.editIntroduce);
        txtLimit = (TextView) view.findViewById(R.id.txtLimit);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnBack);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    private void setListeners(){
        ImgBtnBack.setOnClickListener(this);
        ImgBtnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnBack:
                hostRoomsRegisterDetailActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                hostingHouse.setIntroduce(editIntroduce.getText().toString());
                goHostRoomsRegisterDetailTitleFragment();
                break;
        }
    }

    private void goHostRoomsRegisterDetailTitleFragment() {
        hostRoomsRegisterDetailActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.detailRoomsRegisterContainer, hostRoomsRegisterDetailActivity.hostRoomsRegisterDetailTitleFragment)
                .addToBackStack(null)
                .commit();
    }
}
