package com.android.airbnb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.data.ApiService;
import com.android.airbnb.login.LoginActivity;
import com.android.airbnb.main.GuestMainActivity;
import com.android.airbnb.signup.SignUpActivity;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.IServerApi;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnGoLogin, btnFinish, btnSignUp;
    private ImageView imageAirbnb;
    private TextView txtTitle, txtOptions, txtDescription;

    Retrofit retrofit;
    IServerApi iServerApi;

    // 페이스북
    private LoginButton btnLoginFacebook;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setViews();
        setListeners();
        callbackManager = CallbackManager.Factory.create();
        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.e("페이스북 로그인 결과 == ", response.toString());
                        Log.e("페이스북 토큰 : ", loginResult.getAccessToken().getToken());
                        Log.e("페이스북 User ID : ", loginResult.getAccessToken().getUserId());
                        Log.e("Facebook result : ",object.toString());

                        // 페이스북 Token SharedPreference에 저장
                        PreferenceUtil.setToken(WelcomeActivity.this, loginResult.getAccessToken().getToken());
                        postFacebookLoginData(loginResult.getAccessToken().getToken());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr", error.toString());
            }
        });
    }

    // 페이스북
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult :: ", "onActivityResult");
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // 페이스북 로그인 POST
    private void postFacebookLoginData(String facebookToken){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iServerApi = retrofit.create(IServerApi.class);

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), facebookToken);

        Call<com.android.airbnb.domain.airbnb.LoginResult> postFacebookLoginData = iServerApi.postFacebookLoginData(token);
        postFacebookLoginData.enqueue(new Callback<com.android.airbnb.domain.airbnb.LoginResult>() {
            @Override
            public void onResponse(Call<com.android.airbnb.domain.airbnb.LoginResult> call, Response<com.android.airbnb.domain.airbnb.LoginResult> response) {
                Log.e("===============", "로그인 데이터 전송");
                Log.e("response.code()", response.code()+"");

                // 로그인 완료 후 메인화면으로 이동
                Toast.makeText(WelcomeActivity.this, "Facebook 으로\n정상 로그인 되었습니다.\nMain 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WelcomeActivity.this, GuestMainActivity.class);
                startActivity(intent);
                finish();



                // TODO - 서버에서 작업이 끝나면 respone 코드에 따라 처리해줘야함.
                /*

                // 로그인시 예외처리
                if(response.isSuccessful()) {
                    Log.e("response code", "Success : " + response.code()+"");

                    // 로그인 완료 후 메인화면으로 이동
                    Toast.makeText(WelcomeActivity.this, "Facebook 으로\n정상 로그인 되었습니다.\nMain 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WelcomeActivity.this, GuestMainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (response.code() == 400){
                    Log.e("response code", "Fail : " + response.code()+"");
                    Toast.makeText(WelcomeActivity.this, "로그인 정보를\n다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WelcomeActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                }

                */


            }

            @Override
            public void onFailure(Call<com.android.airbnb.domain.airbnb.LoginResult> call, Throwable t) {

            }
        });

    }


    private void setViews(){
        btnGoLogin = (Button) findViewById(R.id.btnGoLogin);
        btnLoginFacebook = (LoginButton) findViewById(R.id.btnLoginFacebook);   // 페이스북 로그인 버튼
        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        imageAirbnb = (ImageView) findViewById(R.id.imageAirbnb);
        txtTitle = (TextView) findViewById(R.id.txtTitle1);
        txtOptions = (TextView) findViewById(R.id.txtOptions);
        txtDescription = (TextView) findViewById(R.id.txtTitle);
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
            case R.id.btnSignUp :
                intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
