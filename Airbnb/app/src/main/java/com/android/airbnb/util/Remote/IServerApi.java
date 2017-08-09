package com.android.airbnb.util.Remote;

import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.domain.airbnb.House;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public interface IServerApi {

    @GET("apis/house/")
    Call<List<House>> readAllHouses();

    @GET("apis/house/{pk}")
    Call<House> readOneHouse(@Path("pk") String pk);

    @GET("apis/user/")
    Call<List<Host>> readAllHosts();

    @GET("apis/house/{pk}")
    Call<Host> readOneHost(@Path("pk") String pk);

}
