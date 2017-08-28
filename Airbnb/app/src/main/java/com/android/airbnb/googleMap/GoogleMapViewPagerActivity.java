package com.android.airbnb.googleMap;

import android.content.Context;
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

import com.android.airbnb.detailActivity.MakeWishListActivity;
import com.android.airbnb.R;
import com.android.airbnb.adapter.BottomSheetAdapter;
import com.android.airbnb.adapter.MapPagerAdapter;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.main.GuestReservedListFragment;
import com.android.airbnb.main.GuestSearchRoomsFragment;
import com.android.airbnb.main.GuestWishListDetailFragment;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GoogleMapViewPagerActivity extends FragmentActivity implements OnMapReadyCallback, MapPagerAdapter.OnMapPagerListener, ITask.allWishList {

    private GoogleMap mMap;

    /* data setting */
    private List<House> houseList;
    private List<MarkerOptions> markerOptList;
    private List<Marker> markerList;

    /* widget setting */
    private FloatingActionButton fabMap;
    private SupportMapFragment mapFragment;
    private ViewPager mapListPager;
    private RotateLoading progress;
    private ImageView btnAddList;

    /* bottom sheet settings */
    private LinearLayout llBottomSheet;
    private MapPagerAdapter adapter;
    private BottomSheetBehavior behavior;
    private RecyclerView wishBottomRecycler;
    private BottomSheetAdapter bottomSheetAdapter;
    private CoordinatorLayout snackbarPlace;
    private String userToken = "";

    /* google map utils settings */
    public static final float BOTTOM_UP = 300.f;
    public static final float BOTTON_DOWN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_view_pager);
        // 1. SupportMapFragment를 변수와 연결한다.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_house_mapFragment);

        // 2. map을 셋팅하기 위해 getMapAsync를 호출해 비동기처리를 한다.
        mapFragment.getMapAsync(GoogleMapViewPagerActivity.this);
        userToken = "Token " + PreferenceUtil.getToken(this);

        initView();
        progress.start();
        initArrayList();
        getExIntent();
        Loader.getWishList(userToken, this);
        setAdapter();
        /* Async 처리 */
        setViewPager();
        setBottomSheet(0);
        setBottomSheetAdapter(wishlist);
        setBtnOnClick();
    }

    // 3. onMapReady가 콜백된다.
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // 3.1. widget 및 map 관련된 작업을 한다.
        MapsInitializer.initialize(getApplicationContext());
        mMap = googleMap;
        setMarkers(mMap);
        setMarkerOnClick();
        initMap();
    }

    private void getExIntent() {
        Intent intent = getIntent();
        String objKey = intent.getStringExtra("key");
        if (objKey.equals(GuestWishListDetailFragment.WISHLIST_HOUSES)) {
            houseList = intent.getParcelableArrayListExtra(GuestWishListDetailFragment.WISHLIST_HOUSES);
        } else if (objKey.equals(GuestSearchRoomsFragment.GUEST_SEARCH_ROOMS_FRAGMENT)) {
            houseList = intent.getParcelableArrayListExtra(GuestSearchRoomsFragment.GUEST_SEARCH_ROOMS_FRAGMENT);
        } else if (objKey.equals(GuestReservedListFragment.RESERVED_HOUSES)) {
            houseList = intent.getParcelableArrayListExtra(GuestReservedListFragment.RESERVED_HOUSES);
        }
    }

    private void initArrayList() {
        markerOptList = new ArrayList<>();
        markerList = new ArrayList<>();
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

                // marker title에 onclick이 일어날 때마다 viewPager adapter의 currentItem이 바뀜
                setMarkerChanged(markerList.get(position));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(houseList.get(position).getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });
    }

    private void setBottomSheetAdapter(List<House> wishlist) {
        bottomSheetAdapter = new BottomSheetAdapter(wishlist, this);
        wishBottomRecycler.setAdapter(bottomSheetAdapter);
        wishBottomRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setBtnOnClick() {
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    // GoogleMap 생성 및 호출 후, 초기화를 담당하는 메소드
    private void initMap() {
        mMap.moveCamera(CameraUpdateFactory
                .newLatLng(houseList.get(0).getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public String selectedMarkerID = "";
    public Map<String, Marker> markerMap = new HashMap<>();

    // 다수의 Map Marker를 생성 및 map 위에 생성을 담당하는 메소드
    private void setMarkers(final GoogleMap googleMap) {

        // 1. 커스텀 title box 설정을 위해 iconGenerator를 설정해준다.
        IconGenerator iconGenerator = new IconGenerator(GoogleMapViewPagerActivity.this);
        for (int i = 0; i < houseList.size(); i++) {

            // 2. MarkerOptions 객체에 marker 설정 셋팅
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(houseList.get(i).getLatLng())
                    .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("₩" + houseList.get(i).getPrice_per_day())))
                    .anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV());

            // 3. 위에서 설정한 markerOptions을 map에 뿌려준다.
            Marker marker = googleMap.addMarker(markerOptions);

            // 3.1. 추후에 marker를 통해 객체를 꺼낼 수 있도록 setTag() 메소드를 활용해 tag로 House 객체를 저장한다.
            marker.setTag(houseList.get(i));

            // 3.2. title의 경우, Icon을 사용했으므로 default title Window가 뜨지 않도록 설정한다.
            marker.hideInfoWindow();

            houseList.get(i).setMarker(marker);

            // 4. marker 관리를 위해 Collection Framework에 저장한다.
            markerList.add(marker);
            markerMap.put(marker.getId(), marker);
            progress.stop();
        }

        // 5. 최초 map 로드 시, 선택되는 0번째 마커를 셋팅한다.
        setMarkerChanged(markerList.get(0));
    }

    // IconGenerator 객체 사용
    private void setMarkerIcon(Marker marker, int styleId, int color) {
        House house = (House) marker.getTag();
        IconGenerator iconGenerator = new IconGenerator(getApplicationContext());
        iconGenerator.setTextAppearance(styleId);
        iconGenerator.setColor(color);
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("₩" + house.getPrice_per_day())));
    }

    // Map 상의 marker에 setOnMarkerClickListener 설정
    public void setMarkerOnClick() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                setMarkerChanged(marker);
                return false;
            }
        });
    }

    private void setMarkerChanged(Marker marker) {
        // 1. Map Marker 선택 시, marker 셋팅하는 코드
        if (selectedMarkerID != "") {
            Marker exMarker = markerMap.get(selectedMarkerID);
            setMarkerIcon(exMarker, R.style.iconGenTxt_default, Color.parseColor("#ffffff"));
        }

        // 2. 최초 Map activity 로드 시, marker selected 셋팅하는 코드
        setMarkerIcon(marker, R.style.iconGenTxt_clicked, Color.parseColor("#00A599"));
        selectedMarkerID = marker.getId();
        mapListPager.setCurrentItem(markerList.indexOf(marker));
    }

    private void setBottomSheet(float height) {
        behavior.setPeekHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics()));
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

    // bottomSheetAdapter의 wish button 클릭 시, 바로 UI에 반영되도록 하는 메소드
    @Override
    public void btnWishClicked(boolean btnIsChecked) {

        // 1. wish button 클릭 시, 통신을 통해 wishlist로 로드한다.
        Loader.getWishList("Token " + PreferenceUtil.getToken(this), this);

        // 2. 받아온 wishlist 데이터 업데이트 후, adapter 또한 notifyDataSetChanged()한다.
        bottomSheetAdapter.notifyDataSetChanged();

        // 3. recyclerView position을 0으로 설정하여 초기화 시, 0번째 index가 일관적으로 보이도록 셋팅한다.
        wishBottomRecycler.smoothScrollToPosition(0);

        // 4. wish button의 체크상태에 따라 서버와의 통신을 시도한다.
        // 4.1. 스낵바를 통해 wishlist를 바로 확인할 수 있다.
        if (btnIsChecked)
            setSnackbar("[wishlist]에 저장됨");
        else
            setSnackbar("[wishlist]에 삭제됨");
    }

    private void setSnackbar(String title) {
        Snackbar.make(snackbarPlace, title, Snackbar.LENGTH_SHORT).setAction("위시리스트", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loader.getWishList(userToken, GoogleMapViewPagerActivity.this);

                // 4.2. wishlist 보기 onClick 시, BottomSheet가 화면 상에 올라오는 위치를 설정해준다.
                setBottomSheet(300.f);
            }
        }).show();
    }

    private List<House> wishlist = new ArrayList<>();

    // 서버에서 받아온 모든 House와 wishlist 상의 하우스를 비교하여 user의 wish 상태 유/무를 설정해준다.
    private void findWishHouses() {
        for (House house : houseList) {
            for (House wishHouse : wishlist) {
                if (house.getPk().equals(wishHouse.getPk())) {
                    house.setWished(true);
                }
            }
        }
    }

    // wishlist REST 통신 후, 업데이트된 wishlist 데이터를 갱신한다.
    @Override
    public void doAllWishList(List<House> houses) {
        wishlist = houses;
        bottomSheetAdapter.refreshWishList(wishlist);
        findWishHouses();
    }

    // 액티비티에 폰트 적용하는 메소드 오버라이드
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
