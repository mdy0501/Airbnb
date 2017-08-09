package com.android.airbnb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.airbnb.main.GuestMainActivity;

public class StartActivity extends AppCompatActivity {

    private Button btnGoWelcome, btnGoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        setOnClick();
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
    }

    private void initView() {
        btnGoWelcome = (Button) findViewById(R.id.btnGoWelcome);
        btnGoMain = (Button) findViewById(R.id.btnGoMain);
    }
}

