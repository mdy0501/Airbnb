package com.android.airbnb;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.airbnb.adapter.BottomSheetAdapter;
import com.android.airbnb.adapter.MapPagerAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GoogleMapViewPagerActivity extends FragmentActivity implements OnMapReadyCallback, MapPagerAdapter.OnMapPagerListener {

    private GoogleMap mMap;

    /* data setting */
    private List<House> houseList;
    private List<MarkerOptions> markerOptList;
    private List<Marker> markerList;
    private Marker currentMarker;

    private FloatingActionButton fabMap;
    private SupportMapFragment mapFragment;
    private ViewPager mapListPager;
    private RotateLoading progress;
    private MapPagerAdapter adapter;

    /* bottom sheet settings */
    private LinearLayout llBottomSheet;
    private BottomSheetBehavior behavior;
    private RecyclerView wishBottomRecycler;
    private BottomSheetAdapter bottomSheetAdapter;
    private ImageView btnAddList;
    private CoordinatorLayout snackbarPlace;

    /* google map utils settings */
    public static final float BOTTOM_UP = 300.f;
    public static final float BOTTON_DOWN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_view_pager);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_house_mapFragment);
        mapFragment.getMapAsync(GoogleMapViewPagerActivity.this);
        initView();
        progress.start();
        initArrayList();
        // ==== 수정 코드 ==== //
        getExIntent();
        // ====
        // ======= 수정 //
        setAdapter();
        /* Async 처리 */

        setViewPager();
        setBtnOnClick();
        setBottomSheet(0);
//        initMap();

//        Loader.getHouseList(this);
    }

    private void getExIntent(){
        Intent intent = getIntent();
        houseList = intent.getParcelableArrayListExtra("roomsHouseList");
        Log.e("MapActivity", "houseLIst :: " + houseList.size());


    }

    private void initArrayList() {
        markerOptList = new ArrayList<>();
        markerList = new ArrayList<>();
    }

    private void setBottomSheet(float height) {
        behavior.setPeekHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics()));
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        // 구현완료하기
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        setStatusBarDim(false);
                        break;

                    default:
                        setStatusBarDim(true);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("GoogleMapViewPager", "onMapReady");
        MapsInitializer.initialize(getApplicationContext());
        mMap = googleMap;
        setMarkers(mMap);
        setMarkerOnClick();
        initMap();
    }

    private int getThemedResId(@AttrRes int attr) {
        TypedArray a = getTheme().obtainStyledAttributes(new int[]{attr});
        int resId = a.getResourceId(0, 0);
        a.recycle();
        return resId;
    }

    private void setStatusBarDim(boolean dim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(dim ? Color.TRANSPARENT :
                    ContextCompat.getColor(this, getThemedResId(R.attr.colorPrimaryDark)));
        }
    }

    private void initView() {
        fabMap = (FloatingActionButton) findViewById(R.id.fab_filter);
        mapListPager = (ViewPager) findViewById(R.id.map_list_pager);
        progress = (RotateLoading) findViewById(R.id.indicator_loading);
        // bottom sheet
        llBottomSheet = (LinearLayout) findViewById(R.id.map_bottomsheet);
        behavior = BottomSheetBehavior.from(llBottomSheet);
        wishBottomRecycler = (RecyclerView) findViewById(R.id.wishlist_recyclerview);
        btnAddList = (ImageView) findViewById(R.id.wish_bottomsheet_addlist);
        snackbarPlace = (CoordinatorLayout) findViewById(R.id.snackbar_place);

    }

    private void setAdapter() {
        adapter = new MapPagerAdapter(houseList, this, this);
        adapter.setSnackPlace((CoordinatorLayout) findViewById(R.id.snackbar_place));
        mapListPager.setAdapter(adapter);
        mapListPager.setClipToPadding(false);
        mapListPager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -9);
    }

    private void setViewPager() {
        mapListPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.e("map", "onPageSelected pos :: " + position);
                adapter.setCurrentPostition(position);
                // ... ?
                setMarkerChanged(markerList.get(position));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(houseList.get(position).getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });
        bottomSheetAdapter = new BottomSheetAdapter(houseList, this);
        wishBottomRecycler.setAdapter(bottomSheetAdapter);
        wishBottomRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setBtnOnClick() {
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.snackbar_place), "필털ㅇㄴㅁㄹㅁㄴㅇㄹ", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(GoogleMapViewPagerActivity.this, "필터로 이동..!", Toast.LENGTH_SHORT).show();
            }
        });
        btnAddList.setClickable(true);
        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MakeWishListActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    public String selectedMarkerID = "";
    public Map<String, Marker> markerMap = new HashMap<>();

    private void setMarkerIcon(Marker marker, int styleId, int color) {
        House house = (House) marker.getTag();
        IconGenerator iconGenerator = new IconGenerator(getApplicationContext());
        iconGenerator.setTextAppearance(styleId);
        iconGenerator.setColor(color);
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("₩" + house.getPrice_per_day())));
    }

    /* IconGenerator 객체 사용 */
    private IconGenerator iconGenerator;

    public MarkerOptions getMarkerOpt(int position) {
        iconGenerator = new IconGenerator(GoogleMapViewPagerActivity.this);
        // style 추가해서 커스텀하기
        MarkerOptions markerOptions = new MarkerOptions()
                .position(houseList.get(position).getLatLng())
                .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("₩" + houseList.get(position).getTitle())))
                .anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV());
        markerOptList.add(markerOptions);
        return markerOptions;
    }

    private void setMarkerChanged(Marker marker) {
        if (selectedMarkerID != "") {
            Marker exMarker = markerMap.get(selectedMarkerID);
            setMarkerIcon(exMarker, R.style.iconGenTxt_default, Color.parseColor("#ffffff"));
            // code 정리하기
        }
        setMarkerIcon(marker, R.style.iconGenTxt_clicked, Color.parseColor("#00A599"));
        selectedMarkerID = marker.getId();
        mapListPager.setCurrentItem(markerList.indexOf(marker));
    }

    /* currentMarker 온클릭 셋팅 */
    // 시간적 여유 -> Lambda로 변환
    public void setMarkerOnClick() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                setMarkerChanged(marker);
                return false;
            }
        });
    }

    // 1. houselist response 받아온다.
    // 셋팅할 것들 셋팅한다.
