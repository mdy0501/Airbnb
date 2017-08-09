package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.util.GlideApp;

import java.util.List;

/**
 * Created by MDY on 2017-08-05.
 */

public class PlaceSecondAdapter extends PagerAdapter {

    List<House> data;
    LayoutInflater inflater;

    public PlaceSecondAdapter(Context context, List<House> data){
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.place_item, container,false);
        House house = data.get(position);

        ImageView img = (ImageView) view.findViewById(R.id.img);
        TextView txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);

        txtPrice.setText(house.getPrice_per_day() + " 원");
        txtTitle.setText(house.getTitle());

        House_images[] images = house.getHouse_images();
        if(images.length > 0){
            Log.e("image",images[0].getImage());
        }

        GlideApp
                .with(inflater.getContext())
                .load(images.length > 0 ? images[0].getImage() : null)
                .centerCrop()
                .fallback(R.drawable.question_mark)
                .into(img);
        ((ViewPager) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        ((ViewPager) container).removeView((View) object);
    }
}