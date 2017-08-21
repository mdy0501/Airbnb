package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
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

    private Context mContext;

    /* data 갈아 끼울 것 */
    private List<House> houseList;

    public WishListDetailAdapter(List<House> house, Context mContext) {
        this.mContext = mContext;
        this.houseList = house;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        /* data 갈아 끼울 것 */
        House house = houseList.get(position);
        holder.setTitle(house.getTitle());
        House_images[] houseImages = house.getHouse_images();
        holder.setHouseImg(houseImages.length >0 ? houseImages[0].getImage() : R.mipmap.dummy_host_img+"");
        holder.setHouseType(house.getRoom_type());
        holder.setPrice("₩" + house.getPrice_per_day());
        holder.setRatingBar("4");
        holder.setReviewCount("40");
        holder.setBtnSave(house.isWished());
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    @Override
    public void getWishResponse(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
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
            btnSave = (CheckBox) view.findViewById(R.id.wishlist_btnSave);
            price = (TextView) view.findViewById(R.id.wishlist_house_price);
            title = (TextView) view.findViewById(R.id.wishlist_house_title);
            houseType = (TextView) view.findViewById(R.id.wishlist_house_type);
            ratingBar = (RatingBar) view.findViewById(R.id.wishlist_house_ratingbar);
            reviewCount = (TextView) view.findViewById(R.id.wishlist_review_count);

            btnSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        Loader.postWishList("Token " + PreferenceUtil.getToken(mContext), houseList.get(position).getPk(), WishListDetailAdapter.this);
                    Toast.makeText(mContext, "isChecked :: " + isChecked, Toast.LENGTH_SHORT).show();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "item clicked ======= done! ", Toast.LENGTH_SHORT).show();

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

        public void setBtnSave(boolean isSaved) {
            this.btnSave.setChecked(isSaved);
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
