package com.android.airbnb.detailActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.adapter.BottomSheetAdapter;
import com.android.airbnb.adapter.DetailImgPager;
import com.android.airbnb.adapter.MapPagerAdapter;
import com.android.airbnb.adapter.ReservedAdapter;
import com.android.airbnb.adapter.RoomsAdapter;
import com.android.airbnb.adapter.WishListDetailAdapter;
import com.android.airbnb.calendar.CalendarActivity;
import com.android.airbnb.domain.airbnb.Amenities;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.domain.reservation.Reservation;
import com.android.airbnb.googleMap.DetailMapActivity;
import com.android.airbnb.papago.Translator;
import com.android.airbnb.papago.domain.Data;
import com.android.airbnb.reservationStepFragments.ReservationOneFragment;
import com.android.airbnb.reservationStepFragments.ReservationThreeFragment;
import com.android.airbnb.reservationStepFragments.ReservationTwoFragment;
import com.android.airbnb.util.Const;
import com.android.airbnb.util.GlideApp;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.UnsupportedEncodingException;

import me.relex.circleindicator.CircleIndicator;

public class DetailHouseActivity extends AppCompatActivity implements OnMapReadyCallback, Translator.IPapago, View.OnClickListener, ITask.postWishList, ITask.postReservation {


    private TextView detailGuestCountTxt;
    private TextView detailRoomStyleTxt;
    private TextView detailBedCountTxt;
    private TextView detailBathroomCountTxt;
    private FrameLayout mapFrame;
    private Button detailHouseBtnCheckReserve;
    public static final String HOUSE_LATLNG = "detail_house_latlng";
    public static final String HOUSE_ADDRESS = "detail_house_address";
    public static final String DETAIL_HOUSE = "com.google.android.gms.maps.model.detailHouse";
    public static final int DETAIL_HOUSE_ACTIVITY = 33333;
    public static final String HOST_INFO = "com.google.android.gms.maps.model.HOST_INFO";

    private ImageView detailHostImg;
    private House house;
    private TextView detailRoomDateMin;
    private TextView textView7;
    private ImageView img;
    private TextView txtType;
    private TextView detailRoomIntro;
    private TextView txtPrice;
    private TextView detailHouseTitle;
    private TextView detailHostNameTxt;
    private TextView detailRoomtypeTxt;
    private ViewPager detailRoomViewPager;
    private CircleIndicator detailPagerIndicator;
    private RatingBar ratingBar2;
    private GoogleMap mMap;
    private TextView housePricePerDay;
    public House_images[] houseImages;
    private DetailAmenitiesAdapter amenitiesAdapter;
    private RecyclerView amenitiesRecycler;
    private TextView btnTranslate;
    private TextView detailHouseMapInfo;

    private ViewPager viewPager;
    private CircleIndicator indicator;
    private PagerAdapter mAdapter;
    private Amenities[] amenities;
    private TextView detailHouseMoreCalendar;
    private TextView detailHouseMoreExtrafee;
    private TextView detailHouseReviewRegisterDate;
    private ImageView detailReivewHostImg;
    private TextView detailHouseReviewContent;
    private TextView detailHouseReviewCount;
    private RatingBar detailHouseReviewRatingbar;
    private TextView detailHouseReadReview;
    private CheckBox btnWish;


    // fragment
    private ReservationOneFragment oneStepFragment;
    private ReservationTwoFragment twoStepFragment;
    private ReservationThreeFragment threeStepFragment;
    private Fragment[] fragments;
    private Reservation reservation;
    private ImageView btnBack;

    public ReservationTwoFragment getTwoStepFragment() {
        return twoStepFragment;
    }

