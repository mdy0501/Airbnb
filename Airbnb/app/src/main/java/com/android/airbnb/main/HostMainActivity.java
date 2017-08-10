package com.android.airbnb.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.android.airbnb.R;

public class HostMainActivity extends AppCompatActivity {

    private HostMessageFragment hostMessageFragment;
    private HostCalendarFragment hostCalendarFragment;
    private HostRoomsFragment hostRoomsFragment;
    private HostStatisticsFragment hostStatisticsFragment;
    private HostProfileFragment hostProfileFragment;

    private TabLayout mainTabLayout;
    private static final int MESSAGE = 0;
    private static final int CALENDAR = 1;
    private static final int ROOMS = 2;
    private static final int STATISTICS = 3;
    private static final int PROFILE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_main);
        setFragments();
        setViews();
        setListeners();


    }

    private void setViews(){

    }

    private void setListeners(){

    }

    private void setFragments(){
        hostMessageFragment = new HostMessageFragment();
        hostCalendarFragment = new HostCalendarFragment();
        hostRoomsFragment = new HostRoomsFragment();
        hostStatisticsFragment = new HostStatisticsFragment();
        hostProfileFragment = new HostProfileFragment();
    }
}
