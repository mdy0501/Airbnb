package com.android.airbnb.data;

import com.android.airbnb.domain.airbnb.House;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by MDY on 2017-08-03.
 */

public interface ApiService {
    public static final String API_URL = "http://crusia.xyz/";



    @GET("apis/house/")
    Call<List<RoomsData>> getTotalHouse();

    @GET("apis/house/")
    Call<List<House>> readAllHouses();

    @GET("apis/house/{houseId}")
    Call<ResponseBody> getHouse(@Path("houseId") String houseId);

    @GET("apis/user/{userId}")
    Call<ResponseBody> getUser(@Path("userId") String userId);
}