//    @Override
//    public void doTaskTotalHouseList(List<House> houseList) {
//        this.houseList = houseList;
//        setAdapter();
//        /* Async 처리 */
//        progress.start();
//        setMarkers(mMap);
//        setMarkerOnClick();
//        setViewPager();
//        initMap();
//    }

    private void initMap() {
        /* 바꿈 */
        Log.e("MapActivity", "initmap houselist ::" + houseList.size());
        mMap.moveCamera(CameraUpdateFactory
                .newLatLng(houseList.get(0).getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    /* 비동기 처리 thread 구현부 */
    // 다시 다듬기
    private void setMarkers(final GoogleMap googleMap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 하우스리스트 받아온 상황
                // 셋포지션하려고 비동기처리
                //
                for (int i = 0; i < houseList.size(); i++) {
//                    getMarkerOpt(i);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MarkerOptions markerOptions;
                        iconGenerator = new IconGenerator(GoogleMapViewPagerActivity.this);
                        for (int i = 0; i < houseList.size(); i++) {
                            markerOptions = new MarkerOptions()
                                    .position(houseList.get(i).getLatLng())
                                    .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("₩" + houseList.get(i).getTitle())))
                                    .anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV());
                            Marker marker = googleMap.addMarker(markerOptions);
                            marker.setTag(houseList.get(i));
                            houseList.get(i).setMarker(marker);
                            markerList.add(marker);
                            markerMap.put(marker.getId(), marker);
                            progress.stop();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 세부 데이터 통신 가능한 상황에 데이터 연결
     *
     * @param btnIsChecked
     */
    @Override
    public void btnWishClicked(boolean btnIsChecked) {
        bottomSheetAdapter.notifyDataSetChanged();
        wishBottomRecycler.smoothScrollToPosition(0);
        if (btnIsChecked) {
            Snackbar.make(snackbarPlace, "[위시리스트 이름]에 저장됨.", Snackbar.LENGTH_LONG).setAction("변경", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBottomSheet(300.f);
                }
            }).show();

        } else {
            Snackbar.make(snackbarPlace, "[위시리스트 이름]에 삭제됨.", Snackbar.LENGTH_LONG).setAction("실행취소", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(snackbarPlace, "[위시리스트 이름]에 저장됨.", Snackbar.LENGTH_LONG).setAction("변경", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setBottomSheet(300.f);
                        }
                    }).show();
                }
            }).show();
        }
    }
}
