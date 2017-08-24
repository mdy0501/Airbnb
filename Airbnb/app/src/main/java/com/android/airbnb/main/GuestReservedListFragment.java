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
import android.support.v7.widget.Toolbar;
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
import com.android.airbnb.adapter.ReservedAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.reservation.Reservation;
import com.android.airbnb.googleMap.GoogleMapViewPagerActivity;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestReservedListFragment extends Fragment implements ITask.getReservation, View.OnClickListener {


    private GuestMainActivity guestMainActivity;
    private TextView txtTitle;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnMenu;
    private TextView title;
    private TextView houseCount;
    private RecyclerView wishRecycler;
    private FloatingActionButton fabMap;
    private Context mContext;
    private ReservedAdapter reservedAdapter;
    private List<House> reservedHouses;
    private ImageView btnFilter;
    private GuestWistListFragment wishListFragment;
    public static final String RESERVED_HOUSES = "Wcom.android.airbnb.main.RESERVED_HOUSES";
    private String userToken = "";
    private SwipeRefreshLayout refreshLayout;

    public GuestReservedListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        reservedHouses = new ArrayList<>();
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
                Loader.getReservation(this);
                break;

            case android.R.id.home:
                getFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void getData() {
        Loader.getReservation(this);
    }

    private void setAdapter() {
        reservedAdapter = new ReservedAdapter(reservedHouses, mContext);
        wishRecycler.setAdapter(reservedAdapter);
        wishRecycler.setLayoutManager(new LinearLayoutManager(mContext));
    }


    private void setViews(View view) {
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        toolbar = (Toolbar) view.findViewById(R.id.content);
        title = (TextView) view.findViewById(R.id.title);
        houseCount = (TextView) view.findViewById(R.id.reservation_house_count);
        wishRecycler = (RecyclerView) view.findViewById(R.id.wish_recycler);
        fabMap = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.wishlist_refresh);
    }

    @Override
    public void onResume() {
        super.onResume();
        Loader.getReservation(GuestReservedListFragment.this);
    }

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Loader.getReservation(GuestReservedListFragment.this);
            }
        });
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    private void connectData() {
        title.setText("예약된 숙소 리스트");
        if (reservedHouses != null)
            houseCount.setText("예약된 숙소 " + reservedHouses.size() + "개");
        else
            houseCount.setText("예약된 숙소 " + 0 + "개");
    }

    private void setListeners() {
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GoogleMapViewPagerActivity.class);
                intent.putExtra("key", RESERVED_HOUSES);
                intent.putParcelableArrayListExtra(RESERVED_HOUSES, (ArrayList<? extends Parcelable>) reservedHouses);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getReservatoinResponse(List<Reservation> reservations) {
        if (reservations != null) {
            this.reservedHouses.clear();
            List<Reservation> reservationList = reservations;
            for (int i = reservationList.size() - 1; i > -1; i--) {
                if (reservationList.get(i).getGuest().getPk().equals(PreferenceUtil.getPrimaryKey(guestMainActivity))) {
                    this.reservedHouses.add(reservationList.get(i).getHouse());
                }
            }
            setAdapter();
            connectData();
        } else {
            Toast.makeText(guestMainActivity, "예약된 숙소가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        refreshLayout.setRefreshing(false);

    }
}
