package com.android.airbnb.main.registerrooms.prepare;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterPrepareRequisiteFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterPrepareActivity hostRoomsRegisterPrepareActivity;
    private TextView txtTitle, txtTitleDescription, textView1, textView2, textView3, textView4, textView5, textView6;
    private ImageView imgCheck1, imgCheck2, imgCheck3, imgCheck4, imgCheck5, imgCheck6;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private View view = null;

    public HostRoomsRegisterPrepareRequisiteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hostRoomsRegisterPrepareActivity = (HostRoomsRegisterPrepareActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_host_rooms_register_prepare_requisite, container, false);
        }
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view) {
        textView1 = (TextView) view.findViewById(R.id.textView1);
        imgCheck1 = (ImageView) view.findViewById(R.id.imgCheck1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        imgCheck2 = (ImageView) view.findViewById(R.id.imgCheck2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        imgCheck3 = (ImageView) view.findViewById(R.id.imgCheck3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        imgCheck4 = (ImageView) view.findViewById(R.id.imgCheck4);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        imgCheck5 = (ImageView) view.findViewById(R.id.imgCheck5);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        imgCheck6 = (ImageView) view.findViewById(R.id.imgCheck6);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        txtTitleDescription = (TextView) view.findViewById(R.id.txtTitleDescription);
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
                hostRoomsRegisterPrepareActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                goHostRoomsRegisterDetailIntroduceFragment();
                break;
        }
    }

    private void goHostRoomsRegisterDetailIntroduceFragment() {
        hostRoomsRegisterPrepareActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.prepareRoomsRegisterContainer, hostRoomsRegisterPrepareActivity.hostRoomsRegisterPreparePriceFragment)
                .addToBackStack(null)
                .commit();
    }
}
