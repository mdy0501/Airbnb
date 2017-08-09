package com.android.airbnb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.util.GlideApp;

import java.util.List;

/**
 * Created by MDY on 2017-08-02.
 */

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.Holder> {

    List<House> data;
    LayoutInflater inflater;
    int count = 0;

    public RoomsAdapter(Context context, List<House> data){
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rooms_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        House house = data.get(position);
        holder.setPrice(house.getPrice_per_day());
        holder.setTitle(house.getTitle());
        holder.setRoomType(house.getRoom_type());
        holder.setRatingBar("4");
        holder.setReviewCount("110개");


        holder.setPosition(position);

//        House_images[] images = house.getHouse_images();
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
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        int position = -1;
        TextView txtPrice, txtTitle, txtRoomType, txtReview, txtReviewCount;
        ImageView img;
        ImageButton imgBtnHeart;
        RatingBar ratingBar;

        public Holder(View itemView) {
            super(itemView);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtRoomType = (TextView) itemView.findViewById(R.id.txtRoomType);
            txtReview = (TextView) itemView.findViewById(R.id.txtReview);
            txtReviewCount = (TextView) itemView.findViewById(R.id.txtReviewCount);
            img = (ImageView) itemView.findViewById(R.id.img);
            imgBtnHeart = (ImageButton) itemView.findViewById(R.id.imgBtnHeart);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailHouseActivity.class);
                     House roomsHouse = data.get(position);
                    Bundle extra = new Bundle();
                    extra.putString("key", "roomsHouse");
                    extra.putParcelable("roomsHouse", roomsHouse);
//                     intent.putExtra("roomsHouse", roomsHouse);
//                    intent.putExtra("key", "roomsHouse");
                    intent.putExtras(extra);
                     v.getContext().startActivity(intent);
                }
            });

        }

        private void setPrice(String price){
            txtPrice.setText(price + " 원");
        }
        private void setTitle(String title){
            txtTitle.setText(title);
        }
        private void setRoomType(String roomType){
            txtRoomType.setText(roomType);
        }
        private void setPosition(int position){
            this.position = position;
        }
        private void setRatingBar(String ratingBar) {
            this.ratingBar.setRating(Float.parseFloat(ratingBar));
        }
        private void setReviewCount(String reviewCount) {
            this.txtReviewCount.setText(reviewCount);
        }
    }
}
