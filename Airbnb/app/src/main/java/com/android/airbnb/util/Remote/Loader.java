package com.android.airbnb.util.Remote;

import android.util.Log;

import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.reservation.Reservation;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
                oneHouseList.doOneHouseList(house);
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
                    totalHouseList.doTotalHouseList(houseList);
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
                oneHostList.doOneHostList(host);
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
                totalHostList.doTotalHostList(hostList);
            }

            @Override
            public void onFailure(Call<List<Host>> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    public static void getReservation(String housePk, final ITask.oneReservation oneReservation) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<List<Reservation>> call = serverApi.readReservation(housePk);
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    Log.e("Loader", "code : " + response.code());
                    reservations = response.body();
                    Log.e("Loader", "reservation : " + response.body().toString());
                    oneReservation.doOneReservation(reservations);
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private static List<House> wishList = new ArrayList<>();

    public static void getWishList(String userToken, final ITask.allWishList allWishList) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<List<House>> call = serverApi.getWishList(userToken);
        call.enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                if (response.isSuccessful()) {
                    Log.e("Loader", "wishlist : " + response.body().toString());
                    wishList = response.body();
                    allWishList.doAllWishList(wishList);
                }
            }

            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void postWishList(String token, String housePk, final ITask.postWishList postWishList) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<String> call = serverApi.postWishList(token, housePk);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    postWishList.getWishResponse("위시리스트에 저장되었습니다.");
                } else if (response.code() == 201) {
                    postWishList.getWishResponse("위시리스트에서 삭제되었습니다.");
                } else {
                    postWishList.getWishResponse("통신이 실패하였습니다. 다시 한번 시도해주세요.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public static void postReservation(String token, String housePk, String checkin, String checkout, final ITask.postReservation iTask) throws UnsupportedEncodingException {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IServerApi serverApi = client.create(IServerApi.class);

        RequestBody checkinBody = RequestBody.create(MediaType.parse("text/plain"), checkin);
        RequestBody checkoutBody = RequestBody.create(MediaType.parse("text/plain"), checkout);

        Log.e("Loader", "postReservation : token : " + token + ", housePk : " + housePk + ", check in : " + checkin + ", checkout : " + checkout);

        Call<Reservation> call = serverApi.postReservation(token, housePk.toString(), checkinBody, checkoutBody);
        String originalUrl = call.request().url().toString();
        Log.e("Loader", " origin : " + originalUrl);

        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if (response.isSuccessful()) {
                    iTask.getReservationResponse("예약이 완료되었습니다.");
                } else {
                    Log.e("Loader", "body : " + response.body());
                    Log.e("Loader", "error : " + response.code());
                    iTask.getReservationResponse("예약이 실패하였습니다. 다시 한번 시도해주세요.");
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void getReservation(final ITask.getReservation iTask) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<List<Reservation>> call = serverApi.getReservation();
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> reservations = response.body();
                    iTask.getReservatoinResponse(reservations);
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
