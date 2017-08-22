package com.android.airbnb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.domain.reservation.ReservationSingleTon;
import com.android.airbnb.util.GlideApp;
import com.tsengvn.typekit.TypekitContextWrapper;

public class ReservationTwoActivity extends AppCompatActivity {

    private Button btnNext;
    private ImageView hostImg;
    private EditText editChat;
    private TextView reservationPricePerDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_two);
        initView();
        setOnClick();
        setData();
    }

    private void initView() {
        btnNext = (Button) findViewById(R.id.btn_next);
        hostImg = (ImageView) findViewById(R.id.reservation_host_img);
        editChat = (EditText) findViewById(R.id.edit_chat);
        reservationPricePerDay = (TextView) findViewById(R.id.reservation_price_per_day);
    }

    private void setOnClick() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReservationThreeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        String img = ReservationSingleTon.getInstnace().getHouse().getHost().getImg_profile();
        GlideApp.with(this)
                .load(img != null && !img.equals("") ? img : R.mipmap.dummy_host_img)
                .centerCrop()
                .circleCrop()
                .into(hostImg);

        reservationPricePerDay.setText("₩"+ReservationSingleTon.getInstnace().getHouse().getPrice_per_day() + " / 박");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
