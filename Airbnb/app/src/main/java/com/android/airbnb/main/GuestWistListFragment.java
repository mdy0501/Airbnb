package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.airbnb.R;
import com.android.airbnb.adapter.WishListAdadpter;
import com.android.airbnb.domain.airbnb.House;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestWistListFragment extends Fragment {

    private List<House> houseList;
    private RecyclerView wishlistListRecycler;
    private WishListAdadpter adapter;
    private View view = null;
    public GuestMainActivity guestMainActivity;


    public GuestWistListFragment() {
        // Required empty public constructor
    }

    public void getData(List<House> houseList){
        this.houseList = houseList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GuestMainActivity){
            guestMainActivity = (GuestMainActivity) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_guest_wist_list, container, false);
        }
        setViews();
        return view;
    }

    private void setViews() {
        wishlistListRecycler = (RecyclerView) view.findViewById(R.id.wishlist_list_recycler);
    }

    private void setAdapter(){
        adapter = new WishListAdadpter();

    }
}
