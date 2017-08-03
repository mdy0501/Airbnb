package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.data.RoomsData;

import java.util.List;

/**
 * Created by MDY on 2017-08-02.
 */

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.Holder> {

    List<RoomsData> data;
    LayoutInflater inflater;


    public RoomsAdapter(List<RoomsData> roomsData, Context context){
        data = roomsData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rooms_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        RoomsData roomsData = data.get(position);
//        holder.txtPrice.setText("$" + roomsData.);
//        holder.setPrice();
//        holder.setDescription(roomsData.);
    }

    @Override
    public int getItemCount() {
        // TODO
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView txtPrice, txtDescripton, txtType, txtReview, txtReviewCount;
        ImageView img;
        ImageButton imgBtnHeart;
        RatingBar ratingBar;

        public Holder(View itemView) {
            super(itemView);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtDescripton = (TextView) itemView.findViewById(R.id.txtDescription);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtReview = (TextView) itemView.findViewById(R.id.txtReview);
            txtReviewCount = (TextView) itemView.findViewById(R.id.txtReviewCount);
            img = (ImageView) itemView.findViewById(R.id.img);
            imgBtnHeart = (ImageButton) itemView.findViewById(R.id.imgBtnHeart);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }

        private void setPrice(int price){
            txtPrice.setText("$"+price);
        }

        private void setDescription(String description){
            txtDescripton.setText(description);
        }

        private void setType(String type){
            txtType.setText(type);
        }

        private void setReviewCount(int reviewCount){
            txtReviewCount.setText(reviewCount + "개");
        }

        private void setRatingBar(int ratingCount){
            ratingBar.setRating( (float)ratingCount );
        }

        private void setImg(int imgUri){
            img.setImageResource(imgUri);
        }

    }
}
