package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicAmenitiesFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private CheckBox checkBox1, checkBox3, checkBox5, checkBox6, checkBox7, checkBox8, checkBox10, checkBox11, checkBox12, checkBox15, checkBox17, checkBox19, checkBox20, checkBox22, checkBox23;
    private TextView txtTitle, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16, textView17, textView18, textView19, textView20, textView21, textView22, textView23;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private View view = null;


    public HostRoomsRegisterBasicAmenitiesFragment() {
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
            view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_amenities, container, false);
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
        checkBox6 = (CheckBox) view.findViewById(R.id.checkBox6);
        textView7 = (TextView) view.findViewById(R.id.textView7);
        checkBox7 = (CheckBox) view.findViewById(R.id.checkBox7);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        checkBox8 = (CheckBox) view.findViewById(R.id.checkBox8);
        textView9 = (TextView) view.findViewById(R.id.detail_house_more_calendar);
        textView10 = (TextView) view.findViewById(R.id.textView10);
        checkBox10 = (CheckBox) view.findViewById(R.id.checkBox10);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        checkBox11 = (CheckBox) view.findViewById(R.id.checkBox11);
        textView12 = (TextView) view.findViewById(R.id.textView12);
        checkBox12 = (CheckBox) view.findViewById(R.id.checkBox12);
        textView13 = (TextView) view.findViewById(R.id.textView13);
        textView14 = (TextView) view.findViewById(R.id.textView14);
        textView15 = (TextView) view.findViewById(R.id.textView15);
        checkBox15 = (CheckBox) view.findViewById(R.id.checkBox15);
        textView16 = (TextView) view.findViewById(R.id.textView16);
        textView17 = (TextView) view.findViewById(R.id.textView17);
        checkBox17 = (CheckBox) view.findViewById(R.id.checkBox17);
        textView18 = (TextView) view.findViewById(R.id.textView18);
        textView19 = (TextView) view.findViewById(R.id.textView19);
        checkBox19 = (CheckBox) view.findViewById(R.id.checkBox19);
        textView20 = (TextView) view.findViewById(R.id.textView20);
        checkBox20 = (CheckBox) view.findViewById(R.id.checkBox20);
        textView21 = (TextView) view.findViewById(R.id.textView21);
        textView22 = (TextView) view.findViewById(R.id.textView22);
        checkBox22 = (CheckBox) view.findViewById(R.id.checkBox22);
        textView23 = (TextView) view.findViewById(R.id.textView23);
        checkBox23 = (CheckBox) view.findViewById(R.id.checkBox23);
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
                hostRoomsRegisterBasicActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                goHostRoomsRegisterBasicSpaceFragment();
                break;
        }
    }

    private void goHostRoomsRegisterBasicSpaceFragment(){
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicSpaceFragment)
                .addToBackStack(null)
                .commit();
    }
}
