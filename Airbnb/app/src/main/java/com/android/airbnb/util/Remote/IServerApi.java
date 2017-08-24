package com.android.airbnb.util.Remote;

import com.android.airbnb.domain.airbnb.FacebookLoginResult;
import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.LoginResult;
import com.android.airbnb.domain.airbnb.SignUpData;
import com.android.airbnb.domain.reservation.Reservation;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public interface IServerApi {

    public static final String BASE_URL = "http://crusia.xyz/";

    @GET("apis/house/")
    Call<List<House>> readAllHouses();

    @GET("apis/house/{pk}")
    Call<House> readOneHouse(@Path("pk") String pk);

    @GET("apis/user/")
    Call<List<Host>> readAllHosts();

    @GET("apis/house/{pk}")
    Call<Host> readOneHost(@Path("pk") String pk);

    @GET("apis/reservations/")
    Call<List<Reservation>> readReservation(@Query("house") String housePk);

    // 회원가입 POST
    @Multipart
    @POST("apis/user/")
    Call<SignUpData> postSignUpData(@Part("email")RequestBody email,
                                    @Part("password1") RequestBody password1,
                                    @Part("password2")RequestBody password2,
                                    @Part("first_name")RequestBody firstName,
                                    @Part("last_name") RequestBody lastName,
                                    @Part("birthday") RequestBody birthday,
                                    @Part("agreement")RequestBody agreement);

    // 로그인 POST
    @Multipart
    @POST("apis/user/login/")
    Call<LoginResult> postLoginData(@Part("email") RequestBody email,
                                    @Part("password") RequestBody password);


    // 페이스북 로그인 POST
    @Multipart
    @POST("apis/user/facebook-login/")
    Call<FacebookLoginResult> postFacebookLoginData(@Part("token") RequestBody token);


    // 로그아웃 GET
    // Key : Authorization  ,  Value : Token xxxxxxxxxxxxxxx
    @GET("apis/user/logout/")
    Call<ResponseBody> getLogout(@Header("Authorization") String token);


    // 숙소 등록 POST
    @Multipart
    @POST("apis/house/")
    Call<ResponseBody> postRegisterRooms(@Header("Authorization") String token,
                                         @Part("title") RequestBody title,
                                         @Part("address") RequestBody address,
                                         @Part("introduce") RequestBody introduce,
                                         @Part("space_info") RequestBody space_info,
                                         @Part("guest_access") RequestBody guest_access,
                                         @Part("price_per_day") RequestBody price_per_day,
                                         @Part("extra_people_fee") RequestBody extra_people_fee,
                                         @Part("cleaning_fee") RequestBody cleaning_fee,
                                         @Part("weekly_discount") RequestBody weekly_discount,
                                         @Part("accommodates") RequestBody accomodates,
                                         @Part("bathrooms") RequestBody bathrooms,
                                         @Part("bedrooms") RequestBody bedrooms,
                                         @Part("beds") RequestBody beds,
                                         @Part("room_type") RequestBody room_type,
                                         @Part("amenities") RequestBody amenities,
                                         @Part("latitude") RequestBody latitude,
                                         @Part("longitude") RequestBody longitude,
//                                         @Part("image") RequestBody image);
                                         @Part List<MultipartBody.Part> photos);


    @GET("apis/like/")
    Call<List<House>> getWishList(@Header("Authorization") String token);

    @POST("apis/like/")
    Call<String> postWishList(@Header("Authorization") String token, @Query("house") String housePk);

    @Multipart
    @POST("apis/reservations/")
    Call<Reservation> postReservation(@Header("Authorization") String token,
                                      @Query("house") String pk,
                                      @Part("checkin_date") RequestBody checkin,
                                      @Part("checkout_date") RequestBody checkout);

    @GET("apis/reservations/")
    Call<List<Reservation>> getReservation();
}
