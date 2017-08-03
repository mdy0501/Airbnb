package com.android.airbnb.util.Remote;

import com.android.airbnb.domain.Host;
import com.android.airbnb.domain.House;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public interface IServerApi {

    @GET("house")
    Call<List<House>> getHouseList();

    @GET("user")
    Call<List<Host>> getHostList();

}
