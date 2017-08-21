package com.android.airbnb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.adapter.BottomSheetAdapter;
import com.android.airbnb.adapter.DetailImgPager;
import com.android.airbnb.adapter.MapPagerAdapter;
import com.android.airbnb.calendar.CalendarActivity;
import com.android.airbnb.domain.airbnb.Amenities;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.domain.reservation.Reservation;
import com.android.airbnb.papago.Translator;
import com.android.airbnb.papago.domain.Data;
import com.android.airbnb.util.Const;
import com.android.airbnb.util.GlideApp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.tsengvn.typekit.TypekitContextWrapper;

import me.relex.circleindicator.CircleIndicator;

public class DetailHouseActivity extends AppCompatActivity implements OnMapReadyCallback, Translator.IPapago, View.OnClickListener {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_house);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detail_house_mapFragment);

        getExIntent();
        initView();
        setData(this.house);
        mapFragment.getMapAsync(this);
        setRoomPager();
        setAmenitiesRecycler();
        setViewPager();
        setPagerIndicator();
        setOnClick();
    }

    // DetailMapViewPagerActivity로 부터 전달 받은 intent를 꺼낸다.
    private void getExIntent() {
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra.getString("key").equals("roomsHouse")) {
            house = extra.getParcelable("roomsHouse");
            houseImages = house.getHouse_images();
            amenities = house.getAmenities();

        } else if (extra.getString("key").equals(MapPagerAdapter.HOUSE_OBJ)) {
            house = extra.getParcelable(MapPagerAdapter.HOUSE_OBJ);
            houseImages = house.getHouse_images();
            amenities = house.getAmenities();

        } else if (extra.getString("key").equals(BottomSheetAdapter.BOTTOM_SHEET_ADAPTER_PK)) {
            house = extra.getParcelable(BottomSheetAdapter.BOTTOM_SHEET_ADAPTER_PK);
            houseImages = house.getHouse_images();
            amenities = house.getAmenities();
        }
    }

    private void initView() {
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
        detailHouseReviewRegisterDate = (TextView) findViewById(R.id.detail_house_review_register_date);
        detailReivewHostImg = (ImageView) findViewById(R.id.detail_reivew_host_img);
        detailHouseReviewContent = (TextView) findViewById(R.id.detail_house_review_content);
        detailHouseReviewCount = (TextView) findViewById(R.id.detail_house_review_count);
        detailHouseReviewRatingbar = (RatingBar) findViewById(R.id.detail_house_review_ratingbar);
        detailHouseReadReview = (TextView) findViewById(R.id.detail_house_read_review);
    }

    private void setReviews(){
        // Review data가 넘어오면 셋팅한다..
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
        Log.e("Detail", "imgs size === :  " + house.getHouse_images().length);
        mAdapter = new DetailImgPager(house.getHouse_images(), this);
        viewPager.setAdapter(mAdapter);
    }

    Handler mHandler = null;

    private void setOnClick() {
        detailHostImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HostInfoActivity.class);
                intent.putExtra(HOST_INFO, house.getHost());
                startActivity(intent);
            }
        });
        // papago api를 통해 영문 -> 한글 번역한다.
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler = new Handler();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Log.e("detail", "========= doTranslate ======== ");
                        Translator.doTranslate(house.getIntroduce(), DetailHouseActivity.this);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("thread", "thread txt :: " + translatedTxt);
                                detailRoomIntro.setText(house.getIntroduce() + "\n === 한국어 번역 === \n  " + translatedTxt);
                            }
                        });
                    }
                }.start();
            }
        });
        mapFrame.setClickable(true);
        mapFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailHouseActivity.this, DetailMapActivity.class);
                intent.putExtra(HOUSE_LATLNG, house.getLatLng());
                intent.putExtra(HOUSE_ADDRESS, house.getAddress());
                startActivity(intent);
            }
        });

        detailHouseBtnCheckReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  /* 수정하기 */
