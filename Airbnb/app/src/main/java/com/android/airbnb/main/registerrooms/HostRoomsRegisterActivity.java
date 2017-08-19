package com.android.airbnb.main.registerrooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.main.registerrooms.basic.HostRoomsRegisterBasicActivity;

public class HostRoomsRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout layoutTitle, layoutBasic, layoutDetail, layoutPrepare;
    private ImageButton imgBtnBack, imgBtnBasic, imgBtnDetail, imgBtnPrepare;
    private TextView txtTitle, txtBasicTitle, txtBasicContent, txtDetailTitle, txtDetailContent, txtPrepareTitle, txtPrepareContent;


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
        switch (v.getId()){
            case R.id.ImgBtnCancel:
                finish();
                break;
            case R.id.imgBtnBasic:
                Intent intent = new Intent(this, HostRoomsRegisterBasicActivity.class);
                startActivity(intent);
                break;
            case R.id.imgBtnDetail:
                Toast.makeText(this, "상세정보입력 버튼 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgBtnPrepare:
                Toast.makeText(this, "게스트준비 버튼 클릭", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
