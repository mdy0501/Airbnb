package com.android.airbnb.reservationStepFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.detailActivity.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.util.GlideApp;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationOneFragment extends Fragment {


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
    private FrameLayout container;
    private Context context;
    private ReservationTwoFragment twoStepFragment;
    private DetailHouseActivity presenter;

    public ReservationOneFragment() {
        // Required empty public constructor
    }

    public DetailHouseActivity getPresenter() {
        return presenter;
    }

    public void setPresenter(DetailHouseActivity presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation_one, container, false);
        getData();
        initView(view);
        setOnClick();
        setData(house);
        return view;
    }

    private void initView(View view) {
        reservationStep = (TextView) view.findViewById(R.id.reservation_step);
        reservationRoomType = (TextView) view.findViewById(R.id.reservation_room_type);
        reservationHostName = (TextView) view.findViewById(R.id.reservation_host_name);
        reservationHouseImg = (ImageView) view.findViewById(R.id.reservation_house_img);
        btnNext = (Button) view.findViewById(R.id.btn_next);
        reservationPricePerDay = (TextView) view.findViewById(R.id.reservation_price_per_day);
        reservationDate = (TextView) view.findViewById(R.id.reservation_date);
    }

    private void setOnClick() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getTwoStepFragment().setPresenter(presenter);
                presenter.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentContainer, presenter.getTwoStepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void getData() {
        house = presenter.getReservation().getHouse();
        checkIn = presenter.getReservation().getCheckin_date();
        checkOut = presenter.getReservation().getCheckout_date();
    }

    private void setData(House house) {
        House_images[] imgs = house.getHouse_images();
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

}
