package com.android.airbnb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.android.airbnb.adapter.MapListAdapter;
import com.android.airbnb.domain.Host;
import com.android.airbnb.domain.House;
import com.android.airbnb.presenter.IMapMarker;
import com.android.airbnb.presenter.ITask;
import com.android.airbnb.util.Remote.Loader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class GoogleMapViewPagerActivity extends FragmentActivity implements OnMapReadyCallback, IMapMarker, ITask {

    private Marker currentMarker = null;
    private GoogleMap mMap;
    /* data setting */
    private List<House> houseList;
    private List<CameraPosition> cameraPositionList;
    private List<MarkerOptions> markerList;
    // =============
    private boolean mapInit = false;
    private FloatingActionButton fabMap;
    private SupportMapFragment mapFragment;
    private ViewPager mapListPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_view_pager);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_house_mapFragment);
        mapFragment.getMapAsync(GoogleMapViewPagerActivity.this);
        initView();
        cameraPositionList = new ArrayList<>();
        markerList = new ArrayList<>();
        Loader.getHouseList(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("GoogleMapViewPager", "onMapReady");
        mMap = googleMap;
    }

    private void initView() {
        fabMap = (FloatingActionButton) findViewById(R.id.fab_filter);
        mapListPager = (ViewPager) findViewById(R.id.map_list_pager);
    }

    private void setAdapter() {
        MapListAdapter adapter = new MapListAdapter(houseList, this);
        Log.e("MapActivity", "==== adapter");
        mapListPager.setAdapter(adapter);
    }

    private void setViewPager() {
        mapListPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.e("MapViewPager", "pos :: " + position);
                Log.e("MapViewPager", "houseList :: " + houseList.get(position).toString());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(houseList.get(position).getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });

    }

    public void setMarker(/* GoogleMap googleMap, */int position) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(houseList.get(position).getLatLng())
                .title("₩" + houseList.get(position).getPrice_per_day());
        // googleMap.addMarker(markerOptions).showInfoWindow();
        markerList.add(markerOptions);
        Log.e("MarketList size :: ", "" + markerList.size());
    }


    @Override
    public void moveMarker(House house) {

    }

    @Override
    public void doHostListTask(List<Host> hostList) {

    }

    /* marker 온클릭 셋팅 */
    public void setMarkerOnClick() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int position = 0;
                for (int i = 0; i < houseList.size(); i++) {
                    if (marker.getPosition().equals(houseList.get(i).getLatLng())) {
                        position = i;
                    }
                }
                Toast.makeText(GoogleMapViewPagerActivity.this, marker.getId() + " :: idid", Toast.LENGTH_SHORT).show();
                mapListPager.setCurrentItem(position);
                return false;
            }
        });
    }

    @Override
    public void doHouseListTask(List<House> houseList) {
        this.houseList = houseList;
        Log.e("Map", "doHouseListTask :: " + houseList.size());
        setAdapter();
        /* Async 처리 */
        setMarkers();
        setMarkerOnClick();
        setViewPager();

        mMap.moveCamera(CameraUpdateFactory
                .newLatLng(houseList.get(0).getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


    }

    /* 비동기 처리 thread 구현부 */
    private void setMarkers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < houseList.size(); i++) {
                    setMarker(i);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < markerList.size(); i++) {
                            mMap.addMarker(markerList.get(i));
                        }
                    }
                });
            }
        }).start();


    }

    @Override
    public void doMapSync() {
        Log.e("MapViewPager", "================== getMapAsync");

    }
}
