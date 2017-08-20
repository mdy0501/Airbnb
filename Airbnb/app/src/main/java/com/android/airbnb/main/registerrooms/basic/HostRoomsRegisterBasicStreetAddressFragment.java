package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.airbnb.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicStreetAddressFragment extends Fragment {

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
//    private android.widget.fragment placeAutocompleteFragment;
    private TextView txtTitle;


    private OnStreetAddressCallBack mStreetAddressListener;
    PlaceAutocompleteFragment autocompleteFragment;

    private View view = null;

    public HostRoomsRegisterBasicStreetAddressFragment() {
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
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_street_address, container, false);
        }
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view) {
        autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    private void setListeners(){
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("StreetAddressFragment", "Place name : " + place.getName());          // 도로명 주소
                Log.i("StreetAddressFragment", "Place latlng : " + place.getLatLng());      // 위도, 경도
                Log.i("StreetAddressFragment", "Place lat : " + place.getLatLng().latitude);      // 위도
                Log.i("StreetAddressFragment", "Place lng : " + place.getLatLng().longitude);      // 경도
                Log.i("StreetAddressFragment", "Place id : " + place.getId());
                Log.i("StreetAddressFragment", "Place address : " + place.getAddress());    // 전체 주소
                Log.i("StreetAddressFragment", "Place locale : " + place.getLocale());
                Log.i("StreetAddressFragment", "Place attributions : " + place.getViewport());
                Log.i("StreetAddressFragment", "Place place price : " + place.getPriceLevel());



                mStreetAddressListener.streetAddressCallBack(place);
//                hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction().remove(HostRoomsRegisterBasicStreetAddressFragment.this).commit();
                hostRoomsRegisterBasicActivity.getSupportFragmentManager().popBackStack();

            }

            @Override
            public void onError(Status status) {
                Log.i("StreetAddressFragment", "An error occurred: " + status);
            }
        });
    }

    public void setmStreetAddressListener(OnStreetAddressCallBack mStreetAddressListener){
        this.mStreetAddressListener = mStreetAddressListener;
    }

    // HostRoomsRegisterBasicAddressFragment에 결과로 받은 객체 전달하기 위해 인터페이스 작성
    public interface OnStreetAddressCallBack{
        public void streetAddressCallBack(Place place);
    }
}
