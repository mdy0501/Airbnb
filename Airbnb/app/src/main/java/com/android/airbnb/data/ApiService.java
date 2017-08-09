package com.android.airbnb.data;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by MDY on 2017-08-03.
 */

public interface ApiService {
    public static final String API_URL = "http://crusia.xyz/";

    /* @Multipart annotation 달아줘야함, 일종의 form-data 통신 시 syntax인 걸로 보임 */
    // 비효율적으로 인자를 많이 넘기는 감이 있으나.. 우선은 이렇게 해결했고 객체 자체를 넘길 수 있는지 여부는 조금 더 찾아봐야할 것 같음
    @Multipart
    @POST("apis/user/")
    Call<SignUpData> postSignUpData(@Part("email")RequestBody email,
                                    @Part("password1")RequestBody pw1,
                                    @Part("password2")RequestBody pw2,
                                    @Part("first_name")RequestBody firstName,
                                    @Part("last_name") RequestBody lastName,
                                    @Part("birthday") RequestBody birthday,
                                    @Part("agreement")RequestBody agreement);

    @Multipart
    @POST("apis/user/login/")
    Call<LoginResult> postLoginData(@Part("email") RequestBody email,
                                    @Part("password") RequestBody password);
    // Key : Authorization
    // Value : Token 762e7a853bbf466be109b91f2249b80411ebb99a

    @GET("apis/user/logout/")
    Call<ResponseBody> getLogout(@Header("Authorization") String token);

    @GET("apis/house/")
    Call<List<RoomsData>> getTotalHouse();

    @GET("apis/house/{houseId}")
    Call<ResponseBody> getHouse(@Path("houseId") String houseId);

    @GET("apis/user/{userId}")
    Call<ResponseBody> getUser(@Path("userId") String userId);
}