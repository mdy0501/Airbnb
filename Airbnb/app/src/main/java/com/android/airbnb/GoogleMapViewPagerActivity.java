package com.android.airbnb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.airbnb.adapter.MapAdapter;
import com.android.airbnb.domain.House;
import com.android.airbnb.domain.IMapMarker;
import com.android.airbnb.domain.LoaderDummy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GoogleMapViewPagerActivity extends FragmentActivity implements OnMapReadyCallback, IMapMarker{

    private Marker currentMarker = null;

    private GoogleMap mMap;
    private RecyclerView mapRecyclerview;
    private MapAdapter adapter;
    private List<House> houseList;
    private boolean mapInit = false;
    private FloatingActionButton fabMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_view_pager);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initView();
        setBtnOnClick();
        houseList = LoaderDummy.getDummyHouseList();
        setAdapter();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("GoogleMapViewPager", "onMapReady");
        mMap = googleMap;
        setMarkers();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(houseList.get(0).getHouseLatLng(), 12));
        mapInit = true;
        setMapOnClick();
    }


    private void setMarkers() {
        for (House temp : houseList) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(temp.getHouseLatLng())
                    .title("₩" + temp.getPrice());
            Marker marker = mMap.addMarker(markerOptions);
            marker.showInfoWindow();
        }
    }

    private void initView() {
        mapRecyclerview = (RecyclerView) findViewById(R.id.map_recyclerview);
        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
    }

    private void setAdapter() {
        Log.e("googlemap", "new adapter");
        adapter = new MapAdapter(houseList, GoogleMapViewPagerActivity.this, GoogleMapViewPagerActivity.this);
        // 라이브러리를 활용하여 리사이클러 아이템이 항상 중앙에 위치하게끔 설정해줌.
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mapRecyclerview);
        mapRecyclerview.setAdapter(adapter);
        mapRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setBtnOnClick() {
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GoogleMapViewPagerActivity.this, "필터 기능으로 이동합니다..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMapOnClick(){
        if (mapInit == true) {
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(getBaseContext(), "눌렸씁니다!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void moveMarker(House house) {
        if (mapInit == true) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(house.getHouseLatLng(), 12));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(house.getHouseLatLng())
                    .title("₩" + house.getPrice());
            Marker marker = mMap.addMarker(markerOptions);
            marker.showInfoWindow();
        }
    }

}
