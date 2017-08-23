package com.android.airbnb.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.adapter.RegisterRoomsAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsFragment extends Fragment implements View.OnClickListener, ITask.totalHouseList{

    private HostMainActivity hostMainActivity;
    private TextView txtTitle, txtDescription;
    private ImageButton imgBtnAddRooms;
    private RecyclerView recyclerRegisterRooms;
    private RegisterRoomsAdapter registerRoomsAdapter;

    public static final int ADD_ROOMS = 10001;

    // 내가 등록한 숙소들 보여주는 변수
    private List<House> myRegisterHouses = new ArrayList<>();


    public HostRoomsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hostMainActivity = (HostMainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_rooms, container, false);
        setViews(view);
        setListeners();
        getData();
        return view;
    }

    private void setViews(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        imgBtnAddRooms = (ImageButton) view.findViewById(R.id.imgBtnAddRooms);
        recyclerRegisterRooms = (RecyclerView) view.findViewById(R.id.recyclerRegisterRooms);
    }

    private void setListeners(){
        imgBtnAddRooms.setOnClickListener(this);
    }

    private void getData(){
        Loader.getTotalHouse(this);
    }

    private void setAdapter() {
        if (myRegisterHouses != null) {
            registerRoomsAdapter = new RegisterRoomsAdapter(getActivity(), myRegisterHouses);
            recyclerRegisterRooms.setAdapter(registerRoomsAdapter);
            recyclerRegisterRooms.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnAddRooms:
                Intent intent = new Intent(getActivity(), HostRoomsRegisterActivity.class);
                startActivityForResult(intent, ADD_ROOMS);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == ADD_ROOMS){
                Log.e("ADD_ROOMS", "ADD_ROOMS");
                registerRoomsAdapter.clearData();
                getData();
            }
        }
    }

    @Override
    public void doTotalHouseList(List<House> houseList) {
        if(houseList != null){
            List<House> houseLists = houseList;
            for(int i = houseLists.size()-1 ; i>-1 ; i--){
                // 전체 숙소들 중에서 숙소를 등록한 Host pk값 추출
                String hostPrimaryKey = houseLists.get(i).getHost().getPk();
                // 현재 로그인한 계정의 pk값
                String userPrimaryKey = PreferenceUtil.getPrimaryKey(getActivity());

                if(hostPrimaryKey.equals(userPrimaryKey)) {
                    myRegisterHouses.add(houseLists.get(i));
                }
            }
            setAdapter();
        } else {
            Toast.makeText(getActivity(), "등록된 숙소가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
