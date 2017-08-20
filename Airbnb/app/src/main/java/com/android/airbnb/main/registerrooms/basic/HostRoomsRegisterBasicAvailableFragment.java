package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.airbnb.R;

import static com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity.hostingHouse;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicAvailableFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private TextView txtPerson, txtTitle, txtBed, txtBedroom;
    private Spinner spinnerPerson, spinnerBedroom, spinnerBed;

    ArrayAdapter personAdapter, bedroomAdapter, bedAdapter;
    private View view = null;


    public HostRoomsRegisterBasicAvailableFragment() {
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
            view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_available, container, false);
        }
        setViews(view);
        setListeners();
        setSpinners();
        return view;
    }

    private void setViews(View view) {
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        spinnerBed = (Spinner) view.findViewById(R.id.spinnerBed);
        txtBed = (TextView) view.findViewById(R.id.txtBed);
        spinnerBedroom = (Spinner) view.findViewById(R.id.spinnerBedroom);
        txtBedroom = (TextView) view.findViewById(R.id.txtBedroom);
        spinnerPerson = (Spinner) view.findViewById(R.id.spinnerPerson);
        txtPerson = (TextView) view.findViewById(R.id.txtKingTitle);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnCancel);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    private void setListeners(){
        ImgBtnBack.setOnClickListener(this);
        ImgBtnNext.setOnClickListener(this);
    }

    private void setSpinners(){
        // 숙박 가능 인원 Adapter 및 Spinner
        personAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.availablePerson, android.R.layout.simple_spinner_item);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPerson.setAdapter(personAdapter);
        spinnerPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*Log.e("position ", position + "");
                Log.e("id ", id + "");
                Log.e("SelectedItem", parent.getSelectedItem()+ "");*/
                // 숙박 가능 인원 저장
                switch (position){
                    case 0:
                        hostingHouse.setAccommodates("1");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 1:
                        hostingHouse.setAccommodates("2");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 2:
                        hostingHouse.setAccommodates("3");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 3:
                        hostingHouse.setAccommodates("4");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 4:
                        hostingHouse.setAccommodates("5");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 5:
                        hostingHouse.setAccommodates("6");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 6:
                        hostingHouse.setAccommodates("7");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 7:
                        hostingHouse.setAccommodates("8");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 8:
                        hostingHouse.setAccommodates("9");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 9:
                        hostingHouse.setAccommodates("10");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 10:
                        hostingHouse.setAccommodates("11");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 11:
                        hostingHouse.setAccommodates("12");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 12:
                        hostingHouse.setAccommodates("13");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 13:
                        hostingHouse.setAccommodates("14");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 14:
                        hostingHouse.setAccommodates("15");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                    case 15:
                        hostingHouse.setAccommodates("16");
                        Log.e("숙박 가능 인원 :: ", hostingHouse.getAccommodates()+"");
                        break;
                }
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 게스트용 침실 Adapter 및 Spinner
        bedroomAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bedroom, android.R.layout.simple_spinner_item);
        bedroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBedroom.setAdapter(bedroomAdapter);
        spinnerBedroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*Log.e("position ", position + "");
                Log.e("id ", id + "");
                Log.e("SelectedItem", parent.getSelectedItem()+ "");*/
                // 게스트용 침실 개수 저장
                switch (position){
                    case 0:
                        hostingHouse.setBedrooms("1");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 1:
                        hostingHouse.setBedrooms("2");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 2:
                        hostingHouse.setBedrooms("3");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 3:
                        hostingHouse.setBedrooms("4");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 4:
                        hostingHouse.setBedrooms("5");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 5:
                        hostingHouse.setBedrooms("6");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 6:
                        hostingHouse.setBedrooms("7");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 7:
                        hostingHouse.setBedrooms("8");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 8:
                        hostingHouse.setBedrooms("9");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 9:
                        hostingHouse.setBedrooms("10");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 10:
                        hostingHouse.setBedrooms("11");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 11:
                        hostingHouse.setBedrooms("12");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 12:
                        hostingHouse.setBedrooms("13");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 13:
                        hostingHouse.setBedrooms("14");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 14:
                        hostingHouse.setBedrooms("15");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                    case 15:
                        hostingHouse.setBedrooms("16");
                        Log.e("게스트용 침실 개수 :: ", hostingHouse.getBedrooms()+"");
                        break;
                }



                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 게스트용 침대 Adapter 및 Spinner
        bedAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bed, android.R.layout.simple_spinner_item);
        bedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBed.setAdapter(bedAdapter);
        spinnerBed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*Log.e("position ", position + "");
                Log.e("id ", id + "");
                Log.e("SelectedItem", parent.getSelectedItem()+ "");*/
                // 게스트용 침대 개수 저장
                switch (position){
                    case 0:
                        hostingHouse.setBeds("1");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 1:
                        hostingHouse.setBeds("2");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 2:
                        hostingHouse.setBeds("3");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 3:
                        hostingHouse.setBeds("4");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 4:
                        hostingHouse.setBeds("5");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 5:
                        hostingHouse.setBeds("6");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 6:
                        hostingHouse.setBeds("7");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 7:
                        hostingHouse.setBeds("8");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 8:
                        hostingHouse.setBeds("9");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 9:
                        hostingHouse.setBeds("10");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 10:
                        hostingHouse.setBeds("11");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 11:
                        hostingHouse.setBeds("12");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 12:
                        hostingHouse.setBeds("13");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 13:
                        hostingHouse.setBeds("14");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 14:
                        hostingHouse.setBeds("15");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                    case 15:
                        hostingHouse.setBeds("16");
                        Log.e("게스트용 침대 개수 :: ", hostingHouse.getBeds()+"");
                        break;
                }
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnCancel:
                hostRoomsRegisterBasicActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                goHostRoomsRegisterBasicBedFragment();
                break;
        }
    }

    private void goHostRoomsRegisterBasicBedFragment(){
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicBedFragment)
                .addToBackStack(null)
                .commit();
    }
}
