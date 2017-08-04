package com.android.airbnb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.airbnb.adapter.MapPagerAdapter;
import com.android.airbnb.domain.Host;
import com.android.airbnb.domain.House;
import com.android.airbnb.presenter.ITask;
import com.android.airbnb.util.Remote.Loader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;


public class GoogleMapViewPagerActivity extends FragmentActivity implements OnMapReadyCallback, ITask {

    private Marker currentMarker = null;
    private GoogleMap mMap;
    /* data setting */
    private List<House> houseList;
    private List<CameraPosition> cameraPositionList;
    private List<MarkerOptions> markerOptList;
    private List<Marker> markerList;

    private FloatingActionButton fabMap;
    private SupportMapFragment mapFragment;
    private ViewPager mapListPager;
    private RotateLoading progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_view_pager);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_house_mapFragment);
        mapFragment.getMapAsync(GoogleMapViewPagerActivity.this);
        initView();
        initList();
        setBtnOnClick();
        progress.start();
        Loader.getHouseList(this);
    }

    private void initList(){
        cameraPositionList = new ArrayList<>();
        markerOptList = new ArrayList<>();
        markerList = new ArrayList<>();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("GoogleMapViewPager", "onMapReady");
        MapsInitializer.initialize(getApplicationContext());
        mMap = googleMap;
    }

    private void initView() {
        fabMap = (FloatingActionButton) findViewById(R.id.fab_filter);
        mapListPager = (ViewPager) findViewById(R.id.map_list_pager);
        progress = (RotateLoading) findViewById(R.id.indicator_loading);
    }

    private void setAdapter() {
        MapPagerAdapter adapter = new MapPagerAdapter(houseList, this);
        mapListPager.setAdapter(adapter);
        mapListPager.setClipToPadding(false);
        mapListPager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -9);
    }

    private void setViewPager() {
        mapListPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(houseList.get(position).getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });

    }

    public void setMarkerOpt(/* GoogleMap googleMap, */int position) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(houseList.get(position).getLatLng())
                .title("₩" + houseList.get(position).getPrice_per_day());
        markerOptList.add(markerOptions);
    }


    @Override
    public void doHostListTask(List<Host> hostList) {

    }

    private void setBtnOnClick(){
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GoogleMapViewPagerActivity.this, "필터로 이동..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* marker 온클릭 셋팅 */
    // 시간적 여유 -> Lambda로 변환
    public void setMarkerOnClick() {

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(GoogleMapViewPagerActivity.this, "Lat/Lon ::  " + marker.getPosition(), Toast.LENGTH_SHORT).show();

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int position = 0;
                for (int i = 0; i < houseList.size(); i++) {
                    if (marker.getPosition().equals(houseList.get(i).getLatLng())) {
                        position = i;
                    }
                }
                mapListPager.setCurrentItem(position);
                return false;
            }
        });
    }

    @Override
    public void doHouseListTask(List<House> houseList) {
        this.houseList = houseList;
        setAdapter();
        /* Async 처리 */
        progress.start();
        setMarkers(mMap);
        setMarkerOnClick();
        setViewPager();
        initMap();

    }

    @Override
    public void doOnHouseTask(House house) {

    }

    @Override
    public void doOnHostTask(Host host) {

    }

    private void initMap(){
        mMap.moveCamera(CameraUpdateFactory
                .newLatLng(houseList.get(0).getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }



    /* 비동기 처리 thread 구현부 */
    private void setMarkers(final GoogleMap googleMap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < houseList.size(); i++) {
                    setMarkerOpt(i);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < markerOptList.size(); i++) {
                            MarkerOptions markerOpt = markerOptList.get(i);
                            Marker marker = mMap.addMarker(markerOpt);
                            marker.showInfoWindow();
                            markerList.add(marker);
                            progress.stop();
                        }
                    }
                });
            }
        }).start();


    }
}
