package com.android.airbnb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.airbnb.adapter.DetailImgPager;
import com.android.airbnb.domain.House;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailHouseActivity extends AppCompatActivity {

    ViewPager viewPager;
    CircleIndicator indicator;
    PagerAdapter mAdapter;
    private ImageView detailHostImg;
    private House house;
    private List<House> houseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_house);

        setViewPager();
        setPagerIndicator();
        initView();
        setOnClick();
    }

    /* 이 부분이 데이터 통신하게 되면 필요 */
    private void getExIntent() {
//        Intent intent = getIntent();
//        house = (House) intent.getSerializableExtra(MapAdapter.SERIAL_HOUSE);

    }

    // 외부 라이브러리를 사용해 완성도를 viewpager indicator를 viewpager와 연결하였다.
    private void setPagerIndicator() {
        indicator = (CircleIndicator) findViewById(R.id.detail_pagerIndicator);
        indicator.setViewPager(viewPager);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    private void setViewPager() {
        viewPager = (ViewPager) findViewById(R.id.detail_room_viewPager);
        mAdapter = new DetailImgPager(this);
        viewPager.setAdapter(mAdapter);
    }

    private void initView() {
        detailHostImg = (ImageView) findViewById(R.id.detail_host_img);
        detailHostImg.setClickable(true);
    }

    private void setOnClick() {
        detailHostImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HostInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}

