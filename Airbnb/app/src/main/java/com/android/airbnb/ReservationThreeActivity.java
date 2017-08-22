package com.android.airbnb;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.calendar.Utils;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.domain.reservation.ReservationSingleTon;
import com.android.airbnb.util.GlideApp;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.Loader;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.UnsupportedEncodingException;

public class ReservationThreeActivity extends AppCompatActivity implements View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_three);
        getData();
        initView();
        setData(ReservationSingleTon.getInstnace().getHouse());
    }

    private void setData(House house) {
        reservationRoomType.setText(house.getRoom_type());
        String cleaingFee = house.getCleaning_fee();
        reservationCalculateCleaningFee.setText(cleaingFee != null ? "₩" + cleaingFee : "청소비는 부과되지 않습니다.");
        reservationHostName.setText(house.getHost().getFirst_name() + ", " + house.getHost().getLast_name());
        long period = Utils.CalendarUtil.calculatePeriod(checkIn, checkOut);
        reservationRentalFee.setText("₩" + house.getPrice_per_day() + " X " + period +"박");
        reservationTotalRentalFee.setText("₩"+calculateTotalRentalFee(period, house.getPrice_per_day()));

        String[] total = {house.getPrice_per_day(), house.getCleaning_fee(), calculateTotalRentalFee(period, house.getPrice_per_day())};
        reservationTotalFee.setText("₩" + calculateTotalFee(total));

        House_images[] houseImages = house.getHouse_images();


        GlideApp
                .with(this)
                .load(houseImages[0].getImage())
                .centerCrop()
                .circleCrop()
                .into(reservationRoomImg);


    }

    private void getData(){

        checkIn = ReservationSingleTon.getInstnace().getCheckin_date();
        checkOut = ReservationSingleTon.getInstnace().getCheckout_date();
        house = ReservationSingleTon.getInstnace().getHouse();

    }

    private String calculateTotalRentalFee(long period, String pricePerDay){
        return period * (Integer.parseInt(pricePerDay)) + "";

    }

    private String calculateTotalFee(String fee[]) {
        int sum = 0;
        for (int i = 0; i < fee.length; i++) {
            int each = 0;
            if (fee[i] != null && !fee[i].equals(""))
                each = Integer.parseInt(fee[i]);
            sum += each;
        }
        return sum + "";
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private void initView() {
        reservationRoomType = (TextView) findViewById(R.id.reservation_room_type);
        reservationHostName = (TextView) findViewById(R.id.reservation_host_name);
        reservationRoomImg = (ImageView) findViewById(R.id.reservation_room_img);
        reservationRentalFee = (TextView) findViewById(R.id.reservation_rental_fee);
        reservationTotalRentalFee = (TextView) findViewById(R.id.reservation_total_rental_fee);
        reservationCalculateCleaningFee = (TextView) findViewById(R.id.reservation_calculate_cleaning_fee);
        reservationTotalFee = (TextView) findViewById(R.id.reservation_total_fee);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnReserve = (Button) findViewById(R.id.btn_reserve);

        btnCancel.setOnClickListener(this);
        btnReserve.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
        switch (v.getId()){
            case R.id.btn_cancel:
                setCancelDialog(dialogBuilder);
                break;

            case R.id.btn_reserve:
                setReserveDialog(dialogBuilder);
                break;
        }
    }

    private void setReserveDialog(AlertDialog.Builder builder){
        builder.setTitle("예약 완료 확인");
        builder.setMessage("정말로 해당 House를 예약하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 예약 관련 엑티비티 한번에 꺼지게 하기
                                try {
                                    Loader.postReservation("Token " + PreferenceUtil.getToken(ReservationThreeActivity.this), house.getPk(), checkIn, checkOut);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                ReservationThreeActivity.this.finish();
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

    private void setCancelDialog(AlertDialog.Builder builder){
        builder.setTitle("예약 취소");
        builder.setMessage("정말로 해당 House를 예약을 취소하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 예약 관련 엑티비티 한번에 꺼지게 하기
                                ReservationThreeActivity.this.finish();
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
}
