package com.android.airbnb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class HostInfoActivity extends AppCompatActivity {

    private ImageView hostImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_info);
        initView();
        setImg();
    }

    private void initView() {
        hostImg = (ImageView) findViewById(R.id.hostInfo_hostImg);
    }

    private void setImg(){
        Glide
                .with(getBaseContext())
                .load(R.mipmap.dummy_host_img)
                .into(hostImg);
    }
}