    public ReservationThreeFragment getThreeStepFragment() {
        return threeStepFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_house);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detail_house_mapFragment);

        getExIntent();
        fragments = new Fragment[3];
        initView();
        setFragments();
        setData(this.house);
        mapFragment.getMapAsync(this);
        setRoomPager();
        setAmenitiesRecycler();
        setViewPager();
        setPagerIndicator();
        setOnClick();
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // DetailMapViewPagerActivity로 부터 전달 받은 intent를 꺼낸다.
    private void getExIntent() {
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        String keyFromIntent = extra.getString("key");

        switch (keyFromIntent) {
            case RoomsAdapter.ROOMS_ADAPTER:
                getDataFromBundle(extra, keyFromIntent);
                break;

            case "registerHouse":
                getDataFromBundle(extra, keyFromIntent);
                break;

            case MapPagerAdapter.HOUSE_OBJ:
                getDataFromBundle(extra, keyFromIntent);
                break;

            case BottomSheetAdapter.BOTTOM_SHEET_ADAPTER_PK:
                getDataFromBundle(extra, keyFromIntent);
                break;

            case WishListDetailAdapter.WISHLIST_HOUSE:
                getDataFromBundle(extra, keyFromIntent);
                break;

            case ReservedAdapter.RESERVED_ADAPTER:
                getDataFromBundle(extra, keyFromIntent);
                break;
        }
    }

    private void getDataFromBundle(Bundle extra, String key) {
        this.house = (House) extra.getParcelable(key);
        Log.e("DetailHouseActivity", "house pk : " + house.getPk().toString());
        houseImages = house.getHouse_images();
        amenities = house.getAmenities();
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.detail_btn_back);
        detailHouseMapInfo = (TextView) findViewById(R.id.detail_house_map_info_txt);
        mapFrame = (FrameLayout) findViewById(R.id.mapFrameLayout);
        btnTranslate = (TextView) findViewById(R.id.btnTranslate);
        btnTranslate.setClickable(true);
        detailHostImg = (ImageView) findViewById(R.id.detail_host_img);
        detailHostImg.setClickable(true);
        img = (ImageView) findViewById(R.id.img);
        txtType = (TextView) findViewById(R.id.txtRoomType);
        detailRoomIntro = (TextView) findViewById(R.id.detail_room_intro);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        detailHouseTitle = (TextView) findViewById(R.id.detail_house_title);
        detailHostNameTxt = (TextView) findViewById(R.id.detail_host_name_txt);
        detailRoomtypeTxt = (TextView) findViewById(R.id.detail_roomtype_txt);
        detailRoomViewPager = (ViewPager) findViewById(R.id.detail_room_viewPager);
        detailPagerIndicator = (CircleIndicator) findViewById(R.id.detail_pagerIndicator);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        housePricePerDay = (TextView) findViewById(R.id.house_price_per_day);
        amenitiesRecycler = (RecyclerView) findViewById(R.id.amenitiesRecycler);
        detailGuestCountTxt = (TextView) findViewById(R.id.detail_guest_count_txt);
        detailRoomStyleTxt = (TextView) findViewById(R.id.detail_room_style_txt);
        detailBedCountTxt = (TextView) findViewById(R.id.detail_bed_count_txt);
        detailBathroomCountTxt = (TextView) findViewById(R.id.detail_bathroom_count_txt);
        detailHouseBtnCheckReserve = (Button) findViewById(R.id.detailHouse_btn_check_reserve);
        detailHouseMoreCalendar = (TextView) findViewById(R.id.detail_house_more_calendar);
        detailHouseMoreExtrafee = (TextView) findViewById(R.id.detail_house_more_extrafee);
        btnWish = (CheckBox) findViewById(R.id.detail_wish);
        btnBack = (ImageView) findViewById(R.id.detail_btn_back);
    }

    private void setRoomPager() {
        DetailImgPager imgPager = new DetailImgPager(houseImages, this);
        detailRoomViewPager.setAdapter(imgPager);
    }

    private void setAmenitiesRecycler() {
        Log.e("Detail", "recycler amen size :: " + amenities.length);
        amenitiesAdapter = new DetailAmenitiesAdapter(this.amenities);
        amenitiesRecycler.setAdapter(amenitiesAdapter);
        amenitiesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setData(House house) {
        detailHouseTitle.setText(house.getTitle());
        detailHostNameTxt.setText(house.getHost().getUsername());
        detailRoomIntro.setText("  " + house.getIntroduce());
        detailRoomtypeTxt.setText(house.getRoom_type());
        housePricePerDay.setText("₩" + house.getPrice_per_day() + "/1박");
        detailRoomStyleTxt.setText("방 " + house.getBedrooms() + "개");
        detailBathroomCountTxt.setText("욕실 " + house.getBathrooms() + "개");
        detailGuestCountTxt.setText("게스트 " + house.getAccommodates() + "명");
        detailBedCountTxt.setText("침대 " + house.getBeds() + "개");
        btnWish.setChecked(house.isWished());

        GlideApp.with(this)
                .load(house.getHost().getImg_profile())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .circleCrop()
                .into(detailHostImg);
    }

    // 외부 라이브러리를 사용해 완성도를 viewpager indicator를 viewpager와 연결하였다.
    private void setPagerIndicator() {
        indicator = (CircleIndicator) findViewById(R.id.detail_pagerIndicator);
        indicator.setViewPager(viewPager);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    private void setViewPager() {
        viewPager = (ViewPager) findViewById(R.id.detail_room_viewPager);
        mAdapter = new DetailImgPager(house.getHouse_images(), this);
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.detail_host_img:
                intent = new Intent(getBaseContext(), HostInfoActivity.class);
                intent.putExtra(HOST_INFO, house.getHost());
                startActivity(intent);
                break;

            case R.id.btnTranslate:
                doTranslate();
                break;

            case R.id.mapFrameLayout:
                intent = new Intent(DetailHouseActivity.this, DetailMapActivity.class);
                intent.putExtra(HOUSE_LATLNG, house.getLatLng());
                intent.putExtra(HOUSE_ADDRESS, house.getAddress());
                startActivity(intent);
                break;

            case R.id.detailHouse_btn_check_reserve:
                doReservation(isDateSelected);
                break;

            case R.id.detail_house_more_calendar:
                goCalendar();
                break;

            case R.id.detail_house_more_extrafee:
                intent = new Intent(v.getContext(), DetailExtraFeeActivity.class);
                intent.putExtra(DETAIL_HOUSE, house);
                v.getContext().startActivity(intent);
                break;

            case R.id.detail_btn_back:
                onBackPressed();
                break;
        }
    }

    private void setOnClick() {
        mapFrame.setClickable(true);
        detailHouseMoreCalendar.setClickable(true);
        detailHostImg.setOnClickListener(this);
        btnTranslate.setOnClickListener(this);
        mapFrame.setOnClickListener(this);
        detailHouseBtnCheckReserve.setOnClickListener(this);
        detailHouseMoreCalendar.setOnClickListener(this);
        detailHouseMoreExtrafee.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        btnWish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Loader.postWishList("Token " + PreferenceUtil.getToken(buttonView.getContext()), house.getPk(), DetailHouseActivity.this);
                } else {
                    Loader.postWishList("Token " + PreferenceUtil.getToken(buttonView.getContext()), house.getPk(), DetailHouseActivity.this);
                }
            }
        });
    }

    private Handler mHandler = null;

    private void doTranslate() {
        mHandler = new Handler();
        new Thread() {
            @Override
            public void run() {
                super.run();
                Translator.doTranslate(house.getIntroduce(), DetailHouseActivity.this);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        detailRoomIntro.setText(house.getIntroduce() + "\n === 한국어 번역 === \n  " + translatedTxt);
                    }
                });
            }
        }.start();
    }

    // TODO 메소드로 분리하기
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("Detail", house.getLatLng().toString());
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);

        // spannable 객체 사용하여 html 직접 작성하는 효과를 text에 줌
        String address = house.getAddress();
        detailHouseMapInfo.setText(getSpannableString(address));
        detailHouseMapInfo.setTextSize(20f);
        detailHouseMapInfo.setTextColor(Color.BLACK);

        // marker title 중복 제거 완료
        Marker marker = googleMap.addMarker(new MarkerOptions().position(house.getLatLng()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(house.getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        marker.showInfoWindow();
    }

    private String translatedTxt = "";


    // SpannableStringBuilder 사용
    private SpannableStringBuilder getSpannableString(String address) {

        // 1. SpannableStringBuilder를 생성한다.
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        String info = "\n" + "정확한 위치는 예약 완료 후에 표시됩니다.";

        // 2. String -> SpannableString 변환해준다.
        SpannableString ssAddress = new SpannableString(address);
        SpannableString ssInfo = new SpannableString(info);

        // 3. SpannableString에 text 설정을 해준다.
        ssAddress.setSpan(new AbsoluteSizeSpan(80), 0, address.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssInfo.setSpan(new AbsoluteSizeSpan(60), 0, info.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 4. SpannableStringBuilder에 append하여 완성된 text를 가진 SpannableStringBuilder를 만들어준다.
        ssb.append(ssAddress);
        ssb.append(ssInfo);

        return ssb;
    }

    @Override
    public void getResult(String jsonString) {
        Gson gson = new Gson();
        Data data = gson.fromJson(jsonString, Data.class);
        translatedTxt = data.getMessage().getResult().getTranslatedText();
    }

    @Override
    public void getWishResponse(String message) {
    // Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getReservationResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        reservation = null;
    }

    // 해당 클래스 이외의 클래스에서 재사용되지 않는 어댑터이므로 이너 클래스로 작성하였다.
    class DetailAmenitiesAdapter extends RecyclerView.Adapter<DetailAmenitiesAdapter.Holder> {

        Amenities[] amenities;

        public DetailAmenitiesAdapter(Amenities[] amenities) {
            this.amenities = amenities;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_amenities_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            // 받아온 amenities 목록에 맞는 이미지 리소스를 조회하여 imageView에 setting한다.
            holder.setAmenityImg(Const.Amenities.getAmenityImg(amenities[position].getName()));
        }

        @Override
        public int getItemCount() {
            return amenities.length;
        }

        class Holder extends RecyclerView.ViewHolder {

            private ImageView amenityImg;

            public Holder(View itemView) {
                super(itemView);
                amenityImg = (ImageView) itemView.findViewById(R.id.amenity_img);
            }

            public void setAmenityImg(int resId) {
                GlideApp
                        .with(getBaseContext())
                        .load(resId)
                        .centerCrop()
                        .into(amenityImg);
            }
        }
    }

    private boolean isDateSelected = false;
    private String checkIn = "";
    private String checkOut = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_HOUSE_ACTIVITY) {
            if (resultCode == 200) {
                checkIn = data.getStringExtra("checkin");
                checkOut = data.getStringExtra("checkout");
                isDateSelected = true;
                detailHouseBtnCheckReserve.setText("예약 진행하기");
            }
        }
    }

    public void removeAllFragments() {
        // 1. backstack에 저장되어 있는 fragment들을 하나하나 pop시킨다.
        for (int i = fragments.length - 1; i > -1; i--) {
            getSupportFragmentManager()
                    .popBackStack();
        }
        // 2. widget과 flag 값을 초기화한다.
        isDateSelected = false;
        detailHouseBtnCheckReserve.setText("예약 가능 여부 확인");
    }

    private void setFragments() {
        // 1. fragment를 생성한다.
        oneStepFragment = new ReservationOneFragment();
        twoStepFragment = new ReservationTwoFragment();
        threeStepFragment = new ReservationThreeFragment();

        // 2. 추후 DetailActivity class 내에서 프레그먼트 관리를 위해 배열에 셋팅한다.
        fragments[0] = oneStepFragment;
        fragments[1] = twoStepFragment;
        fragments[2] = threeStepFragment;
    }

    private void doReservation(boolean isDateSelected) {

        // 1. flag 상태에 따라
        if (isDateSelected) {
            // 2. 예약진행한다.
            goReservationOneStep();
        } else {
            // 3. 달력을 통해 날짜를 먼저 선택한다.
            goCalendar();
        }
    }

    // 예약절차 1step으로 이동하는 메소드
    private void goReservationOneStep() {

        // 1. 초기화된 fragment에 presenter 역할을 하는 activity를 넘겨준다.
        oneStepFragment.setPresenter(this);

        // 2. fragment를 호출한다.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, oneStepFragment)
                .addToBackStack(null)
                .commit();

        // 3. 예약 객체에 예약날짜를 저장한다.
        reservation = new Reservation(checkIn, checkOut, house);

        // 4. flag 값을 false로 재설정
        isDateSelected = false;
    }

    private void goCalendar() {
        Intent intent = new Intent(DetailHouseActivity.this, CalendarActivity.class);
        intent.putExtra(DETAIL_HOUSE, house);
        startActivityForResult(intent, DETAIL_HOUSE_ACTIVITY);

        // activity 전환효과를 위해 anim에 전환효과 설정값 셋팅 후, 아래 메소드를 통해 전환효과를 준다.
        overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
    }

    public void postReservation() throws UnsupportedEncodingException {
        Log.e("DetailHouseActivity", reservation.getHouse().getPk() + ", " + reservation.getCheckin_date() + ", " + reservation.getCheckout_date());
        Loader.postReservation("Token " + PreferenceUtil.getToken(this), reservation.getHouse().getPk(), reservation.getCheckin_date(), reservation.getCheckout_date(), this);
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}

