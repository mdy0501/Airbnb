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


public class GoogleMapViewPagerActivity extends FragmentActivity implements OnMapReadyCallback, ITask, MapPagerAdapter.OnMapPagerListener {

    private Marker currentMarker = null;
    private GoogleMap mMap;
    /* data setting */
    private List<House> houseList;
    private List<CameraPosition> cameraPositionList;
    private List<MarkerOptions> markerOptList;
    private List<Marker> markerList;
    private Marker marker;

    private FloatingActionButton fabMap;
    private SupportMapFragment mapFragment;
    private ViewPager mapListPager;
    private RotateLoading progress;
    private MapPagerAdapter adapter;


    // bottom sheet
    private LinearLayout llBottomSheet;
    private BottomSheetBehavior behavior;
    private RecyclerView wishBottomRecycler;
    private BottomSheetAdapter bottomSheetAdapter;
    private ImageView btnAddList;
    private CoordinatorLayout snackbarPlace;

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
        initArrayList();
        setBtnOnClick();
        setBottomSheet(0);
        progress.start();
        Loader.getHouseList(this);
    }

    private void initArrayList() {
        cameraPositionList = new ArrayList<>();
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
                switch (newState){
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
                mMap.moveCamera(CameraUpdateFactory.newLatLng(houseList.get(position).getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });
        bottomSheetAdapter = new BottomSheetAdapter(houseList, this);
        wishBottomRecycler.setAdapter(bottomSheetAdapter);
        wishBottomRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


    }

//    private Bitmap writeTextOnDrawable(int drawableId, String text) {
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
//                .copy(Bitmap.Config.ARGB_8888, true);
//
//        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
//
//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.BLACK);
//        paint.setTypeface(tf);
//        paint.setTextAlign(Paint.Align.CENTER);
//        paint.setTextSize(convertToPixels(this, 11));
//
//        Rect textRect = new Rect();
//        paint.getTextBounds(text, 0, text.length(), textRect);
//
//        Canvas canvas = new Canvas(bm);
//
//        //If the text is bigger than the canvas , reduce the font size
//        if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
//            paint.setTextSize(convertToPixels(this, 7));        //Scaling needs to be used for different dpi's
//
//        //Calculate the positions
//        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset
//
//        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
//        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;
//
//        canvas.drawText(text, xPos, yPos, paint);
//        return  bm;
//    }


//    public static int convertToPixels(Context context, int nDP)
//    {
//        final float conversionScale = context.getResources().getDisplayMetrics().density;
//
//        return (int) ((nDP * conversionScale) + 0.5f) ;
//
//    }


    public void setMarkerOpt(/* GoogleMap googleMap, */int position) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(houseList.get(position).getLatLng())
                .title("₩" + houseList.get(position).getPrice_per_day());
        markerOptList.add(markerOptions);
    }


    @Override
    public void doHostListTask(List<Host> hostList) {

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

    private void initMap() {
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
                            marker = mMap.addMarker(markerOptList.get(i));
                            markerList.add(marker);
                            marker.showInfoWindow();
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