//                Log.e("CalendarActivity", "house pk : " + house.getPk());
//                Reservation reservation = new Reservation();
//                reservation.setCheckout_date(checkoutDate);
//                reservation.setCheckin_date(checkinDate);
//                Gson gson = new Gson();
//                String jsonString = gson.toJson(reservation);
//                String queryString = "?house=" + house.getPk();
//                Log.e("CalendarAct", "query : " + queryString);
//                try {
//                    Loader.postReservation("Token" + PreferenceUtil.getToken(this), queryString, checkinDate, checkoutDate);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
            }
        });

        detailHouseMoreCalendar.setClickable(true);
        detailHouseMoreCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarActivity.class);
                intent.putExtra(DETAIL_HOUSE, house);
                startActivityForResult(intent, DETAIL_HOUSE_ACTIVITY);
                // activity 전환효과를 위해 anim에 전환효과 설정값 셋팅
                // 아래 메소드를 통해 전환효과 설정
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay);

            }
        });

        detailHouseMoreExtrafee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailExtraFeeActivity.class);
                intent.putExtra(DETAIL_HOUSE, house);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("Detail", house.getLatLng().toString());
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        /* spannable 객체 사용법 */
        String address = house.getAddress();
        String title = address + "\n" + "정확한 위치는 예약 완료 후에 표시됩니다.";
        SpannableStringBuilder builder = new SpannableStringBuilder(title);
        builder.setSpan(title, 0, address.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        detailHouseMapInfo.setText(title);
        detailHouseMapInfo.setTextColor(Color.BLACK);
        // marker title 제거
        Marker marker = googleMap.addMarker(new MarkerOptions().position(house.getLatLng()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(house.getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        marker.showInfoWindow();
    }

    private String translatedTxt = "";

    @Override
    public void getResult(String jsonString) {
        Gson gson = new Gson();
        Data data = gson.fromJson(jsonString, Data.class);
        translatedTxt = data.getMessage().getResult().getTranslatedText();
    }

    @Override
    public void onClick(View v) {
        // OnClickListener 정리하기
    }

    /* 재사용하지 않는 어댑터 - 이너 클래스로 작성 */
    class DetailAmenitiesAdapter extends RecyclerView.Adapter<DetailAmenitiesAdapter.Holder> {

        Amenities[] amenities;

        public DetailAmenitiesAdapter(Amenities[] amenities) {
            Log.e("adapter", "amenities" + amenities.length);
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
            Log.e("amen", "adapter :: amen name" + position + ", " + amenities[position].getName());
            holder.setAmenityImg(Const.Amenities.getAmenityImg(amenities[position].getName()));
        }

        @Override
        public int getItemCount() {
            return amenities.length;
        }

        class Holder extends RecyclerView.ViewHolder {

            private ImageView amenityImg;

            // toolbug 발견 -> () 안에 typecasting 안해줘도 빨간줄이 뜨지 않는 문제...
            // 해결 완료
            public Holder(View itemView) {
                super(itemView);
                amenityImg = (ImageView) itemView.findViewById(R.id.amenity_img);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* 액티비티 넘기기 만들기 */
                        Intent intent = new Intent();
                    }
                });
            }

            // amenities 이미지를 setting한다..
            public void setAmenityImg(int resId) {
                GlideApp
                        .with(getBaseContext())
                        .load(resId)
                        .centerCrop()
                        .into(amenityImg);
            }
        }
    }

    private Reservation reservation;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_HOUSE_ACTIVITY) {
            if (resultCode == 200) {
                reservation = new Reservation();
                reservation.setCheckin_date(data.getStringExtra("checkin"));
                reservation.setCheckout_date(data.getStringExtra("checkout"));
                Log.e("DetailHouseActivity", reservation.toString());
            }
        }
    }

    public void setHousePricePerDay(TextView housePricePerDay) {
        this.housePricePerDay = housePricePerDay;
    }

    public void setDetailHostImg(ImageView detailHostImg) {
        this.detailHostImg = detailHostImg;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setDetailRoomDateMin(TextView detailRoomDateMin) {
        this.detailRoomDateMin = detailRoomDateMin;
    }

    public void setTextView7(TextView textView7) {
        this.textView7 = textView7;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public void setTxtType(TextView txtType) {
        this.txtType = txtType;
    }

    public void setDetailRoomIntro(TextView detailRoomIntro) {
        this.detailRoomIntro = detailRoomIntro;
    }

    public void setTxtPrice(TextView txtPrice) {
        this.txtPrice = txtPrice;
    }

    public void setDetailHouseTitle(TextView detailHouseTitle) {
        this.detailHouseTitle = detailHouseTitle;
    }

    public void setDetailHostNameTxt(TextView detailHostNameTxt) {
        this.detailHostNameTxt = detailHostNameTxt;
    }

    public void setDetailRoomtypeTxt(TextView detailRoomtypeTxt) {
        this.detailRoomtypeTxt = detailRoomtypeTxt;
    }

    public void setDetailRoomViewPager(ViewPager detailRoomViewPager) {
        this.detailRoomViewPager = detailRoomViewPager;
    }

    public void setDetailPagerIndicator(CircleIndicator detailPagerIndicator) {
        this.detailPagerIndicator = detailPagerIndicator;
    }

    public void setRatingBar2(float count) {
        this.ratingBar2.setRating(count);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}

