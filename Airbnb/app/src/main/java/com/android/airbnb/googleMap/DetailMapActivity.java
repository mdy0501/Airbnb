package com.android.airbnb.googleMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

import com.android.airbnb.detailActivity.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.util.MapUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.tsengvn.typekit.TypekitContextWrapper;

public class DetailMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng houseLatlng;
    private String houseAddress;
    private TextView detailMapAddress;
    private Toolbar detailMapToolbar;
    private SupportMapFragment mapFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);

        getExIntent();
        setSpannableString();
        setMap();
        init();
    }

    // DetailHouseActivity에서 map OnClick 이벤트가 발생했을 때, 인텐트에 필요한 정보를 전달받는다.
    private void getExIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            houseAddress = bundle.getString(DetailHouseActivity.HOUSE_ADDRESS);
            houseLatlng = (LatLng) bundle.get(DetailHouseActivity.HOUSE_LATLNG);
        }
    }

    // GoogleMap을 셋팅한다.
    private void setMap() {
        MapsInitializer.initialize(getApplicationContext());
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // 상단 Toolbar를 셋팅한다.
    private void setToolbar() {
        setSupportActionBar(detailMapToolbar);
        getSupportActionBar().setTitle("위치");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // 초기 onMapReady 콜백 시 맵을 셋팅한다.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 1. airbnb는 정책상, 예약이 완료 후 숙소의 정확한 위치 정보를 제공하는 플로우를 참고하였다.
        // 1.1. 자세한 주소 공개 X
        // 1.2. 반경으로 대략적인 위치 표시
        // 1.3. 따라서 MapUtil의 drawCircle을 활용하여 반경을 map 상에 표시했다.
        MapUtil.drawCircle(mMap, houseLatlng);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(houseLatlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13f));
    }

    private void init() {
        detailMapAddress = (TextView) findViewById(R.id.detail_map_address);
        detailMapToolbar = (Toolbar) findViewById(R.id.detail_map_toolbar);
        setToolbar();
        detailMapAddress.setText(sps);
    }

    // String의 속성 값을 다르게 하기 위해 SpannableString과 SpannableStringBuilder를 활용했다.
    private SpannableStringBuilder sps = new SpannableStringBuilder();
    private void setSpannableString(){

        SpannableString ss = new SpannableString(houseAddress);
        ss.setSpan(new AbsoluteSizeSpan(70), 0, houseAddress.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sps.append(ss);

        String info = "\n 정확한 위치는 예약 완료 후에 표시됩니다.";
        ss = new SpannableString(info);

        ss.setSpan(new AbsoluteSizeSpan(50), 0, info.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sps.append(ss);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
