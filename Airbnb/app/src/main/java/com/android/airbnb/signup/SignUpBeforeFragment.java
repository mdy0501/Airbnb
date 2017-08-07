package com.android.airbnb.signup;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.data.ApiService;
import com.android.airbnb.data.SignUpData;

import java.util.List;

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
    ApiService apiService;
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
        apiService = retrofit.create(ApiService.class);

//        SignUpData signUpData = new SignUpData("test555@hanmail.net", "12345678", "12345678", "dongyeon", "min", "1989-05-01", true);

        Call<SignUpData> postSignUp = apiService.postSignUpData(signUpActivity.signUpData);
//        Call<SignUpData> postSignUp = apiService.postSignUpData(signUpData);
        postSignUp.enqueue(new Callback<SignUpData>() {
            @Override
            public void onResponse(Call<SignUpData> call, Response<SignUpData> response) {
                Log.e("==============" , "데이터 전송");
            }

            @Override
            public void onFailure(Call<SignUpData> call, Throwable t) {

            }
        });


        /*Call<List<SignUpData>> postSignUp = apiService.postSignUpData(signUpActivity.signUpData);
        postSignUp.enqueue(new Callback<List<SignUpData>>() {
               @Override
               public void onResponse(Call<List<SignUpData>> call, Response<List<SignUpData>> response) {
                   Log.e("==============" , "데이터 전송");
               }

               @Override
               public void onFailure(Call<List<SignUpData>> call, Throwable t) {

               }
           });*/

        /*postSignUp.enqueue(new Callback<SignUpData>() {
            @Override
            public void onResponse(Call<SignUpData> call, Response<SignUpData> response) {
                try {
                    Log.e("==============" , "데이터 전송");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpData> call, Throwable t) {

            }
        });*/
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
