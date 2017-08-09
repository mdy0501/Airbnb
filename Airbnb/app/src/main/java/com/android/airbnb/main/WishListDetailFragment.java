package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.adapter.WishListDetailAdapter;
import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.presenter.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishListDetailFragment extends Fragment implements ITask {

    private GuestMainActivity guestMainActivity;
    private TextView txtTitle;
    private android.support.v7.widget.Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnMenu;
    private TextView title;
    private TextView filteredResult;
    private TextView houseCount;
    private RecyclerView wishRecycler;
    private FloatingActionButton fabMap;
    private Context mContext;
    private WishListDetailAdapter adapter;
    private List<House> houseList;
    private ImageView btnFilter;
    private WistListFragment wishListFragment;

    public WishListDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        guestMainActivity = (GuestMainActivity) context;
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish, container, false);
        setViews(view);
        setToolbar();
        setListeners();
        return view;
    }

    private void setToolbar(){
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.wishlist_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;

            case R.id.btnFilter:

                break;

            case R.id.menu_delete:

                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void getData() {
        Loader.getTotalHouse(this);
    }

    private void setAdapter() {
        adapter = new WishListDetailAdapter(houseList, mContext);
        wishRecycler.setAdapter(adapter);
        wishRecycler.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private void setViews(View view) {
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.tooblar);
        btnBack = (ImageView) view.findViewById(R.id.wish_btnback);
        btnMenu = (ImageView) view.findViewById(R.id.wish_menu);
        title = (TextView) view.findViewById(R.id.wishlist_title);
        filteredResult = (TextView) view.findViewById(R.id.filtered_result);
        houseCount = (TextView) view.findViewById(R.id.reservation_house_count);
        wishRecycler = (RecyclerView) view.findViewById(R.id.wish_recycler);
        fabMap = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        btnFilter = (ImageView) view.findViewById(R.id.btnFilter);

        btnBack.setClickable(true);
        btnMenu.setClickable(true);
        btnFilter.setClickable(true);

//        View viewToolbar = getActivity().getLayoutInflater().inflate(R.layout.tt, null)
    }



    private void connectData() {
        houseCount.setText("예약 가능한 숙소 " + /* data 양 */houseList.size() + "개");

    }

    private void setListeners() {
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishListFragment = new WistListFragment();
                /* fragment stack에서 pop을 한 번하고 해주고 다시 쌓아줌 */
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_container, wishListFragment).addToBackStack(null).commit();

            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "move to filter activity", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void doTaskTotalHostList(List<Host> hostList) {

    }

    @Override
    public void doTaskTotalHouseList(List<House> houseList) {
        this.houseList = houseList;
        Log.e("houselist size :: ", "------------------- " + houseList.size());
        setAdapter();
        connectData();
    }

    @Override
    public void doTaskOneHouseList(House house) {

    }

    @Override
    public void doTaskOneHostList(Host host) {

    }

}
