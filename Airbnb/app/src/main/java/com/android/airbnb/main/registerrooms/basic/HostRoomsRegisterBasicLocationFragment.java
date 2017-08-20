package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicLocationFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private TextView txtTitle;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private ConstraintLayout layoutTitle;
    private SupportMapFragment locationMapFragment;
    private GoogleMap map;
    private Place place;
    private View view = null;


    public HostRoomsRegisterBasicLocationFragment() {
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
            view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_location, container, false);
        }
        setViews(view);
        setListeners();
        setMap();
        return view;
    }

    private void setViews(View view) {
        layoutTitle = (ConstraintLayout) view.findViewById(R.id.layoutTitle);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnBack);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);

        /* fragment가 품고 있는 fragment를 띄울 때(교체 x), getChildFragmentManager를 호출한다. */
        locationMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.locationMapFragment);
    }

    private void setListeners(){
        ImgBtnBack.setOnClickListener(this);
        ImgBtnNext.setOnClickListener(this);
    }

    private void setMap(){
        locationMapFragment.getMapAsync((OnMapReadyCallback) HostRoomsRegisterBasicLocationFragment.this);
    }

    public void setPlace(Place place){
        this.place = place;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

            // draw circle using CircleOptions
            map.addCircle(new CircleOptions().center(place.getLatLng()).strokeColor(R.color.black).radius(150)
                    .strokeWidth(7f).fillColor(getActivity().getResources().getColor(R.color.map_circle)));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15f));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnBack:
                hostRoomsRegisterBasicActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                goHostRoomsRegisterBasicAmenitiesFragment();
                break;
        }
    }

    private void goHostRoomsRegisterBasicAmenitiesFragment(){
        hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicAmenitiesFragment)
                .addToBackStack(null)
                .commit();
    }

}
