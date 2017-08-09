package com.android.airbnb.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.GoogleMapViewPagerActivity;
import com.android.airbnb.R;
import com.android.airbnb.adapter.RoomsAdapter;
import com.android.airbnb.data.ApiService;
import com.android.airbnb.domain.airbnb.House;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRoomsFragment extends Fragment implements View.OnClickListener {

    Retrofit retrofit;
    ApiService apiService;
    private List<House> dataList;

    private Main2Activity main2Activity;
    private RecyclerView recyclerRooms;
    private RoomsAdapter roomsAdapter;
    private FloatingActionButton fabGoogleMapViewPager;


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
        setListeners();
        getData();
        return view;
    }


    private void setViews(View view){
        recyclerRooms = (RecyclerView) view.findViewById(R.id.recyclerRooms);
        fabGoogleMapViewPager = (FloatingActionButton) view.findViewById(R.id.fabGoogleMapViewPager);
    }

    private void setListeners(){
        fabGoogleMapViewPager.setOnClickListener(this);
    }

    private void getData(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        Call<List<House>> readAllHouse= apiService.readAllHouses();
        readAllHouse.enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
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
            public void onFailure(Call<List<House>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabGoogleMapViewPager:
                Intent intent = new Intent(main2Activity.getBaseContext(), GoogleMapViewPagerActivity.class);
                intent.putParcelableArrayListExtra("roomsHouseList", (ArrayList<? extends Parcelable>) dataList);
                startActivity(intent);
                break;
        }
    }
}
