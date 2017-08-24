package com.android.airbnb.reservationStepFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.detailActivity.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.reservation.Reservation;
import com.android.airbnb.util.GlideApp;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationTwoFragment extends Fragment {

    private Button btnNext;
    private ImageView hostImg;
    private EditText editChat;
    private TextView reservationPricePerDay;
    private Context context;
    private DetailHouseActivity presenter;

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

    public ReservationTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation_two, container, false);
        initView(view);
        setOnClick();
        setData();
        return view;
    }

    private void initView(View view) {
        btnNext = (Button) view.findViewById(R.id.btn_next);
        hostImg = (ImageView) view.findViewById(R.id.reservation_host_img);
        editChat = (EditText) view.findViewById(R.id.edit_chat);
        reservationPricePerDay = (TextView) view.findViewById(R.id.reservation_price_per_day);
    }

    private void setOnClick() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add step 3 fragment
                presenter.getThreeStepFragment().setPresenter(presenter);
                presenter.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentContainer, presenter.getThreeStepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setData() {
        Reservation reservation = presenter.getReservation();
        String img = reservation.getHouse().getHost().getImg_profile();
        GlideApp.with(this)
                .load(img != null && !img.equals("") ? img : R.mipmap.dummy_host_img)
                .centerCrop()
                .circleCrop()
                .into(hostImg);

        reservationPricePerDay.setText("₩" + reservation.getHouse().getPrice_per_day() + " / 박");
    }

}
