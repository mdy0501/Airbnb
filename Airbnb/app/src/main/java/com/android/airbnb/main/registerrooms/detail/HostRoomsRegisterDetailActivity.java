package com.android.airbnb.main.registerrooms.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.airbnb.R;

/**
 * 숙소등록 2단계 (상세정보)
 */
public class HostRoomsRegisterDetailActivity extends AppCompatActivity {

    HostRoomsRegisterDetailImageFragment hostRoomsRegisterDetailImageFragment;
    HostRoomsRegisterDetailIntroduceFragment hostRoomsRegisterDetailIntroduceFragment;
    HostRoomsRegisterDetailTitleFragment hostRoomsRegisterDetailTitleFragment;
    HostRoomsRegisterDetailSpaceInfoFragment hostRoomsRegisterDetailSpaceInfoFragment;
    HostRoomsRegisterDetailGuestAccessFragment hostRoomsRegisterDetailGuestAccessFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_rooms_register_detail);
        setFragments();
        addFirstFragment();
    }

    private void setFragments(){
        hostRoomsRegisterDetailImageFragment = new HostRoomsRegisterDetailImageFragment();
        hostRoomsRegisterDetailIntroduceFragment = new HostRoomsRegisterDetailIntroduceFragment();
        hostRoomsRegisterDetailTitleFragment = new HostRoomsRegisterDetailTitleFragment();
        hostRoomsRegisterDetailSpaceInfoFragment = new HostRoomsRegisterDetailSpaceInfoFragment();
        hostRoomsRegisterDetailGuestAccessFragment = new HostRoomsRegisterDetailGuestAccessFragment();
    }

    private void addFirstFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detailRoomsRegisterContainer, hostRoomsRegisterDetailImageFragment)
                .commit();
    }

}
