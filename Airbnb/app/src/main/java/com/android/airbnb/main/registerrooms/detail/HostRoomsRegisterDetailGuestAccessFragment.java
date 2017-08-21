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
 * 숙소등록 2-5단계 (숙소편의시설현황 등록)
 */
public class HostRoomsRegisterDetailGuestAccessFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterDetailActivity hostRoomsRegisterDetailActivity;

    private View view = null;
    private EditText editGuestAccess;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private TextView txtTitle, txtLimit;

    public HostRoomsRegisterDetailGuestAccessFragment() {
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
            view = inflater.inflate(R.layout.fragment_host_rooms_register_detail_guest_access, container, false);
        }
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view) {
        editGuestAccess = (EditText) view.findViewById(R.id.editGuestAccess);
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
                hostingHouse.setGuest_access(editGuestAccess.getText().toString());
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.detailRoomsRegisterContainer)).commit();
                getActivity().finish();
//                Intent intent = new Intent(getActivity(), HostRoomsRegisterActivity.class);
//                startActivity(intent);
                break;
        }
    }

}
