package com.android.airbnb.main.registerrooms.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
public class HostRoomRegisterLocationFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private FragmentManager fm;
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private View view = null;
    private Context mContext;
    private FloatingActionButton btnNext;
    private TextView btnSaveClose;
    private ImageView btnBack;
    private Place place;


    public HostRoomRegisterLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null)
            view = inflater.inflate(R.layout.fragment_host_room_register_location, container, false);
        initView(view);

        /* fragment가 품고 있는 fragment를 띄울 때(교체 x), getChildFragmentManager를 호출한다. */
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(HostRoomRegisterLocationFragment.this);
        return view;
    }

    public FragmentManager getFm() {
        return fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        // draw circle using CircleOptions
        map.addCircle(new CircleOptions().center(place.getLatLng()).strokeColor(R.color.black).radius(150)
                .strokeWidth(7f).fillColor(getActivity().getResources().getColor(R.color.map_circle)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15f));
    }

    private void initView(View view) {
        btnNext = (FloatingActionButton) view.findViewById(R.id.btn_next);
        btnSaveClose = (TextView) view.findViewById(R.id.btn_save_close);
        btnBack = (ImageView) view.findViewById(R.id.btn_back);
        setListner();
    }

    private void setListner(){
        // clickable setting
        btnNext.setClickable(true);
        btnSaveClose.setClickable(true);
        btnBack.setClickable(true);

        // onClickListener setting
        btnNext.setOnClickListener(this);
        btnSaveClose.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                Toast.makeText(v.getContext(), "next clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_save_close:
                Toast.makeText(v.getContext(), "save & close clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_back:
                // TODO fragment간 통신 Error 생김
                Toast.makeText(v.getContext(), "back clicked", Toast.LENGTH_SHORT).show();
                fm.beginTransaction().remove(this).commit();
                break;
        }
    }
}
