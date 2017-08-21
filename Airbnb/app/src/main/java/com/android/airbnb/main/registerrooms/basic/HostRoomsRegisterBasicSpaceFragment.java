package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicSpaceFragment extends Fragment implements View.OnClickListener {

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private CheckBox checkBox1, checkBox3, checkBox5, checkBox7, checkBox8, checkBox9, checkBox11, checkBox12;
    private ImageButton ImgBtnNext, ImgBtnBack;
    private TextView txtTitle, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13;
    private View view = null;


    public HostRoomsRegisterBasicSpaceFragment() {
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
            view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_space, container, false);
        }
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view) {
        checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
        textView1 = (TextView) view.findViewById(R.id.txtLimit);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        checkBox3 = (CheckBox) view.findViewById(R.id.checkBox3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        checkBox5 = (CheckBox) view.findViewById(R.id.checkBox5);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView7 = (TextView) view.findViewById(R.id.textView7);
        checkBox7 = (CheckBox) view.findViewById(R.id.checkBox7);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        checkBox8 = (CheckBox) view.findViewById(R.id.checkBox8);
        textView9 = (TextView) view.findViewById(R.id.detail_house_more_calendar);
        textView10 = (TextView) view.findViewById(R.id.textView10);
        checkBox9 = (CheckBox) view.findViewById(R.id.checkBox9);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        checkBox11 = (CheckBox) view.findViewById(R.id.checkBox11);
        textView12 = (TextView) view.findViewById(R.id.textView12);
        checkBox12 = (CheckBox) view.findViewById(R.id.checkBox12);
        textView13 = (TextView) view.findViewById(R.id.textView13);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnBack);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    private void setListeners() {
        ImgBtnNext.setOnClickListener(this);
        ImgBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ImgBtnBack:
                hostRoomsRegisterBasicActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                Intent intent = new Intent(getActivity(), HostRoomsRegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
