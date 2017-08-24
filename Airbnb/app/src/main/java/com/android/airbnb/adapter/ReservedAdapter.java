package com.android.airbnb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.detailActivity.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.util.GlideApp;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 22..
 */

public class ReservedAdapter extends RecyclerView.Adapter<ReservedAdapter.Holder> {

    private Context mContext;
    private List<House> houseList;
    public static final String RESERVED_ADAPTER = "com.android.airbnb.RESERVED_ADAPTER";

    public ReservedAdapter(List<House> house, Context mContext) {
        this.mContext = mContext;
        this.houseList = house;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserved_house_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        House house = houseList.get(position);
        holder.setTitle(house.getTitle());
        House_images[] houseImages = house.getHouse_images();
        holder.setHouseImg(houseImages.length >0 ? houseImages[0].getImage() : R.mipmap.dummy_host_img+"");
        holder.setHouseType(house.getRoom_type());
        holder.setPrice("₩" + house.getPrice_per_day());
        holder.setRatingBar("4");
        holder.setReviewCount("40");
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView houseImg;
        private CheckBox btnSave;
        private TextView price;
        private TextView title;
        private TextView houseType;
        private RatingBar ratingBar;
        private TextView reviewCount;
        private int position;

        public Holder(View view) {
            super(view);
            houseImg = (ImageView) view.findViewById(R.id.wishlist_img);
            price = (TextView) view.findViewById(R.id.wishlist_house_price);
            title = (TextView) view.findViewById(R.id.wishlist_house_title);
            houseType = (TextView) view.findViewById(R.id.wishlist_house_type);
            ratingBar = (RatingBar) view.findViewById(R.id.wishlist_house_ratingbar);
            reviewCount = (TextView) view.findViewById(R.id.wishlist_review_count);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailHouseActivity.class);
                    intent.putExtra("key", RESERVED_ADAPTER);
                    intent.putExtra(RESERVED_ADAPTER, houseList.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setHouseImg(String imgUrl) {
            GlideApp
                    .with(mContext)
                    .load(imgUrl)
                    .centerCrop()
                    .into(houseImg);
        }

        public void setPrice(String price) {
            this.price.setText(price);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setHouseType(String houseType) {
            this.houseType.setText(houseType);
        }

        public void setRatingBar(String ratingBar) {
            this.ratingBar.setRating(Float.parseFloat(ratingBar));
        }

        public void setReviewCount(String reviewCount) {
            this.reviewCount.setText(reviewCount);
        }

        public int getHousePistion() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
