package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.R;
import com.android.airbnb.adapter.TripAdapter;
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
public class SearchTripFragment extends Fragment {

    Retrofit retrofit;
    ApiService apiService;
    private List<RoomsData> dataList;

    private Main2Activity main2Activity;
    private RecyclerView recyclerTrip;
    private TripAdapter tripAdapter;


    public SearchTripFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search_trip, container, false);
        setViews(view);
        getData();
        return view;
    }

    private void setViews(View view){
        recyclerTrip = (RecyclerView) view.findViewById(R.id.recyclerTrip);
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
                    tripAdapter = new TripAdapter(main2Activity.getBaseContext(), dataList);
                    recyclerTrip.setAdapter(tripAdapter);
//                    recyclerTrip.setLayoutManager(new LinearLayoutManager(main2Activity.getBaseContext()));
                    recyclerTrip.setLayoutManager(new GridLayoutManager(main2Activity.getBaseContext(), 2));
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
