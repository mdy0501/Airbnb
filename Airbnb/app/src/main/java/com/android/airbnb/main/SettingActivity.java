package com.android.airbnb.main;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.airbnb.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout layoutNotice, layoutReceivePayment, layoutCurrency, layoutInformation, layoutAdvancedSettings, layoutMultipleAccounts, layoutSendFeedback, layoutLogout;
    private TextView txtTitle, txtNotice, txtReceivePayment, txtCurrency, txtCurrencyDetail, txtInformation, txtAdvancedSettings, txtMultipleAccounts, txtSendFeedback, txtLogout;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setViews();
        setListeners();
    }

    private void setViews(){
        layoutNotice = (ConstraintLayout) findViewById(R.id.layoutNotice);
        layoutReceivePayment = (ConstraintLayout) findViewById(R.id.layoutReceivePayment);
        layoutCurrency = (ConstraintLayout) findViewById(R.id.layoutCurrency);
        layoutInformation = (ConstraintLayout) findViewById(R.id.layoutInformation);
        layoutAdvancedSettings = (ConstraintLayout) findViewById(R.id.layoutAdvancedSettings);
        layoutMultipleAccounts = (ConstraintLayout) findViewById(R.id.layoutMultipleAccounts);
        layoutSendFeedback = (ConstraintLayout) findViewById(R.id.layoutSendFeedback);
        layoutLogout = (ConstraintLayout) findViewById(R.id.layoutLogout);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtNotice = (TextView) findViewById(R.id.txtNotice);
        txtReceivePayment = (TextView) findViewById(R.id.txtReceivePayment);
        txtCurrency = (TextView) findViewById(R.id.txtCurrency);
        txtCurrencyDetail = (TextView) findViewById(R.id.txtCurrencyDetail);
        txtInformation = (TextView) findViewById(R.id.txtInformation);
        txtAdvancedSettings = (TextView) findViewById(R.id.txtAdvancedSettings);
        txtMultipleAccounts = (TextView) findViewById(R.id.txtMultipleAccounts);
        txtSendFeedback = (TextView) findViewById(R.id.txtSendFeedback);
        txtLogout = (TextView) findViewById(R.id.txtLogout);
        btnBack = (Button) findViewById(R.id.btnBack);
    }

    private void setListeners(){
        layoutNotice.setOnClickListener(this);
        layoutReceivePayment.setOnClickListener(this);
        layoutCurrency.setOnClickListener(this);
        layoutInformation.setOnClickListener(this);
        layoutAdvancedSettings.setOnClickListener(this);
        layoutMultipleAccounts.setOnClickListener(this);
        layoutSendFeedback.setOnClickListener(this);
        layoutLogout.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack :

                break;
            case R.id.layoutNotice :

               break;
            case R.id.layoutReceivePayment :

                break;
            case R.id.layoutCurrency :

                break;
            case R.id.layoutInformation :

                break;
            case R.id.layoutAdvancedSettings :

                break;
            case R.id.layoutMultipleAccounts :

                break;
            case R.id.layoutSendFeedback :

                break;
            case R.id.layoutLogout :

                break;
        }

    }
}
