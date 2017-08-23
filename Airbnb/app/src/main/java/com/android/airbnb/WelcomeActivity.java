package com.android.airbnb;

import android.app.ProgressDialog;
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
import com.android.airbnb.domain.airbnb.FacebookLoginResult;
import com.android.airbnb.login.LoginActivity;
import com.android.airbnb.main.GuestMainActivity;
import com.android.airbnb.signup.SignUpActivity;
import com.android.airbnb.util.PermissionControl;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.IServerApi;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, PermissionControl.CallBack{

    private Button btnGoLogin, btnFinish, btnSignUp;
    private ImageView imageAirbnb;
    private TextView txtTitle, txtOptions, txtDescription;

    Retrofit retrofit;
    IServerApi iServerApi;

    // 페이스북
    private LoginButton btnLoginFacebook;
    private CallbackManager callbackManager;

    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 프로그래스바 정의
        loginDialog = new ProgressDialog(this);
        loginDialog.setTitle("페이스북 로그인");
        loginDialog.setMessage("로그인 중 입니다...");
        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        setViews();
        setListeners();
        facebookLogin();
        PermissionControl.checkVersion(this);
    }

    // 페이스북 로그인 코드
    private void facebookLogin(){
        callbackManager = CallbackManager.Factory.create();
        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                loginDialog.show();
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        String email = "";

                        Log.e("페이스북 로그인 결과 == ", response.toString());
                        Log.e("페이스북 토큰 : ", loginResult.getAccessToken().getToken());
                        Log.e("페이스북 User ID : ", loginResult.getAccessToken().getUserId());
                        Log.e("Facebook result : ",object.toString());

                        try {
                            email = object.getString("email");
                            Log.e("페이스북 User Email : ", email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        /*try {
                            email = response.getJSONObject().getString("email").toString();
                            Log.e("페이스북 User Email : ", email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

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

    // 페이스북 onActivityResult
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

        Call<FacebookLoginResult> postFacebookLoginData = iServerApi.postFacebookLoginData(token);
        postFacebookLoginData.enqueue(new Callback<FacebookLoginResult>() {
            @Override
            public void onResponse(Call<FacebookLoginResult> call, Response<FacebookLoginResult> response) {
                Log.e("===============", "로그인 데이터 전송");

                /*Log.e("User img_profile ", response.body().user.getImg_profile() );
                Log.e("User first_name ", response.body().user.getFirst_name() );
                Log.e("User last_name ", response.body().user.getLast_name() );
                Log.e("User gender ", response.body().user.getGender() );
                Log.e("User phone_num ", response.body().user.getPhone_num() );
                Log.e("User birthday ", response.body().user.getBirthday() );
                Log.e("User preference_lang ", response.body().user.getPreference_language() );
                Log.e("User preference_curr ", response.body().user.getPreference_currency() );
                Log.e("User living_site ", response.body().user.getLiving_site() );
                Log.e("User introduce ", response.body().user.getIntroduce() );
                Log.e("User last_login ", response.body().user.getLast_login() );
                Log.e("User date_joined ", response.body().user.getDate_joined() );*/


                if(response.isSuccessful()) {
                    Log.e("response User pk ", response.body().user.getPk());
                    Log.e("response User username ", response.body().user.getUsername());
                    Log.e("response User email ", response.body().user.getEmail());
                    Log.e("response Token ", "Success : " + response.body().getToken());

                    // Shared Preference에 User Primary Key 저장
                    PreferenceUtil.setPrimaryKey(WelcomeActivity.this, response.body().user.getPk());
                    // Shared Preference에 User Email 저장
                    PreferenceUtil.setEmail(WelcomeActivity.this, response.body().user.getUsername());
                    // Shared Preference에 User Token 저장
                    PreferenceUtil.setToken(WelcomeActivity.this, response.body().getToken());


                    // 로그인 완료 후 메인화면으로 이동
                    Toast.makeText(WelcomeActivity.this, "Facebook 으로\n정상 로그인 되었습니다.\nMain 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WelcomeActivity.this, GuestMainActivity.class);
                    startActivity(intent);
                    finish();
                    loginDialog.dismiss();

                } else {
                    int statusCode = response.code();
                    Log.e("response code ::" , statusCode + "");
                }

                // TODO - 서버에서 작업이 끝나면 response 코드에 따라 처리해줘야함.
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
            public void onFailure(Call<FacebookLoginResult> call, Throwable t) {

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

    @Override
    public void init() {

    }

    @Override
    public void cancel() {
        Toast.makeText(this, "권한을 요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
