package com.android.airbnb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class HostInfoActivity extends AppCompatActivity {

    private ImageView hostImg;
    private TextView hostInfoReviewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_info);
        initView();
        setImg();
        setOnClick();
    }

    private void initView() {
        hostImg = (ImageView) findViewById(R.id.hostInfo_hostImg);
        hostInfoReviewAll = (TextView) findViewById(R.id.hostInfo_review_all);
    }

    private void setOnClick(){
        hostInfoReviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostInfoActivity.this, ReviewActivity.class);
                v.getContext().startActivity(intent);

            }
        });
    }

    private void setImg() {
        Glide
                .with(getBaseContext())
                .load(R.mipmap.dummy_host_img)
                .into(hostImg);
    }
}
