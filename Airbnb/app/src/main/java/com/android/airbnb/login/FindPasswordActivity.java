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

public class FindPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPreviousFindPassword, btnNextFindPassword;
    private TextView txtTitle, txtDescription, txtEmail;
    private EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        setViews();
        setListeners();
    }

    private void setViews(){
        btnPreviousFindPassword = (Button) findViewById(R.id.btnPreviousFindPassword);
        btnNextFindPassword = (Button) findViewById(R.id.btnNextFindPassword);
        txtTitle = (TextView) findViewById(R.id.txtTitle1);
        txtDescription = (TextView) findViewById(R.id.txtTitle);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        editEmail = (EditText) findViewById(R.id.editEmail);
    }

    private void setListeners(){
        btnPreviousFindPassword.setOnClickListener(this);
        btnNextFindPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPreviousFindPassword :
                Intent intent = new Intent(FindPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnNextFindPassword :
                Toast.makeText(FindPasswordActivity.this, "입력하신 이메일로 계정을 찾는 링크를 보냈습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
