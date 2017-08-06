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
import com.android.airbnb.adapter.PlaceFirstAdapter;
import com.android.airbnb.adapter.PlaceSecondAdapter;
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
public class SearchPlaceFragment extends Fragment {

    Retrofit retrofit;
    ApiService apiService;
    private List<RoomsData> dataList;

    private Main2Activity main2Activity;
    private TextView txtTitle1, txtTitle2;
    private RecyclerView recyclerPlace1, recyclerPlace2;
    private PlaceFirstAdapter placeFirstAdapter;
    private PlaceSecondAdapter placeSecondAdapter;


    public SearchPlaceFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search_place, container, false);
        setViews(view);
        getFirstData();
        getSecondData();
        return view;
    }

    private void setViews(View view){
        txtTitle1 = (TextView) view.findViewById(R.id.txtTitle1);
        txtTitle2 = (TextView) view.findViewById(R.id.txtTitle2);
        recyclerPlace1 = (RecyclerView) view.findViewById(R.id.recyclerPlace1);
        recyclerPlace2 = (RecyclerView) view.findViewById(R.id.recyclerPlace2);
    }

    private void getFirstData(){
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
                    placeFirstAdapter = new PlaceFirstAdapter(main2Activity.getBaseContext(), dataList);
                    recyclerPlace1.setAdapter(placeFirstAdapter);
                    LinearLayoutManager mLinearlayout = new LinearLayoutManager(main2Activity.getBaseContext());
                    mLinearlayout.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerPlace1.setLayoutManager(mLinearlayout);
//                    recyclerPlace1.setLayoutManager(new LinearLayoutManager(main2Activity.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<RoomsData>> call, Throwable t) {

            }
        });
    }

    private void getSecondData(){
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
                    placeSecondAdapter = new PlaceSecondAdapter(main2Activity.getBaseContext(), dataList);
                    recyclerPlace2.setAdapter(placeSecondAdapter);
//                    recyclerPlace2.setLayoutManager(new LinearLayoutManager(main2Activity.getBaseContext()));
                    recyclerPlace2.setLayoutManager(new LinearLayoutManager(main2Activity.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
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
