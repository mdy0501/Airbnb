package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.util.GlideApp;

/**
 * Created by JunHee on 2017. 8. 2..
 */

public class DetailImgPager extends PagerAdapter {

    private House_images[] mimgUrl;
    private Context mContext;

    public DetailImgPager(House_images[] imgUrl, Context mContext) {
        Log.e("imgpager", "img size" + imgUrl.length);
        this.mContext = mContext;
        this.mimgUrl = imgUrl;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewItem = inflater.inflate(R.layout.detail_house_fragment, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.detail_viewpager_img);
        GlideApp
                .with(mContext)
                .load(mimgUrl.length > 0 ? mimgUrl[position].getImage() : null)
                .fallback(R.mipmap.dummy_room)
                .into(imageView);
        ((ViewPager) container).addView(viewItem);
        return viewItem;
    }

    /* image count check 하기 */
    @Override
    public int getCount() {
        return mimgUrl.length;
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
