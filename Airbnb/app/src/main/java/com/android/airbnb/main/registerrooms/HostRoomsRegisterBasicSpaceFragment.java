package com.android.airbnb.main.registerrooms;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicSpaceFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private TextView txtPerson, txtTitle, txtBed, txtBedroom;
    private Spinner spinnerPerson, spinnerBedroom, spinnerBed;

    ArrayAdapter personAdapter, bedroomAdapter, bedAdapter;

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
        View view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_space, container, false);
        setViews(view);
        setListeners();
        setSpinners();
        return view;
    }

    private void setViews(View view) {
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnSave);
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
                Log.e("position ", position + "");
                Log.e("id ", id + "");
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
                Log.e("position ", position + "");
                Log.e("id ", id + "");
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
                Log.e("position ", position + "");
                Log.e("id ", id + "");
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
            case R.id.ImgBtnSave:
                goHostRoomsRegisterBasicBedFragment();
                break;
        }
    }

    private void goHostRoomsRegisterBasicBedFragment(){
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicBedFragment)
                .addToBackStack(null)
                .commit();
    }
}
