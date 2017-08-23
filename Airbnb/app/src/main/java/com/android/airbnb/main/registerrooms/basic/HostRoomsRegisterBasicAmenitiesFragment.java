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
public class HostRoomsRegisterBasicAmenitiesFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private CheckBox checkBox1, checkBox3, checkBox5, checkBox6, checkBox7, checkBox8, checkBox10, checkBox11, checkBox12, checkBox15, checkBox17, checkBox19, checkBox20;
    private TextView txtTitle, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView17, textView19, textView20;
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
        textView17 = (TextView) view.findViewById(R.id.textView17);
        checkBox17 = (CheckBox) view.findViewById(R.id.checkBox17);
        textView19 = (TextView) view.findViewById(R.id.textView19);
        checkBox19 = (CheckBox) view.findViewById(R.id.checkBox19);
        textView20 = (TextView) view.findViewById(R.id.textView20);
        checkBox20 = (CheckBox) view.findViewById(R.id.checkBox20);
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
                /*if(hostingHouse.facilities != null) {
                    hostingHouse.facilities = hostingHouse.facilities.substring(0, hostingHouse.facilities.length() - 2);
                }
                hostingHouse.setAmenities(hostingHouse.facilities);*/
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

    private void setCheckBoxListeners(){


        // 필수 아이템(수건, 침구 등)
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    hostingHouse.addFacilities("Essentials", "Essentials");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Essentials");
//                    hostingHouse.facilities = "Essentials, ";
                }
            }
        });

        // 무선 인터넷
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Wireless_Internet", "Wireless_Internet");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Wireless_Internet");
                }
//                hostingHouse.facilities = hostingHouse.facilities + "Wireless_Internet, ";
            }
        });

        // 샴푸
        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Shampoo", "Shampoo");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Shampoo");
                }
//                hostingHouse.facilities = hostingHouse.facilities + "Shampoo, ";
            }
        });

        // 옷걸이
        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Hangers", "Hangers");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Hangers");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Hangers, ";
            }
        });

        // TV
        checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("TV", "TV");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("TV");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "TV, ";
            }
        });

        // 난방
        checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Heating", "Heating");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Heating");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Heating, ";
            }
        });

        // 에어컨
        checkBox10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Air_conditioning", "Air_conditioning");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Air_conditioning");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Air_conditioning, ";
            }
        });

        // 조식
        checkBox11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Breakfast", "Breakfast");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Breakfast");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Breakfast, ";
            }
        });

        // 실내 벽난로
        checkBox12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Indoor_fireplace", "Indoor_fireplace");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Indoor_fireplace");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Indoor_fireplace, ";
            }
        });

        // 애완동물 동행 허용
        checkBox15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Pets_allowed", "Pets_allowed");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Pets_allowed");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Pets_allowed, ";
            }
        });

        // 흡연 허용
        checkBox17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Smoking_allowed", "Smoking_allowed");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Smoking_allowed");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Smoking_allowed, ";
            }
        });

        // 휠체어 접근 가능
        checkBox19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Wheelchair_accessible", "Wheelchair_accessible");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Wheelchair_accessible");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Wheelchair_accessible, ";
            }
        });

        // 현관 안내인(doorman)
        checkBox20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hostingHouse.addFacilities("Doorman", "Doorman");
                } else if(isChecked == false){
                    hostingHouse.removeFacilities("Doorman");
                }
//                    hostingHouse.facilities = hostingHouse.facilities + "Doorman, ";
            }
        });
    }
}
