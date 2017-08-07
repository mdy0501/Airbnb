package com.android.airbnb.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.WelcomeActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnPreviousLogin, btnNextLoginEmail, btnUseTel;
    private TextView txtFindPassword, txtLogin, txtEmail, txtPassword, txtIndicate;
    private EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
        setListeners();
    }

    private void setViews(){
        btnPreviousLogin = (Button) findViewById(R.id.btnPreviousLogin);
        btnNextLoginEmail = (Button) findViewById(R.id.btnNextLoginEmail);
        btnUseTel = (Button) findViewById(R.id.btnUseTel);
        txtFindPassword = (TextView) findViewById(R.id.txtFindPassword);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPassword = (TextView) findViewById(R.id.txtPassword1);
        txtIndicate = (TextView) findViewById(R.id.txtIndicate);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword1);
    }

    private void setListeners(){
        btnPreviousLogin.setOnClickListener(this);
        btnNextLoginEmail.setOnClickListener(this);
        btnUseTel.setOnClickListener(this);
        txtFindPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnPreviousLogin :
                intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnNextLoginEmail :
                Toast.makeText(LoginActivity.this, "메인 화면으로 이동", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnUseTel :
                Toast.makeText(LoginActivity.this, "전화번호 사용 버튼 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txtFindPassword :
                intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
