package com.android.airbnb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.detailActivity.DetailHouseActivity;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.util.GlideApp;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 8..
 * wishlist의 담겨있는 아이템을 보여주는 리스트어댑터
 */

public class WishListDetailAdapter extends RecyclerView.Adapter<WishListDetailAdapter.Holder> implements ITask.postWishList {

    private Context context;
    private List<House> wishList;
    public static final String WISHLIST_HOUSE = "com.android.airbnb.adapter.WISHLIST_HOUSE";

    public WishListDetailAdapter(List<House> house, Context mContext) {
        this.context = mContext;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);
        return new Holder(view);
    }

    public void refreshData(List<House> wishlist) {
        this.wishList = wishlist;
        notifyDataSetChanged();
    }

    private Holder currentHolder;

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        /* data 갈아 끼울 것 */
        Log.e("WishListAdapter", "postition : " + position);
        House house = wishList.get(position);
        currentHolder = holder;
        holder.setTitle(house.getTitle());
        House_images[] houseImages = house.getHouse_images();
        holder.setHouseImg(houseImages.length > 0 ? houseImages[0].getImage() : R.mipmap.dummy_host_img + "");
        holder.setHouseType(house.getRoom_type());
        holder.setPrice("₩" + house.getPrice_per_day());
        holder.setRatingBar("4");
        holder.setReviewCount("40");
        holder.setBtnWish(house.isWished());
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    @Override
    public void getWishResponse(String message) {
        // response 메시지 받는 부분
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView houseImg;
        private CheckBox btnWish;
        private TextView price;
        private TextView title;
        private TextView houseType;
        private RatingBar ratingBar;
        private TextView reviewCount;
        private int position;

        public Holder(View view) {
            super(view);
            houseImg = (ImageView) view.findViewById(R.id.wishlist_img);
            btnWish = (CheckBox) view.findViewById(R.id.wishlist_btnSave);
            price = (TextView) view.findViewById(R.id.wishlist_house_price);
            title = (TextView) view.findViewById(R.id.wishlist_house_title);
            houseType = (TextView) view.findViewById(R.id.wishlist_house_type);
            ratingBar = (RatingBar) view.findViewById(R.id.wishlist_house_ratingbar);
            reviewCount = (TextView) view.findViewById(R.id.wishlist_review_count);
            btnWish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecked) {
                        Log.e("WishAdapter", "isChecked : " + isChecked);
                        Loader.postWishList("Token " + PreferenceUtil.getToken(context), wishList.get(position).getPk(), WishListDetailAdapter.this);
                    }
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailHouseActivity.class);
                    intent.putExtra("key", WISHLIST_HOUSE);
                    intent.putExtra(WISHLIST_HOUSE, wishList.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setHouseImg(String imgUrl) {
            GlideApp
                    .with(context)
                    .load(imgUrl)
                    .centerCrop()
                    .into(houseImg);
        }

        public void setBtnWish(boolean isSaved) {
            this.btnWish.setChecked(isSaved);
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
