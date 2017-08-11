package com.android.airbnb.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.airbnb.R;
import com.android.airbnb.util.PreferenceUtil;

import static com.android.airbnb.R.id.guest_main_container;
import static com.android.airbnb.R.id.hostMainTabLayout;

public class GuestMainActivity extends AppCompatActivity {

    private GuestSearchFragment guestSearchFragment;
    private GuestWishListDetailFragment wishFragment;
    private GuestTravelFragment guestTravelFragment;
    private GuestMessageFragment guestMessageFragment;
    private GuestProfileFragment guestProfileFragment;

    private TabLayout guestMainTabLayout;
    private static final int SEARCH = 0;
    private static final int WISH = 1;
    private static final int TRAVEL = 2;
    private static final int MESSAGE = 3;
    private static final int PROFILE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);
        setFragments();
        setViews();
        setListeners();
        Log.e("Main에서 token값 확인", PreferenceUtil.getToken(this));
        Log.e("Main에서 primaryKey값 확인", PreferenceUtil.getPrimaryKey(this));
        Log.e("Main에서 email값 확인", PreferenceUtil.getEmail(this));
    }

    private void setViews(){
        guestMainTabLayout = (TabLayout) findViewById(hostMainTabLayout);
        getSupportFragmentManager().beginTransaction()
                .add(guest_main_container, guestSearchFragment)
                .commit();
    }

    private void setListeners(){

        // TabLayout Listener
        guestMainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.w("test============" , tab.getPosition()+"");
                switch (tab.getPosition()){
                    case SEARCH :
                        getSupportFragmentManager().beginTransaction()
                                .replace(guest_main_container, guestSearchFragment)
                                .commit();
                        break;
                    case WISH :
                        getSupportFragmentManager().beginTransaction()
                                .replace(guest_main_container, wishFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case TRAVEL :
                        getSupportFragmentManager().beginTransaction()
                                .replace(guest_main_container, guestTravelFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case MESSAGE :
                        getSupportFragmentManager().beginTransaction()
                                .replace(guest_main_container, guestMessageFragment)
                                .addToBackStack(null)
                                .commit();
                        break;

                    case PROFILE :
                        getSupportFragmentManager().beginTransaction()
                                .replace(guest_main_container, guestProfileFragment)
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
        guestSearchFragment = new GuestSearchFragment();
        wishFragment = new GuestWishListDetailFragment();
        guestTravelFragment = new GuestTravelFragment();
        guestMessageFragment = new GuestMessageFragment();
        guestProfileFragment = new GuestProfileFragment();
    }

}
