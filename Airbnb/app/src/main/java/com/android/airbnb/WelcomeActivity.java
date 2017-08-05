package com.android.airbnb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.login.LoginActivity;
import com.android.airbnb.signup.SignUpActivity;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnGoLogin, btnFinish, btnLoginFacebook, btnSignUp;
    private ImageView imageAirbnb;
    private TextView txtTitle, txtOptions, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setViews();
        setListeners();
    }

    private void setViews(){
        btnGoLogin = (Button) findViewById(R.id.btnGoLogin);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnLoginFacebook = (Button) findViewById(R.id.btnLoginFacebook);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        imageAirbnb = (ImageView) findViewById(R.id.imageAirbnb);
        txtTitle = (TextView) findViewById(R.id.txtTitle1);
        txtOptions = (TextView) findViewById(R.id.txtOptions);
        txtDescription = (TextView) findViewById(R.id.txtIntroduce);
    }

    private void setListeners(){
        btnGoLogin.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnLoginFacebook.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnGoLogin :
                intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnFinish :
                finish();
                break;
            case R.id.btnLoginFacebook :
                Toast.makeText(WelcomeActivity.this, "페이스북 로그인 버튼 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSignUp :
                intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
