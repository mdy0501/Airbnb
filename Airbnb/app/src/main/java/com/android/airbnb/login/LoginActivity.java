package com.android.airbnb.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.WelcomeActivity;
import com.android.airbnb.data.ApiService;
import com.android.airbnb.domain.airbnb.LoginData;
import com.android.airbnb.domain.airbnb.LoginResult;
import com.android.airbnb.main.GuestMainActivity;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.IServerApi;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Retrofit retrofit;
    IServerApi iServerApi;

    private Button btnPreviousLogin, btnNextLoginEmail, btnUseTel;
    private TextView txtFindPassword, txtLogin, txtEmail, txtPassword;
    private EditText editEmail, editPassword;
    private CheckBox checkIndicate;

    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 프로그래스바 정의
        loginDialog = new ProgressDialog(this);
        loginDialog.setTitle("로그인");
        loginDialog.setMessage("로그인 중 입니다...");
        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

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
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword1);
        checkIndicate = (CheckBox) findViewById(R.id.checkIndicate);
    }

    private void setListeners(){
        btnPreviousLogin.setOnClickListener(this);
        btnNextLoginEmail.setOnClickListener(this);
        btnUseTel.setOnClickListener(this);
        txtFindPassword.setOnClickListener(this);
        indicatePassword();
    }


    // Password 표시하기
    private void indicatePassword(){
        checkIndicate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else if (isChecked == false){
//                    editPassword.setTransformationMethod(Text);
                    editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }


    private void postLoginData(){
        loginDialog.show();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iServerApi = retrofit.create(IServerApi.class);

        LoginData loginData = new LoginData();
        loginData.setEmail(editEmail.getText().toString());
        loginData.setPassword(editPassword.getText().toString());

        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), loginData.getEmail());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), loginData.getPassword());

        loginData.setEmail(editEmail.getText().toString());
        loginData.setPassword(editPassword.getText().toString());

        Call<LoginResult> postLoginData = iServerApi.postLoginData(email, password);
        postLoginData.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                Log.e("===============", "로그인 데이터 전송");

                // 로그인시 예외처리
                if(response.isSuccessful()) {
                    Log.e("response code", "Success : " + response.code()+"");
                    Log.e("response body PK", "Success : " + response.body().getPrimaryKey());
                    Log.e("response body email", "Success : " + response.body().getEmail());
                    Log.e("response body token", "Success : " + response.body().getToken());


                    // TODO  - 회원가입하는 부분도 바로 로그인하기 때문에 PK, email sharedPreference에 저장하는 코드 작성
                    // SharedPreference에 저장 (Token값)
                    PreferenceUtil.setToken(LoginActivity.this, response.body().getToken());
                    PreferenceUtil.setPrimaryKey(LoginActivity.this, response.body().getPrimaryKey());
                    PreferenceUtil.setEmail(LoginActivity.this, response.body().getEmail());


                    // 로그인 완료 후 메인화면으로 이동
                    Toast.makeText(LoginActivity.this, "정상 로그인 되었습니다.\nMain 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, GuestMainActivity.class);
                    startActivity(intent);
                    finish();
                    loginDialog.dismiss();
                } else if (response.code() == 400){
                    Log.e("response code", "Fail : " + response.code()+"");
                    Toast.makeText(LoginActivity.this, "이메일주소와 비밀번호를\n다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

            }
        });
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
                postLoginData();
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
