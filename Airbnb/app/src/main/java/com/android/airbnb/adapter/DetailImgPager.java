package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.airbnb.R;
import com.bumptech.glide.Glide;

/**
 * Created by JunHee on 2017. 8. 2..
 */

public class DetailImgPager extends PagerAdapter {

    Context mContext;
    int[] imgId = {R.mipmap.dummy_room2, R.mipmap.dummy_room, R.mipmap.dummy_room3};

    public DetailImgPager(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewItem = inflater.inflate(R.layout.detail_house_fragment, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.detail_viewpager_img);
        Glide
                .with(mContext)
                .load(imgId[position])
                .into(imageView);
        ((ViewPager)container).addView(viewItem);
        return viewItem;
    }

    @Override
    public int getCount() {
        return imgId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
