package com.android.airbnb.util.Remote;

import android.util.Log;

import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.reservation.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.airbnb.util.Remote.IServerApi.BASE_URL;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public class Loader {


    public static List<Host> hostList;
    public static List<House> houseList;
    public static House house;
    public static Host host;
    public static List<Reservation> reservations;

    public static void getOneHouse(String pk, final ITask.oneHouseList oneHouseList) {
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
//                iTask.doTaskOneHouseList(house);
                oneHouseList.doTask(house);
            }

            @Override
            public void onFailure(Call<House> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public static void getTotalHouse(final ITask.totalHouseList totalHouseList) {
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
//                    iTask.doTaskTotalHouseList(houseList);
//                    totalHouseList(houseList);
                    totalHouseList.doTask(houseList);
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

    public static void getOneHost(String pk, final ITask.oneHostList oneHostList) {
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
                oneHostList.doTask(host);
            }

            @Override
            public void onFailure(Call<Host> call, Throwable t) {

            }
        });

    }

    public static void getTotalHost(final ITask.totalHostList totalHostList) {
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
                Log.e("loader", "==== done ====");
                totalHostList.doTask(hostList);
            }

            @Override
            public void onFailure(Call<List<Host>> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    public static void getReservation(String housePk, final ITask.oneReservation oneReservation){
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<List<Reservation>> call = serverApi.readReservation(housePk);
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if(response.code() == 200){
                    Log.e("Loader", "code : " + response.code());
                    reservations = response.body();
                    Log.e("Loader", "reservation : " + response.body().toString());
                    oneReservation.doTask(reservations);
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
