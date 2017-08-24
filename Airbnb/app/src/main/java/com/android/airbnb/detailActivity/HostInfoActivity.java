package com.android.airbnb.detailActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.util.GlideApp;

public class HostInfoActivity extends AppCompatActivity {

    private ImageView hostImg;
    private TextView hostInfoReviewAll;
    private Host host;
    private ImageView hostInfoHostImg;
    private RelativeLayout hostDetailReview;
    private TextView hostInfoLanguage;
    private TextView hostInfoReview;
    private TextView hostInfoHouseCount;
    private TextView hostinfoHostNation;
    private TextView hostInfoHostName;
    private TextView houstInfoIntro;
    private TextView hostInfoSignupDate;
    private TextView hostInfoResponseRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_info);
        getData();
        initView();
        setOnClick();
        setHostInfo(host);

    }

    private void getData() {
        Intent intent = getIntent();
        host = intent.getParcelableExtra(DetailHouseActivity.HOST_INFO);
    }

    private void setHostInfo(Host host) {
        setImg(host.getImg_profile());
        hostInfoLanguage.setText(host.getPreference_language());
        hostInfoHostName.setText(host.getFirst_name() + " " + host.getLast_name());
        houstInfoIntro.setText(host.getIntroduce());
        hostInfoSignupDate.setText(host.getDate_joined());
    }

    private void initView() {
        hostImg = (ImageView) findViewById(R.id.hostInfo_hostImg);
        hostInfoReviewAll = (TextView) findViewById(R.id.hostInfo_review_all);
        hostInfoHostImg = (ImageView) findViewById(R.id.hostInfo_hostImg);
        hostDetailReview = (RelativeLayout) findViewById(R.id.host_detail_review);
        hostInfoLanguage = (TextView) findViewById(R.id.hostInfo_language);
        hostInfoReview = (TextView) findViewById(R.id.hostInfo_review);
        hostInfoHouseCount = (TextView) findViewById(R.id.hostInfo_house_count);
        hostinfoHostNation = (TextView) findViewById(R.id.hostinfo_host_nation);
        hostInfoHostName = (TextView) findViewById(R.id.hostInfo_host_name);
        houstInfoIntro = (TextView) findViewById(R.id.houstInfo_intro);
        hostInfoSignupDate = (TextView) findViewById(R.id.hostInfo_signup_date);
        hostInfoResponseRate = (TextView) findViewById(R.id.hostInfo_responseRate);
    }

    private void setOnClick() {
        hostInfoReviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostInfoActivity.this, ReviewActivity.class);
                v.getContext().startActivity(intent);

            }
        });
    }

    private void setImg(String imgUrl) {
        GlideApp
                .with(getBaseContext())
                .load(imgUrl)
                .placeholder(R.mipmap.dummy_host_img)
                .fallback(R.mipmap.dummy_host_img)
                .into(hostImg);
    }
}
