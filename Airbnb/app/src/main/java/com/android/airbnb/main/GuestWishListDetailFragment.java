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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.adapter.WishListDetailAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.googleMap.GoogleMapViewPagerActivity;
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


    // Fragment 생명주기를 활용해 activity에 onCreateView가 되기 전에 widget 셋팅 시 필요한 data들을 셋팅한다.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        wishlist = new ArrayList<>();
        guestMainActivity = (GuestMainActivity) context;
        userToken = "Token " + PreferenceUtil.getToken(mContext);
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

    // SwipeRefreshLayout를 아래로 swipe했을 때, user의 wishlist 받아오는 refresh 기능을 구현했다.
    private void setRefreshLayout() {

        // reSwipeRefreshLayout을 아래로 swipe 당기면 서버에 request를 요청한다.
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

    //
    private void setWidget() {
        title.setText("위시리스트");
        houseCount.setText("예약 가능한 숙소 " + wishlist.size() + "개");
    }

    // 화면에 떠있는 map button 클릭 시, wishlist 정보만 map 상에 셋팅한다.
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

    // 통신 후, wishlist 데이터를 갱신한 후, wishlist 데이터를 사용하는 인스턴스들과 widget들을 refresh한다.
    @Override
    public void doAllWishList(List<House> wishlist) {

        this.wishlist.clear();
        this.wishlist = wishlist;

        for (House item : wishlist) {
            item.setWished(true);
        }
        setAdapter();
        adapter.refreshData(wishlist);
        setWidget();
        refreshLayout.setRefreshing(false);
    }
}
