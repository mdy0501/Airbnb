package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.adapter.RoomsAdapter;
import com.android.airbnb.data.ApiService;
import com.android.airbnb.data.RoomsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRoomsFragment extends Fragment {

    Retrofit retrofit;
    ApiService apiService;
    private List<RoomsData> dataList;

    private Main2Activity main2Activity;
    private TextView txtTitle;
    private RecyclerView recyclerRooms;
    private RoomsAdapter roomsAdapter;


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
        getData();
        return view;
    }


    private void setViews(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        recyclerRooms = (RecyclerView) view.findViewById(R.id.recyclerRooms);
    }

    private void getData(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        Call<List<RoomsData>> totalHouse = apiService.getTotalHouse();
        totalHouse.enqueue(new Callback<List<RoomsData>>() {
            @Override
            public void onResponse(Call<List<RoomsData>> call, Response<List<RoomsData>> response) {
                try {
                    dataList = response.body();
                    roomsAdapter = new RoomsAdapter(main2Activity.getBaseContext(), dataList);
                    recyclerRooms.setAdapter(roomsAdapter);
                    recyclerRooms.setLayoutManager(new LinearLayoutManager(main2Activity.getBaseContext()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<RoomsData>> call, Throwable t) {

            }
        });
    }

}
