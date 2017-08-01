package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.domain.House;
import com.android.airbnb.domain.IMapMarker;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 1..
 */

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.Holder> {

    List<House> houseList = new ArrayList<>();
    Context mContext;
    IMapMarker onMapAdapter;


    private LatLng currentLatLng;

    public MapAdapter(List<House> houseList, Context context, IMapMarker onMapAdapter) {
        this.houseList = houseList;
        this.mContext = context;
        this.onMapAdapter = onMapAdapter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.googlemap_viewpager_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // parameter로 들어있는 매개변수 말고, 아래와 같이 holder.getAdapterPosition();을 사용한다.
        // 그래야 원하는 position 값이 제대로 전달된다.
        Log.e("MapAdapter", "getAdapterPositoin :: " + holder.getAdapterPosition());
        Log.e("MapAdapter", "onBindViewHolder pos :: " + position);
        House house = houseList.get(position);
        holder.setHousePrice(house.getPrice());
        holder.setHouseDetail(house.getDetail());

        // RatingBar 설정
        onMapAdapter.moveMarker(house);
        holder.mRatingBar.setRating(house.getEvaluation());
        Glide
                .with(mContext)
                .load(house.getImgUri())
                .into(holder.houseImg);
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        public ImageView houseImg;
        public TextView housePrice;
        public TextView houseDetail;
        public RatingBar mRatingBar;


        public void setHousePrice(int price) {
            this.housePrice.setText("₩" + price);
        }

        public void setHouseDetail(String detail) {
            this.houseDetail.setText(detail);
        }

        public Holder(View itemView) {
            super(itemView);
            houseImg = (ImageView) itemView.findViewById(R.id.house_img);
            housePrice = (TextView) itemView.findViewById(R.id.house_price);
            houseDetail = (TextView) itemView.findViewById(R.id.house_detail);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }


}