package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.data.ApiService;
import com.android.airbnb.data.RoomsData;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRoomsFragment extends Fragment {

    Retrofit retrofit;
    ApiService apiService;

    private Main2Activity main2Activity;
    private TextView txtTitle;
    private RecyclerView recyclerRooms;


    public SearchRoomsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        main2Activity = (Main2Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_rooms, container, false);
        setViews(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .build();
        apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> totalHouse = apiService.getTotalHouse();
        totalHouse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();

                    Gson gson = new Gson();
                    RoomsData roomsData = gson.fromJson(jsonString, RoomsData.class);




                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void setViews(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        recyclerRooms = (RecyclerView) view.findViewById(R.id.recyclerRooms);
    }

}
