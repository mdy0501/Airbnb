package com.android.airbnb.main.registerrooms.prepare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.airbnb.R;

/**
 * 숙소등록 3단계 (게스트 맞이할 준비)
 */
public class HostRoomsRegisterPrepareActivity extends AppCompatActivity {

    HostRoomsRegisterPrepareRequisiteFragment hostRoomsRegisterPrepareRequisiteFragment;
    HostRoomsRegisterPreparePriceFragment hostRoomsRegisterPreparePriceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_rooms_register_prepare);
        setFragments();
        addFirstFragment();
    }

    private void setFragments(){
        hostRoomsRegisterPrepareRequisiteFragment = new HostRoomsRegisterPrepareRequisiteFragment();
        hostRoomsRegisterPreparePriceFragment = new HostRoomsRegisterPreparePriceFragment();
    }

    private void addFirstFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.prepareRoomsRegisterContainer, hostRoomsRegisterPrepareRequisiteFragment)
                .commit();
    }
}
