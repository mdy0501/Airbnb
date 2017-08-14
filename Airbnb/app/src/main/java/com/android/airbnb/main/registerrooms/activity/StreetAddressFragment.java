package com.android.airbnb.main.registerrooms.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class StreetAddressFragment extends Fragment {

    public FragmentManager fm;

    PlaceAutocompleteFragment autocompleteFragment;
    public StreetAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        setListener();
        return view;
    }

    private void setListener(){
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("StreetAddressFragment", "Place name : " + place.getName());
                Log.i("StreetAddressFragment", "Place latlng : " + place.getLatLng());
                Log.i("StreetAddressFragment", "Place id : " + place.getId());
                Log.i("StreetAddressFragment", "Place address : " + place.getAddress());
                Log.i("StreetAddressFragment", "Place locale : " + place.getLocale());
                Log.i("StreetAddressFragment", "Place attributions : " + place.getViewport());
                Log.i("StreetAddressFragment", "Place place price : " + place.getPriceLevel());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("StreetAddressFragment", "An error occurred: " + status);
            }
        });


    }

}
