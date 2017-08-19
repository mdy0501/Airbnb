package com.android.airbnb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.util.GlideApp;
import com.android.airbnb.util.Remote.ITask;

import java.util.List;

/**
 * Created by MDY on 2017-08-02.
 */

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.Holder> implements ITask.postWishList {

    private List<House> houses;
    private LayoutInflater inflater;
    private Context context;
    int position = 0;


    public RoomsAdapter(Context context, List<House> data) {
        this.houses = data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rooms_item, parent, false);
        return new Holder(view);
    }

    public void refreshData(List<House> houses) {
        this.houses = houses;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        House house = houses.get(position);
        holder.setPrice(house.getPrice_per_day());
        holder.setTitle(house.getTitle());
        holder.setRoomType(house.getRoom_type());
        holder.setBtnSave(house.isWished());
        holder.setRatingBar("4");
        holder.setReviewCount("110개");
        holder.setPosition(position);
        House_images[] images = house.getHouse_images();

        GlideApp
                .with(inflater.getContext())
                .load(images.length > 0 ? images[0].getImage() : null)
                .centerCrop()
                .fallback(R.drawable.question_mark)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return houses.size();
    }

    @Override
    public void getWishResponse(String message) {
        Toast.makeText(context, "msg : " + message, Toast.LENGTH_SHORT).show();
    }

    class Holder extends RecyclerView.ViewHolder {
        int position = -1;
        TextView txtPrice, txtTitle, txtRoomType, txtReview, txtReviewCount;
        ImageView img;
        CheckBox btnSave;
        RatingBar ratingBar;

        public Holder(View itemView) {
            super(itemView);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtRoomType = (TextView) itemView.findViewById(R.id.txtRoomType);
            txtReview = (TextView) itemView.findViewById(R.id.txtReview);
            txtReviewCount = (TextView) itemView.findViewById(R.id.txtReviewCount);
            img = (ImageView) itemView.findViewById(R.id.img);
            btnSave = (CheckBox) itemView.findViewById(R.id.houselist_wish);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailHouseActivity.class);
                    House roomsHouse = houses.get(position);
                    Bundle extra = new Bundle();
                    extra.putString("key", "roomsHouse");
                    extra.putParcelable("roomsHouse", roomsHouse);
                    intent.putExtras(extra);
                    v.getContext().startActivity(intent);
                }
            });

//            btnSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked)
//                        Loader.postWishList("Token " + PreferenceUtil.getToken(context), houses.get(position).getPk(), RoomsAdapter.this);
//                    else
//                        Loader.postWishList("Token " + PreferenceUtil.getToken(context), houses.get(position).getPk(), RoomsAdapter.this);
//                }
//            });
        }

        private void setPrice(String price) {
            txtPrice.setText(price + " 원");
        }

        private void setTitle(String title) {
            txtTitle.setText(title);
        }

        private void setRoomType(String roomType) {
            txtRoomType.setText(roomType);
        }

        private void setPosition(int position) {
            this.position = position;
        }

        private void setRatingBar(String ratingBar) {
            this.ratingBar.setRating(Float.parseFloat(ratingBar));
        }

        private void setReviewCount(String reviewCount) {
            this.txtReviewCount.setText(reviewCount);
        }

        public void setBtnSave(boolean isWished) {
            this.btnSave.setChecked(isWished);
        }
    }
}
