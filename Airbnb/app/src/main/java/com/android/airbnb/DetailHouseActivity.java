package com.android.airbnb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.adapter.DetailImgPager;
import com.android.airbnb.adapter.MapPagerAdapter;
import com.android.airbnb.domain.Host;
import com.android.airbnb.domain.House;
import com.android.airbnb.domain.House_images;
import com.android.airbnb.presenter.ITask;
import com.android.airbnb.util.GlideApp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailHouseActivity extends AppCompatActivity implements ITask, OnMapReadyCallback {

    public void setHousePricePerDay(TextView housePricePerDay) {
        this.housePricePerDay = housePricePerDay;
    }

    public void setDetailHostImg(ImageView detailHostImg) {
        this.detailHostImg = detailHostImg;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setDetailRoomDateMin(TextView detailRoomDateMin) {
        this.detailRoomDateMin = detailRoomDateMin;
    }

    public void setTextView7(TextView textView7) {
        this.textView7 = textView7;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public void setTxtType(TextView txtType) {
        this.txtType = txtType;
    }

    public void setDetailRoomIntro(TextView detailRoomIntro) {
        this.detailRoomIntro = detailRoomIntro;
    }

    public void setTxtPrice(TextView txtPrice) {
        this.txtPrice = txtPrice;
    }

    public void setDetailHouseTitle(TextView detailHouseTitle) {
        this.detailHouseTitle = detailHouseTitle;
    }

    public void setDetailHostNameTxt(TextView detailHostNameTxt) {
        this.detailHostNameTxt = detailHostNameTxt;
    }

    public void setDetailRoomtypeTxt(TextView detailRoomtypeTxt) {
        this.detailRoomtypeTxt = detailRoomtypeTxt;
    }

    public void setDetailRoomViewPager(ViewPager detailRoomViewPager) {
        this.detailRoomViewPager = detailRoomViewPager;
    }

    public void setDetailPagerIndicator(CircleIndicator detailPagerIndicator) {
        this.detailPagerIndicator = detailPagerIndicator;
    }

    public void setRatingBar2(float count) {
        this.ratingBar2.setRating(count);
    }

    private ImageView detailHostImg;
    private House house;
    private TextView detailRoomDateMin;
    private TextView textView7;
    private ImageView img;
    private TextView txtType;
    private TextView detailRoomIntro;
    private TextView txtPrice;
    private TextView detailHouseTitle;
    private TextView detailHostNameTxt;
    private TextView detailRoomtypeTxt;
    private ViewPager detailRoomViewPager;
    private CircleIndicator detailPagerIndicator;
    private RatingBar ratingBar2;
    private GoogleMap mMap;
    private TextView housePricePerDay;
    public House_images[] houseImages;

    ViewPager viewPager;
    CircleIndicator indicator;
    PagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_house);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detail_house_mapFragment);

        getExIntent();
        initView();
        setData(this.house);
        mapFragment.getMapAsync(this);
        setViewPager();
        setPagerIndicator();
        setOnClick();
    }

    /* 이 부분이 parcelable로 넘겨 받음 == 아직 이미지가 넘어오지 않음 */
    private void getExIntent() {
        Intent intent = getIntent();
        house = intent.getParcelableExtra(MapPagerAdapter.HOUSE_OBJ);
        houseImages = house.getHouse_images();

        Log.e("Detail", house.getHouse_images().toString());
        Log.e("Detail", house.getHouse_images().length + "");
    }

    private void initView() {
        detailHostImg = (ImageView) findViewById(R.id.detail_host_img);
        detailHostImg.setClickable(true);
        detailRoomDateMin = (TextView) findViewById(R.id.detail_room_date_min);
        img = (ImageView) findViewById(R.id.img);
        txtType = (TextView) findViewById(R.id.txtType);
        detailRoomIntro = (TextView) findViewById(R.id.detail_room_intro);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        detailHouseTitle = (TextView) findViewById(R.id.detail_house_title);
        detailHostNameTxt = (TextView) findViewById(R.id.detail_host_name_txt);
        detailRoomtypeTxt = (TextView) findViewById(R.id.detail_roomtype_txt);
        detailRoomViewPager = (ViewPager) findViewById(R.id.detail_room_viewPager);
        detailPagerIndicator = (CircleIndicator) findViewById(R.id.detail_pagerIndicator);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        housePricePerDay = (TextView) findViewById(R.id.house_price_per_day);
    }

    private void setData(House house) {
        detailHouseTitle.setText(house.getTitle());
        txtPrice.setText(house.getPrice_per_day());
        detailHostNameTxt.setText(house.getHost().getUsername());
        detailRoomIntro.setText(house.getIntroduce());
        detailRoomtypeTxt.setText(house.getRoom_type());
        housePricePerDay.setText("₩" + house.getPrice_per_day() + "/1박");

        GlideApp.with(this)
                .load(house.getHost().getImg_profile())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .circleCrop()
                .into(detailHostImg);

    }

    // 외부 라이브러리를 사용해 완성도를 viewpager indicator를 viewpager와 연결하였다.
    private void setPagerIndicator() {
        indicator = (CircleIndicator) findViewById(R.id.detail_pagerIndicator);
        indicator.setViewPager(viewPager);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    private void setViewPager() {
        viewPager = (ViewPager) findViewById(R.id.detail_room_viewPager);
        Log.e("Detail", "imgs size === 222 :  " + house.getHouse_images().length);
        mAdapter = new DetailImgPager(house.getHouse_images(), this);
        viewPager.setAdapter(mAdapter);
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

    @Override
    public void doHostListTask(List<Host> hostList) {

    }

    @Override
    public void doHouseListTask(List<House> houseList) {

    }

    @Override
    public void doOnHouseTask(House house) {
        this.house = house;
    }

    @Override
    public void doOnHostTask(Host host) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("Detail", house.getLatLng().toString());
        mMap = googleMap;
        Marker marker = googleMap.addMarker(new MarkerOptions().position(house.getLatLng()).title(house.getTitle()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(house.getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        marker.showInfoWindow();
    }
}

