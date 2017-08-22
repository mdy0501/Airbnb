package com.android.airbnb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.domain.reservation.ReservationSingleTon;
import com.android.airbnb.util.GlideApp;
import com.tsengvn.typekit.TypekitContextWrapper;

public class ReservationOneActivity extends AppCompatActivity {

    private TextView reservationStep;
    private TextView reservationRoomType;
    private TextView reservationHostName;
    private ImageView reservationHouseImg;
    private Button btnNext;
    public static House house;
    private TextView reservationPricePerDay;
    private TextView reservationDate;
    private String checkIn = "";
    private String checkOut = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_one);
        getData();
        initView();
        setOnClick();
        setData(house);
    }

    private void initView() {
        reservationStep = (TextView) findViewById(R.id.reservation_step);
        reservationRoomType = (TextView) findViewById(R.id.reservation_room_type);
        reservationHostName = (TextView) findViewById(R.id.reservation_host_name);
        reservationHouseImg = (ImageView) findViewById(R.id.reservation_house_img);
        btnNext = (Button) findViewById(R.id.btn_next);
        reservationPricePerDay = (TextView) findViewById(R.id.reservation_price_per_day);
        reservationDate = (TextView) findViewById(R.id.reservation_date);
    }

    private void setOnClick() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReservationTwoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        house = ReservationSingleTon.getInstnace().getHouse();
        checkIn = ReservationSingleTon.getInstnace().getCheckin_date();
        checkOut = ReservationSingleTon.getInstnace().getCheckout_date();
    }

    private void setData(House house) {
        House_images[] imgs = house.getHouse_images();
        Log.e("OneActivity", "img lenght : " + imgs.length);
        reservationRoomType.setText(house.getRoom_type());
        reservationHostName.setText(house.getHost().getFirst_name() + house.getHost().getLast_name());
        reservationPricePerDay.setText("₩" + house.getPrice_per_day() + " / 1박");
        reservationDate.setText(checkIn + " ~ " + checkOut);

        GlideApp
                .with(this)
                .load(imgs[0].getImage())
                .centerCrop()
                .circleCrop()
                .fallback(R.mipmap.dummy_room)
                .into(reservationHouseImg);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
