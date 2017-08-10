package com.android.airbnb.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.airbnb.R;

import static com.android.airbnb.R.id.host_main_container;

public class HostMainActivity extends AppCompatActivity {

    private HostMessageFragment hostMessageFragment;
    private HostCalendarFragment hostCalendarFragment;
    private HostRoomsFragment hostRoomsFragment;
    private HostStatisticsFragment hostStatisticsFragment;
    private HostProfileFragment hostProfileFragment;

    private TabLayout hostMainTabLayout;
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
        hostMainTabLayout = (TabLayout) findViewById(R.id.hostMainTabLayout);
        getSupportFragmentManager().beginTransaction()
                .add(host_main_container, hostMessageFragment)
                .commit();
    }

    private void setListeners(){

        // TabLayout Listener
        hostMainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("HostMainAct tabPosition", tab.getPosition()+"");
                switch (tab.getPosition()){
                    case MESSAGE:
                        getSupportFragmentManager().beginTransaction()
                                .replace(host_main_container, hostMessageFragment)
                                .commit();
                        break;
                    case CALENDAR:
                        getSupportFragmentManager().beginTransaction()
                                .replace(host_main_container, hostCalendarFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case ROOMS:
                        getSupportFragmentManager().beginTransaction()
                                .replace(host_main_container, hostRoomsFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case STATISTICS:
                        getSupportFragmentManager().beginTransaction()
                                .replace(host_main_container, hostStatisticsFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case PROFILE:
                        getSupportFragmentManager().beginTransaction()
                                .replace(host_main_container, hostProfileFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setFragments(){
        hostMessageFragment = new HostMessageFragment();
        hostCalendarFragment = new HostCalendarFragment();
        hostRoomsFragment = new HostRoomsFragment();
        hostStatisticsFragment = new HostStatisticsFragment();
        hostProfileFragment = new HostProfileFragment();
    }

}
