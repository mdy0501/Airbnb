package com.android.airbnb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button goMaps;
    private Button btnGoWelcome;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setOnClick();
    }

    private void setOnClick(){
        goMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoogleMapViewPagerActivity.class);
                startActivity(intent);

            }
        });

        btnGoWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        goMaps = (Button) findViewById(R.id.goMaps);
        btnGoWelcome = (Button) findViewById(R.id.btnGoWelcome);
    }
}
