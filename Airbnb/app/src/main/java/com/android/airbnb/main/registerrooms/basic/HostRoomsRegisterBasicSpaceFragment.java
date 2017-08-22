package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;

import static com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity.hostingHouse;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicSpaceFragment extends Fragment implements View.OnClickListener {

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private CheckBox checkBox1, checkBox3, checkBox5, checkBox7, checkBox8, checkBox9, checkBox12;
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
        setCheckBoxListeners();
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
        textView9 = (TextView) view.findViewById(R.id.textView9);
        textView10 = (TextView) view.findViewById(R.id.textView10);
        checkBox9 = (CheckBox) view.findViewById(R.id.checkBox9);
        textView11 = (TextView) view.findViewById(R.id.textView11);
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


                /*
                if(hostingHouse.facilities != null){
                    hostingHouse.facilities = hostingHouse.facilities.substring(0, hostingHouse.facilities.length() - 2);
                }
                hostingHouse.setAmenities(hostingHouse.facilities);
                */

                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.basicRoomsRegisterContainer)).commit();
                getActivity().finish();
                break;
        }
    }

    private void setCheckBoxListeners(){
        /*if(hostingHouse.facilities != null){
            hostingHouse.facilities = hostingHouse.facilities + ", ";
        }*/

        // 부엌
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Kitchen", "Kitchen");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Kitchen");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Kitchen, ";
            }
        });

        // 세탁 - 세탁기
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Washer", "Washer");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Washer");
                }
                /*if(isChecked){
                    hostingHouse.facilities = hostingHouse.facilities + "Washer, ";
                }*/
            }
        });

        // 세탁 - 건조기
        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Dryer", "Dryer");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Dryer");
                }
                /*if(isChecked){
                    hostingHouse.facilities = hostingHouse.facilities + "Dryer, ";
                }*/
            }
        });

        // 건물 내 무료 주차
        checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Free_parking", "Free_parking");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Free_parking");
                }
                /*if(isChecked){
                    hostingHouse.facilities = hostingHouse.facilities + "Free_parking, ";
                }*/
            }
        });

        // 엘리베이터
        checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Elevator", "Elevator");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Elevator");
                }
                /*if(isChecked){
                    hostingHouse.facilities = hostingHouse.facilities + "Elevator, ";
                }*/
            }
        });

        // 수영장
        checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Pool", "Pool");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Pool");
                }
                /*if(isChecked){
                    hostingHouse.facilities = hostingHouse.facilities + "Pool, ";
                }*/
            }
        });

        // 헬스장
        checkBox12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Gym", "Gym");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Gym");
                }
                /*if(isChecked){
                    hostingHouse.facilities = hostingHouse.facilities + "Gym, ";
                }*/
            }
        });
    }
}
