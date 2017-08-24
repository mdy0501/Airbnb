package com.android.airbnb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.detailActivity.DetailHouseActivity;
import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.airbnb.House_images;
import com.android.airbnb.util.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 5..
 */

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.Holder> {

    List<House> wishList = new ArrayList<>();
    private Context mContext;
    private View view = null;
    public static final String BOTTOM_SHEET_ADAPTER_PK = "BOTTOM_SHEET_ADAPTER_PK";


    public BottomSheetAdapter(List<House> wishList, Context context) {
        this.wishList = wishList;
        this.mContext = context;
    }

    public void refreshWishList(List<House> wishList){
        this.wishList = wishList;
        notifyDataSetChanged();
    }

    @Override
    public BottomSheetAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_bottomsheet_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        House house = wishList.get(position);
        House_images[] houseImages = house.getHouse_images();
        holder.setListName(house.getTitle());
        holder.setPosition(position);
        if (houseImages.length > 0) {
            holder.setItemCount(houseImages.length + "");

            GlideApp
                    .with(mContext)
                    .load(houseImages[0].getImage())
                    .fallback(R.mipmap.dummy_host_img)
                    .centerCrop()
                    .into(holder.listImg1);
        }
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView listImg1;
        private TextView listName;
        private TextView itemCount;

        public void setPosition(int position) {
            this.position = position;
        }

        private int position;

        public Holder(View itemView) {
            super(itemView);
            listImg1 = (ImageView) itemView.findViewById(R.id.wishlist_bottomsheet_img_1);
            listName = (TextView) itemView.findViewById(R.id.wishlist_bottomsheet_listname);
            itemCount = (TextView) itemView.findViewById(R.id.wishlist_bottomsheet_count);
            setOnClick(itemView);
        }

        private void setOnClick(View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailHouseActivity.class);
                    intent.putExtra(BOTTOM_SHEET_ADAPTER_PK, wishList.get(position));
                    intent.putExtra("key", BOTTOM_SHEET_ADAPTER_PK);
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setListName(String name) {
            this.listName.setText(name);
        }

        public void setItemCount(String itemCount) {
            this.itemCount.setText(itemCount);
        }
    }
}
