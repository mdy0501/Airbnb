package com.android.airbnb.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.WelcomeActivity;
import com.android.airbnb.data.ApiService;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.IServerApi;
import com.facebook.login.LoginManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    Retrofit retrofit;
    IServerApi iServerApi;

    private ConstraintLayout layoutNotice, layoutReceivePayment, layoutCurrency, layoutInformation, layoutAdvancedSettings, layoutMultipleAccounts, layoutSendFeedback, layoutLogout;
    private TextView txtTitle, txtNotice, txtReceivePayment, txtCurrency, txtCurrencyDetail, txtInformation, txtAdvancedSettings, txtMultipleAccounts, txtSendFeedback, txtLogout;
    private Button btnBack;

    private ProgressDialog logoutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 프로그래스바 정의
        logoutDialog = new ProgressDialog(this);
        logoutDialog.setTitle("로그아웃");
        logoutDialog.setMessage("로그아웃 중 입니다...");
        logoutDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

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
        txtLogout.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void postLogout(){
        logoutDialog.show();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iServerApi = retrofit.create(IServerApi.class);

        Call<ResponseBody> getLogout = iServerApi.getLogout("Token " + PreferenceUtil.getToken(this));
        getLogout.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("===============", "로그아웃 데이터 전송");
                Log.e("===============", "Response" + response.body());
                Intent intent = new Intent(SettingActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
                logoutDialog.dismiss();
                Toast.makeText(SettingActivity.this, "정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    // AlertDialog - Logout
    private void alertDialogLogout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // 제목 setting
        alertDialogBuilder.setTitle("로그아웃");

        // AlertDialog Setting
        alertDialogBuilder
                .setMessage("정말 로그아웃 하시겠습니까?")
                .setCancelable(false)
                .setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 로그아웃을 한다.
                                postLogout();
                                LoginManager.getInstance().logOut();
                            }
                        })
                .setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 로그아웃을 하지 않는다.
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.layoutNotice:
                Toast.makeText(this, "알림 클릭", Toast.LENGTH_SHORT).show();
               break;
            case R.id.layoutReceivePayment:
                Toast.makeText(this, "대금수령방법 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutCurrency:
                Toast.makeText(this, "통화 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutInformation:
                Toast.makeText(this, "정보 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutAdvancedSettings:
                Toast.makeText(this, "고급설정 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutMultipleAccounts:
                Toast.makeText(this, "복수계정 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutSendFeedback:
                Toast.makeText(this, "피드백보내기 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txtLogout:
                alertDialogLogout();
                break;
        }

    }

}
