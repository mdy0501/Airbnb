package com.android.airbnb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.House;
import com.android.airbnb.domain.House_images;
import com.android.airbnb.util.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public class MapPagerAdapter extends PagerAdapter {

    private List<House> houseList = new ArrayList<>();
    private Context mContext;
    private TextView housePrice;
    private TextView houseTitle;
    private ImageView houseImg;
    private RatingBar ratingBar;
    private TextView houseReviewCount;
    private CheckBox checkBox;
    public static final String HOUSE_OBJ = "house obj";
    public static final String HOUSE_IMG = "house img";

    public MapPagerAdapter(List<House> houseList, Context context) {
        this.houseList = houseList;
        this.mContext = context;
    }

    View view = null;

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        view = LayoutInflater.from(mContext).inflate(R.layout.googlemap_viewpager_item, parent, false);
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
                Intent intent = new Intent(mContext, DetailHouseActivity.class);
                /* index 값 같이 넘기기 */
                intent.putExtra(HOUSE_OBJ, house);
                v.getContext().startActivity(intent);
            }
        });
    }

    House house;

    private void connectDate(int position) {
        house = houseList.get(position);
        Log.e("MapAdapter :: house", house.getTitle());
        House_images house_images[] = house.getHouse_images();
        housePrice.setText(house.getPrice_per_day());
        houseTitle.setText(house.getTitle());
        // dummy, 추후 수정 요망
        ratingBar.setRating(3.5f);
        checkBox.setChecked(true);
        houseReviewCount.setText("123");

        /* house_images.length 체크 */
        GlideApp.with(mContext)
                .load(house_images.length > 0 ? house_images[0].getImage() : null)
                .placeholder(R.mipmap.dummy_host_img)
                .into(houseImg);
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

    private void setWidget(View view) {
        housePrice = (TextView) view.findViewById(R.id.house_price);
        houseTitle = (TextView) view.findViewById(R.id.house_title);
        houseImg = (ImageView) view.findViewById(R.id.house_img);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        houseReviewCount = (TextView) view.findViewById(R.id.house_review_count);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
    }

    @Override
    public float getPageWidth(int position) {
        return (0.9f);
    }

}
