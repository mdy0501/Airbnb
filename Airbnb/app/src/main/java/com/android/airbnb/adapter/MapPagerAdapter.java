package com.android.airbnb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.detailActivity.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.util.GlideApp;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public class MapPagerAdapter extends PagerAdapter implements ITask.postWishList {

    private List<House> houseList = new ArrayList<>();
    private Context context;
    private TextView housePrice;
    private TextView houseTitle;
    private ImageView houseImg;
    private RatingBar ratingBar;
    private TextView houseReviewCount;
    private CheckBox btnWish;
    public static final String HOUSE_OBJ = "house obj";
    private View view = null;
    private int currentPostition = 0;
    public OnMapPagerListener onMapPagerListener;
    private CoordinatorLayout snackPlace;


    public MapPagerAdapter(List<House> houseList, OnMapPagerListener mapPagerListener, Context context) {
        this.houseList = houseList;
        this.context = context;
        this.onMapPagerListener = mapPagerListener;
    }

    public void setCurrentPostition(int currentPostition) {
        this.currentPostition = currentPostition;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        view = LayoutInflater.from(context).inflate(R.layout.googlemap_viewpager_item, parent, false);
        setWidget(view);
        connectDate(position);
        ((ViewPager) parent).addView(view, 0);
        setOnClick();
        return view;
    }

    private void setOnClick() {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailHouseActivity.class);
                House house = houseList.get(currentPostition);
                intent.putExtra(HOUSE_OBJ, house);
                intent.putExtra("key", HOUSE_OBJ);
                v.getContext().startActivity(intent);
            }
        });

        btnWish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnWishIsClicked(isChecked);
            }
        });
    }

    // button wish의 onClick 발생 시, 수행해야하는 작업들을 모아 메소드화하였다.
    private void btnWishIsClicked(boolean isChecked) {

        // 1. click 이벤트가 발생한 house의 wish 값 변경
        houseList.get(currentPostition).setWished(isChecked);

        // 2. wishlist 업데이트를 위해 서버와 통신
        Loader.postWishList("Token " + PreferenceUtil.getToken(context), houseList.get(currentPostition).getPk(), MapPagerAdapter.this);

        // 3. parent actvity의 ui 변경을 위해 인터페이스로 callback
        onMapPagerListener.btnWishClicked(isChecked);
    }

    private void connectDate(int position) {
        House house = houseList.get(position);
        Log.e("MapAdapter :: house", house.getTitle());
        House_images house_images[] = house.getHouse_images();
        housePrice.setText("₩" + house.getPrice_per_day());
        houseTitle.setText(house.getTitle());

        // dummy data 임시로 연결, DB 완성 후 수정할 계획
        ratingBar.setRating(/* 하우스 별점 넣기 */ 3.5f);
        houseReviewCount.setText(/* 리뷰 카운트 넣기*/ "123");
        if (house.isWished())
            setBtnWish(btnWish, R.drawable.icon_wish_full, true);

        // house_images.length를 활용하여 house image 셋팅 시, NullPointerException 처리를 한다.
        GlideApp.with(context)
                .load(house_images.length > 0 ? house_images[0].getImage() : null)
                .placeholder(R.mipmap.ic_launcher)
                .into(houseImg);
    }

    private void setBtnWish(CompoundButton buttonView, int resId, boolean isChecked) {
        buttonView.setChecked(isChecked);
        houseList.get(currentPostition).setWished(isChecked);
    }

    private void setWidget(View view) {
        housePrice = (TextView) view.findViewById(R.id.house_price);
        houseTitle = (TextView) view.findViewById(R.id.house_title);
        houseImg = (ImageView) view.findViewById(R.id.house_img);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        houseReviewCount = (TextView) view.findViewById(R.id.house_review_count);
        btnWish = (CheckBox) view.findViewById(R.id.checkBox);
        snackPlace = (CoordinatorLayout) view.findViewById(R.id.snackbar_place);
    }

    public CoordinatorLayout getSnackPlace() {
        return snackPlace;
    }

    public void setSnackPlace(CoordinatorLayout snackPlace) {
        this.snackPlace = snackPlace;
    }

    @Override
    public int getCount() {
        return houseList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return (0.9f);
    }

    @Override
    public void getWishResponse(String message) {

    }

    public interface OnMapPagerListener {
        public void btnWishClicked(boolean btnisChecked);
    }
}
