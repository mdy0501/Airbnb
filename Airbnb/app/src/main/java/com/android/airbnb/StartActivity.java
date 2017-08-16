package com.android.airbnb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.airbnb.main.GuestMainActivity;
import com.android.airbnb.main.registerrooms.activity.HostRoomRegisterAddressFragment;

public class StartActivity extends AppCompatActivity {

    private Button btnGoWelcome, btnGoMain;
    // test 위해서 별도 추가 by. 준희
    private Button btnGoAddress;
    public HostRoomRegisterAddressFragment hostRoomRegisterAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        setOnClick();
        hostRoomRegisterAddress = new HostRoomRegisterAddressFragment();
    }

    private void setOnClick() {
        btnGoWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        btnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, GuestMainActivity.class);
                startActivity(intent);
            }
        });

        btnGoAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                hostRoomRegisterAddress.fragmentManager = fm;
                android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                transaction.add(R.id.start_layout, hostRoomRegisterAddress);
                transaction.commit();

                btnGoAddress.setVisibility(View.INVISIBLE);
                btnGoMain.setVisibility(View.INVISIBLE);
                btnGoWelcome.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initView() {
        btnGoWelcome = (Button) findViewById(R.id.btnGoWelcome);
        btnGoMain = (Button) findViewById(R.id.btnGoMain);
        btnGoAddress = (Button) findViewById(R.id.btnGoAddress);
    }
}

