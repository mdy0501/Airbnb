package com.android.airbnb.util.Remote;

import android.util.Log;

import com.android.airbnb.domain.Host;
import com.android.airbnb.domain.House;
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

    public static void getHouseList(final ITask iTask) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<List<House>> call = serverApi.getHouseList();
        call.enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                try {
                    houseList = response.body();
                    Log.e("Loader :: onResponse", houseList.size() + "");
                    iTask.doMapSync();
                    iTask.doHouseListTask(houseList);
                } catch (Exception e) {
                    Log.e("Loader :: onResponse", "Error :: ");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
                Log.e("getHouseList", "Error ===========");
                t.printStackTrace();
            }
        });
    }

    public static void getHostList(final ITask iTask) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<List<Host>> call = serverApi.getHostList();
        call.enqueue(new Callback<List<Host>>() {
            @Override
            public void onResponse(Call<List<Host>> call, Response<List<Host>> response) {
                hostList = response.body();
                Log.e("Loader", "============== hostList :: " + hostList.toString());
                Log.e("Loader", "============== hostList.size :: " + hostList.size());
                iTask.doHostListTask(hostList);
            }

            @Override
            public void onFailure(Call<List<Host>> call, Throwable t) {
                Log.e("getHostList", "Error ===========");
                t.printStackTrace();
            }
        });
    }
}
