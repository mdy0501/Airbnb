package com.android.airbnb.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.GoogleMapViewPagerActivity;
import com.android.airbnb.R;
import com.android.airbnb.adapter.WishListDetailAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestWishListDetailFragment extends Fragment implements ITask.allWishList {

    private GuestMainActivity guestMainActivity;
    private TextView txtTitle;
    private android.support.v7.widget.Toolbar toolbar;
    private TextView title;
    private TextView houseCount;
    private RecyclerView wishRecycler;
    private FloatingActionButton fabMap;
    private Context mContext;
    private WishListDetailAdapter adapter;
    private List<House> wishlist;
    public static final String WISHLIST_HOUSES = "Wcom.android.airbnb.main.RESERVED_HOUSES";
    private String userToken = "";
    private SwipeRefreshLayout refreshLayout;

    public GuestWishListDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        guestMainActivity = (GuestMainActivity) context;
        userToken = "Token " + PreferenceUtil.getToken(mContext);
        getData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Loader.getWishList(userToken, this);
        Log.e("WishListDetail", "====== getWishList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_wish, container, false);
        setViews(view);
        setToolbar();
        setListeners();
        setRefreshLayout();
        return view;
    }

    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        switch (item.getItemId()) {
            case R.id.wishlist_menu_delete:
                Toast.makeText(mContext, "목록을 삭제합니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.wishlist_menu_refresh:
                Loader.getWishList(userToken, this);
                break;

            case android.R.id.home:
                getFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void getData() {
        Loader.getWishList(userToken, this);
    }

    private void setAdapter() {
        adapter = new WishListDetailAdapter(wishlist, mContext);
        wishRecycler.setAdapter(adapter);
        wishRecycler.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private void setViews(View view) {
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.content);
        title = (TextView) view.findViewById(R.id.title);
        houseCount = (TextView) view.findViewById(R.id.reservation_house_count);
        wishRecycler = (RecyclerView) view.findViewById(R.id.wish_recycler);
        fabMap = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.wishlist_refresh);
    }

    // 새로고침 기능 구현
    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Loader.getWishList(userToken, GuestWishListDetailFragment.this);
            }
        });

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void connectData() {
        title.setText("위시리스트");
        houseCount.setText("예약 가능한 숙소 " + wishlist.size() + "개");
    }

    private void setListeners() {
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GoogleMapViewPagerActivity.class);
                intent.putExtra("key", WISHLIST_HOUSES);
                intent.putParcelableArrayListExtra(WISHLIST_HOUSES, (ArrayList<? extends Parcelable>) wishlist);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void doAllWishList(List<House> wishlist) {
        Log.e("GuestWishList", "wishlist.size : " + wishlist.size());
        this.wishlist = wishlist;
        for (House item : wishlist) {
            item.setWished(true);
        }
        setAdapter();
        connectData();
        refreshLayout.setRefreshing(false);
    }
}
