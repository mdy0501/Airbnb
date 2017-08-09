package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private Main2Activity main2Activity;
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
        main2Activity = (Main2Activity) context;
        getData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.wishlist_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.wishlist_menu_delete:
                Toast.makeText(mContext, "목록을 삭제합니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.wishlist_menu_filter:
                Toast.makeText(mContext, "필터로 갑니다.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void getData() {
        Loader.getHouseList(this);
    }

    private void setAdapter() {
        adapter = new WishListDetailAdapter(houseList, mContext);
        wishRecycler.setAdapter(adapter);
        wishRecycler.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private void setViews(View view) {
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.wishlist_title);
        filteredResult = (TextView) view.findViewById(R.id.filtered_result);
        houseCount = (TextView) view.findViewById(R.id.reservation_house_count);
        wishRecycler = (RecyclerView) view.findViewById(R.id.wish_recycler);
        fabMap = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);


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
    }

    @Override
    public void doHostListTask(List<Host> hostList) {

    }

    @Override
    public void doHouseListTask(List<House> houseList) {
        this.houseList = houseList;
        setAdapter();
        connectData();
    }

    @Override
    public void doOnHouseTask(House house) {

    }

    @Override
    public void doOnHostTask(Host host) {

    }

}
