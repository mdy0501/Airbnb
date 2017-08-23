package com.android.airbnb;

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
    final SpannableStringBuilder sps = new SpannableStringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);

        getExIntent();
        setSpannableString();
        setMap();
        init();
    }

    private void getExIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            houseAddress = bundle.getString(DetailHouseActivity.HOUSE_ADDRESS);
            houseLatlng = (LatLng) bundle.get(DetailHouseActivity.HOUSE_LATLNG);
        }
    }

    private void setMap() {
        MapsInitializer.initialize(getApplicationContext());
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

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
        MapUtil.drawCircle(mMap, houseLatlng);
        // airbnb는 정책상, 예약이 완료 후 숙소의 정확한 위치 정보를 제공하는 플로우를 따라했다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(houseLatlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13f));
    }

    private void init() {
        detailMapAddress = (TextView) findViewById(R.id.detail_map_address);
        detailMapToolbar = (Toolbar) findViewById(R.id.detail_map_toolbar);
        setToolbar();
        detailMapAddress.setText(sps);
    }

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
