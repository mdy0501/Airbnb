package com.android.airbnb.util.Remote;

import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.presenter.ITask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public class Loader {

    public static final String BASE_URL = "http://crusia.xyz/apis/";
    public static List<Host> hostList;
    public static List<House> houseList;
    public static House house;
    public static Host host;

    public static void getOneHouse(String pk, final ITask iTask) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<House> call = serverApi.readOneHouse(pk);
        call.enqueue(new Callback<House>() {
            @Override
            public void onResponse(Call<House> call, Response<House> response) {
                house = response.body();
                iTask.doOnHouseTask(house);
            }

            @Override
            public void onFailure(Call<House> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public static void getHouseList(final ITask iTask) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<List<House>> call = serverApi.readAllHouses();
        call.enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                try {
                    houseList = response.body();
                    iTask.doHouseListTask(houseList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void getOneHost(String pk, final ITask iTask) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<Host> call = serverApi.readOneHost(pk);
        call.enqueue(new Callback<Host>() {
            @Override
            public void onResponse(Call<Host> call, Response<Host> response) {
                host = response.body();
                iTask.doOnHostTask(host);
            }

            @Override
            public void onFailure(Call<Host> call, Throwable t) {

            }
        });

    }

    public static void getHostList(final ITask iTask) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<List<Host>> call = serverApi.readAllHosts();
        call.enqueue(new Callback<List<Host>>() {
            @Override
            public void onResponse(Call<List<Host>> call, Response<List<Host>> response) {
                hostList = response.body();
                iTask.doHostListTask(hostList);
            }

            @Override
            public void onFailure(Call<List<Host>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
