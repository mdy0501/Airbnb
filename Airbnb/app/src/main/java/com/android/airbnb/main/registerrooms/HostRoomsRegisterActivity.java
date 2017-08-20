package com.android.airbnb.main.registerrooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.HostingHouse;
import com.android.airbnb.main.registerrooms.basic.HostRoomsRegisterBasicActivity;
import com.android.airbnb.main.registerrooms.detail.HostRoomsRegisterDetailActivity;
import com.android.airbnb.main.registerrooms.prepare.HostRoomsRegisterPrepareActivity;

public class HostRoomsRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout layoutTitle, layoutBasic, layoutDetail, layoutPrepare;
    private ImageButton imgBtnBack, imgBtnBasic, imgBtnDetail, imgBtnPrepare;
    private TextView txtTitle, txtBasicTitle, txtBasicContent, txtDetailTitle, txtDetailContent, txtPrepareTitle, txtPrepareContent;

    // HostingHouse Singleton으로 인스턴스 생성
    public static HostingHouse hostingHouse = HostingHouse.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_rooms_register);
        setViews();
        setListeners();


    }

    private void setViews() {
        layoutPrepare = (ConstraintLayout) findViewById(R.id.layoutPrepare);
        imgBtnPrepare = (ImageButton) findViewById(R.id.imgBtnPrepare);
        txtPrepareContent = (TextView) findViewById(R.id.txtTypeDescription);
        txtPrepareTitle = (TextView) findViewById(R.id.txtTypeTitle);
        layoutDetail = (ConstraintLayout) findViewById(R.id.layoutDescription);
        imgBtnDetail = (ImageButton) findViewById(R.id.imgBtnDetail);
        txtDetailContent = (TextView) findViewById(R.id.txtDetailContent);
        txtDetailTitle = (TextView) findViewById(R.id.txtKingTitle);
        layoutBasic = (ConstraintLayout) findViewById(R.id.layoutBasic);
        imgBtnBasic = (ImageButton) findViewById(R.id.imgBtnBasic);
        txtBasicContent = (TextView) findViewById(R.id.txtBasicContent);
        txtBasicTitle = (TextView) findViewById(R.id.txtBasicTitle);
        layoutTitle = (ConstraintLayout) findViewById(R.id.layoutTitle);
        imgBtnBack = (ImageButton) findViewById(R.id.ImgBtnCancel);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
    }

    private void setListeners() {
        imgBtnBack.setOnClickListener(this);
        imgBtnBasic.setOnClickListener(this);
        imgBtnDetail.setOnClickListener(this);
        imgBtnPrepare.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.ImgBtnCancel:
                finish();
                break;
            case R.id.imgBtnBasic:
                intent = new Intent(this, HostRoomsRegisterBasicActivity.class);
                startActivity(intent);
                break;
            case R.id.imgBtnDetail:
                intent = new Intent(this, HostRoomsRegisterDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.imgBtnPrepare:
                intent = new Intent(this, HostRoomsRegisterPrepareActivity.class);
                startActivity(intent);
                break;
        }
    }
}
