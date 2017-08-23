package com.android.airbnb.signup;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.data.ApiService;
import com.android.airbnb.domain.airbnb.LoginResult;
import com.android.airbnb.domain.airbnb.SignUpData;
import com.android.airbnb.login.LoginActivity;
import com.android.airbnb.main.GuestMainActivity;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.IServerApi;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpBeforeFragment extends Fragment implements View.OnClickListener{

    Retrofit retrofit;
    IServerApi iServerApi;
    private List<SignUpData> dataList;

    private SignUpActivity signUpActivity;
    private Button btnAccept, btnRefuse;
    private TextView txtTitle, txtDetail, txtDescription1, txtDescription2;


    public SignUpBeforeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        signUpActivity = (SignUpActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_before, container, false);
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        btnAccept = (Button) view.findViewById(R.id.btnAccept);
        btnRefuse = (Button) view.findViewById(R.id.btnRefuse);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        txtDetail = (TextView) view.findViewById(R.id.txtDetail);
        txtDescription1 = (TextView) view.findViewById(R.id.txtDescription1);
        txtDescription2 = (TextView) view.findViewById(R.id.txtDescription2);
    }

    private void setListeners(){
        btnAccept.setOnClickListener(this);
        btnRefuse.setOnClickListener(this);
        txtDetail.setOnClickListener(this);
    }

    private void postSignUpData(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iServerApi = retrofit.create(IServerApi.class);

//        SignUpData signUpData = new SignUpData("test555@hanmail.net", "12345678", "12345678", "dongyeon", "min", "1989-05-01", true);

        /* RequestBody 객체에 실어보내야 함, sign-up 정보 post 시 JSON 객체로 통신하지 않고 text/plain으로 구성되어 있는 form-data로 통신 */
        // json 객체로 통신 시도하면 400 error와 함께 bad request error가 뜸 -> 조사해본 결과 보통 syntax error 때문에 발생한다고 함
        final RequestBody email = RequestBody.create(MediaType.parse("text/plain"), signUpActivity.signUpData.getEmail());
        final RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"), signUpActivity.signUpData.getPassword1());
        RequestBody password2 = RequestBody.create(MediaType.parse("text/plain"), signUpActivity.signUpData.getPasswrod2());
        RequestBody firstName = RequestBody.create(MediaType.parse("text/plain"), signUpActivity.signUpData.getFirst_name());
        RequestBody lastName = RequestBody.create(MediaType.parse("text/plain"), signUpActivity.signUpData.getLast_name());
        RequestBody brithday = RequestBody.create(MediaType.parse("text/plain"), signUpActivity.signUpData.getBirthday());
        RequestBody agreement = RequestBody.create(MediaType.parse("text/plain"), signUpActivity.signUpData.getAgreement() + "");

        /* apiService interface 또한 Request 인자를 parameter로 받도록 수정 */
        Call<SignUpData> postSignUp = iServerApi.postSignUpData(email, password1, password2, firstName, lastName, brithday, agreement);
        postSignUp.enqueue(new Callback<SignUpData>() {
            @Override
            public void onResponse(Call<SignUpData> call, Response<SignUpData> response) {

                // 회원가입 성공시,
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "회원가입이 완료되었습니다.\nMain 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();

                    // 회원가입 성공하면, 바로 로그인하기
                    Call<LoginResult> postLoginData = iServerApi.postLoginData(email, password1);
                    postLoginData.enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                            // 로그인 성공시
                            if(response.isSuccessful()) {
                                // response되는 token값 저장
                                PreferenceUtil.setToken(getActivity(), response.body().getToken());
                                PreferenceUtil.setPrimaryKey(getActivity(), response.body().getPrimaryKey());
                                PreferenceUtil.setEmail(getActivity(), response.body().getEmail());

                                // 로그인 완료되면 Guest Main 화면으로 이동
                                Intent intent = new Intent(getContext(), GuestMainActivity.class);
                                startActivity(intent);
                                signUpActivity.finish();
                            } else if (response.code() == 400) {
                                Toast.makeText(getActivity(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "로그인 실패", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {

                        }
                    });
                }




                Intent intent = new Intent(signUpActivity.getBaseContext(), LoginActivity.class);
                startActivity(intent);
                signUpActivity.finish();
            }

            @Override
            public void onFailure(Call<SignUpData> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAccept :
                Log.e("가입 정보 getFirst_name", signUpActivity.signUpData.getFirst_name());
                Log.e("가입 정보 getLast_name", signUpActivity.signUpData.getLast_name());
                Log.e("가입 정보 getEmail ========", signUpActivity.signUpData.getEmail());
                Log.e("가입 정보 getPassword1 ====", signUpActivity.signUpData.getPassword1());
                Log.e("가입 정보 getPasswrod2 ====", signUpActivity.signUpData.getPasswrod2());
                Log.e("가입 정보 getBirthday ====", signUpActivity.signUpData.getBirthday());
                Log.e("가입 정보 getAgreement ====", String.valueOf(signUpActivity.signUpData.getAgreement()));
                postSignUpData();
                break;
            case R.id.btnRefuse :

                break;
            case R.id.txtDetail :
                goSignUpBeforeDetailFragment();
                break;
        }
    }

    private void goSignUpBeforeDetailFragment() {
        signUpActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, signUpActivity.signUpBeforeDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}