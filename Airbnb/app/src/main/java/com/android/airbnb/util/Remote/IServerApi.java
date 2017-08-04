package com.android.airbnb.util.Remote;

import com.android.airbnb.domain.Host;
import com.android.airbnb.domain.House;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public interface IServerApi {

    @GET("house/")
    Call<List<House>> readAllHouses();

    @GET("house/{pk}")
    Call<House> readOneHouse(@Path("pk") String pk);

    @GET("user/")
    Call<List<Host>> readAllHosts();

    @GET("house/{pk}")
    Call<Host> readOneHost(@Path("pk") String pk);

}
