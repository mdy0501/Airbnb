package com.android.airbnb.detailActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.tsengvn.typekit.TypekitContextWrapper;

public class DetailExtraFeeActivity extends AppCompatActivity {

    private ImageView extraFeeBtnClose;
    private TextView extraFeeWeeklyDiscount;
    private TextView extraFeePeople;
    private TextView extraFeeCleaingFee;
    private House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_extra_fee);
        getData();
        initView();
        setWidget();
        setOnClick();
    }

    private void getData(){
        Intent intent = getIntent();
        house = intent.getParcelableExtra(DetailHouseActivity.DETAIL_HOUSE);
    }

    private void setWidget(){
        Log.e("DetailExtraFeeActivity", house.getCleaning_fee());
        extraFeeCleaingFee.setText("1박당 청소비는 " + "₩" + house.getCleaning_fee() + "입니다.");
        extraFeePeople.setText("게스트 1명 초과 시 1박당 추가 비용은 " + "₩" + house.getExtra_people_fee() + " 입니다.");
        extraFeeWeeklyDiscount.setText("주 할인율은 " + house.getWeekly_discount() +"% 입니다.");
    }

    private void initView() {
        extraFeeBtnClose = (ImageView) findViewById(R.id.extra_fee_btn_close);
        extraFeeWeeklyDiscount = (TextView) findViewById(R.id.extra_fee_weekly_discount);
        extraFeePeople = (TextView) findViewById(R.id.extra_fee_people);
        extraFeeCleaingFee = (TextView) findViewById(R.id.extra_fee_cleaing_fee);

        extraFeeBtnClose.setClickable(true);
    }

    private void setOnClick(){
        extraFeeBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
