package com.android.airbnb.reservationStepFragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.detailActivity.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.calendar.Utils;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.domain.reservation.Reservation;
import com.android.airbnb.util.GlideApp;
import com.android.airbnb.util.Remote.ITask;

import java.io.UnsupportedEncodingException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationThreeFragment extends Fragment implements View.OnClickListener, ITask.postReservation {

    private TextView reservationRoomType;
    private TextView reservationHostName;
    private ImageView reservationRoomImg;
    private TextView reservationRentalFee;
    private TextView reservationTotalRentalFee;
    private TextView reservationCalculateCleaningFee;
    private TextView reservationTotalFee;
    private Button btnCancel;
    private Button btnReserve;
    private String checkIn = "";
    private String checkOut = "";
    private Context context;
    private House house;
    private DetailHouseActivity presenter;

    public void setPresenter(DetailHouseActivity presenter) {
        this.presenter = presenter;
    }

    public ReservationThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation_three, container, false);
        getData();
        initView(view);
        setData(presenter.getReservation().getHouse());
        return view;
    }

    private void setData(House house) {
        reservationRoomType.setText(house.getRoom_type());
        String cleaingFee = house.getCleaning_fee();

        reservationCalculateCleaningFee.setText(cleaingFee != null ? "₩" + cleaingFee : "청소비는 부과되지 않습니다.");
        reservationHostName.setText(house.getHost().getFirst_name() + ", " + house.getHost().getLast_name());

        long period = Utils.CalendarUtil.calculatePeriod(checkIn, checkOut);
        reservationRentalFee.setText("₩" + house.getPrice_per_day() + " X " + period + "박");
        reservationTotalRentalFee.setText("₩" + calculateTotalRentalFee(period, house.getPrice_per_day()));

        String[] total = {house.getCleaning_fee(), calculateTotalRentalFee(period, house.getPrice_per_day())};
        reservationTotalFee.setText("₩" + calculateTotalFee(total));

        House_images[] houseImages = house.getHouse_images();


        GlideApp
                .with(this)
                .load(houseImages[0].getImage())
                .centerCrop()
                .circleCrop()
                .into(reservationRoomImg);


    }

    private void getData() {
        Reservation reservation = presenter.getReservation();
        house = reservation.getHouse();
        checkIn = reservation.getCheckin_date();
        checkOut = reservation.getCheckout_date();
    }

    private String calculateTotalRentalFee(long period, String pricePerDay) {
        return period * (Integer.parseInt(pricePerDay)) + "";
    }

    private String calculateTotalFee(String fee[]) {
        int sum = 0;
        for (int i = 0; i < fee.length; i++) {
            int each = 0;
            if (fee[i] != null || !fee[i].equals(""))
                each = Integer.parseInt(fee[i]);
            sum += each;
        }
        return sum + "";
    }

    private void initView(View view) {
        reservationRoomType = (TextView) view.findViewById(R.id.reservation_room_type);
        reservationHostName = (TextView) view.findViewById(R.id.reservation_host_name);
        reservationRoomImg = (ImageView) view.findViewById(R.id.reservation_room_img);
        reservationRentalFee = (TextView) view.findViewById(R.id.reservation_rental_fee);
        reservationTotalRentalFee = (TextView) view.findViewById(R.id.reservation_total_rental_fee);
        reservationCalculateCleaningFee = (TextView) view.findViewById(R.id.reservation_calculate_cleaning_fee);
        reservationTotalFee = (TextView) view.findViewById(R.id.reservation_total_fee);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnReserve = (Button) view.findViewById(R.id.btn_reserve);

        btnCancel.setOnClickListener(this);
        btnReserve.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
        switch (v.getId()) {
            case R.id.btn_cancel:
                setCancelDialog(dialogBuilder);
                break;

            case R.id.btn_reserve:
                setReservedDialog(dialogBuilder);
                break;
        }
    }

    private void setReservedDialog(AlertDialog.Builder builder) {
        builder.setTitle("예약 완료 확인");
        builder.setMessage("정말로 해당 House를 예약하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    presenter.postReservation();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                presenter.removeAllFragments();
                            }
                        })
                .setNegativeButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setCancelDialog(AlertDialog.Builder builder) {
        builder.setTitle("예약 취소");
        builder.setMessage("정말로 해당 House를 예약을 취소하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.removeAllFragments();
                            }
                        })
                .setNegativeButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void getReservationResponse(String message) {
        Toast.makeText(presenter, message, Toast.LENGTH_SHORT).show();
        presenter.setReservation(null);
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}
