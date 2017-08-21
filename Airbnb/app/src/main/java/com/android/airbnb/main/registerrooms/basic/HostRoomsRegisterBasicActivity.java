package com.android.airbnb.main.registerrooms.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.airbnb.R;

public class HostRoomsRegisterBasicActivity extends AppCompatActivity {

    HostRoomsRegisterBasicTypeFragment hostRoomsRegisterBasicTypeFragment;
    HostRoomsRegisterBasicAvailableFragment hostRoomsRegisterBasicAvailableFragment;
    HostRoomsRegisterBasicBedFragment hostRoomsRegisterBasicBedFragment;
    HostRoomsRegisterBasicBedDetailFragment hostRoomsRegisterBasicBedDetailFragment;
    HostRoomsRegisterBasicBathFragment hostRoomsRegisterBasicBathFragment;
    HostRoomsRegisterBasicAddressFragment hostRoomsRegisterBasicAddressFragment;
    HostRoomsRegisterBasicStreetAddressFragment hostRoomsRegisterBasicStreetAddressFragment;
    HostRoomsRegisterBasicLocationFragment hostRoomsRegisterBasicLocationFragment;
    HostRoomsRegisterBasicAmenitiesFragment hostRoomsRegisterBasicAmenitiesFragment;
    HostRoomsRegisterBasicSpaceFragment hostRoomsRegisterBasicSpaceFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_rooms_register_basic);
        setFragments();
        addFirstFragment();
    }

    private void setFragments(){
        hostRoomsRegisterBasicTypeFragment = new HostRoomsRegisterBasicTypeFragment();
        hostRoomsRegisterBasicAvailableFragment = new HostRoomsRegisterBasicAvailableFragment();
        hostRoomsRegisterBasicBedFragment = new HostRoomsRegisterBasicBedFragment();
        hostRoomsRegisterBasicBedDetailFragment = new HostRoomsRegisterBasicBedDetailFragment();
        hostRoomsRegisterBasicBathFragment = new HostRoomsRegisterBasicBathFragment();
        hostRoomsRegisterBasicAddressFragment = new HostRoomsRegisterBasicAddressFragment();
        hostRoomsRegisterBasicStreetAddressFragment = new HostRoomsRegisterBasicStreetAddressFragment();
        hostRoomsRegisterBasicLocationFragment = new HostRoomsRegisterBasicLocationFragment();
        hostRoomsRegisterBasicAmenitiesFragment = new HostRoomsRegisterBasicAmenitiesFragment();
        hostRoomsRegisterBasicSpaceFragment = new HostRoomsRegisterBasicSpaceFragment();
    }

    private void addFirstFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicTypeFragment)
                .commit();
    }
}
