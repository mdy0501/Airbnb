package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.domain.House;
import com.android.airbnb.domain.House_images;
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


    public BottomSheetAdapter(List<House> wishList, Context context) {
        this.wishList = wishList;
        this.mContext = context;
    }

    @Override
    public BottomSheetAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_bottomsheet_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        House house = wishList.get(position);
        Log.e("BottomSheet", "pos :: " + position);
//        List<House> houseList = list.getWishList();
//        holder.setListImg1();
        House_images[] houseImages = house.getHouse_images();
        holder.setListName(house.getTitle());
        holder.setPosition(position);
        if (houseImages.length > 0) {
            holder.setItemCount(houseImages.length + "");
            holder.setListImg1(houseImages[0].getImage());
            GlideApp
                    .with(mContext)
                    .load(houseImages[0].getImage())
                    .centerCrop()
                    .into(holder.listImg1);

            GlideApp
                    .with(mContext)
                    .load(R.mipmap.dummy_host_img)
                    .centerCrop()
                    .into(holder.listImg2);

            GlideApp
                    .with(mContext)
                    .load(R.mipmap.dummy_host_img)
                    .centerCrop()
                    .into(holder.listImg3);
        }
    }

    private void setFirstItem(){



    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView listImg1;
        private ImageView listImg2;
        private ImageView listImg3;
        private TextView listName;
        private TextView itemCount;

        public void setPosition(int position) {
            this.position = position;
        }

        private int position;

        public Holder(View itemView) {
            super(itemView);
            listImg1 = (ImageView) itemView.findViewById(R.id.wishlist_bottomsheet_img_1);
            listImg2 = (ImageView) itemView.findViewById(R.id.wishlist_bottomsheet_img_2);
            listImg3 = (ImageView) itemView.findViewById(R.id.wishlist_bottomsheet_img_3);
            listName = (TextView) itemView.findViewById(R.id.wishlist_bottomsheet_listname);
            itemCount = (TextView) itemView.findViewById(R.id.wishlist_bottomsheet_count);
            setOnClick(itemView);
        }

        private void setOnClick(View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "[" + position + "] 클릭", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setListImg1(String imgUrl) {
            GlideApp
                    .with(mContext)
                    .load(imgUrl)
                    .into(listImg1);
        }

        public void setListImg2(String imgUrl) {
            GlideApp
                    .with(mContext)
                    .load(imgUrl)
                    .into(listImg2);
        }

        public void setListImg3(String imgUrl) {
            GlideApp
                    .with(mContext)
                    .load(imgUrl)
                    .into(listImg3);
        }

        public void setListName(String name) {
            this.listName.setText(name);
        }

        public void setItemCount(String itemCount) {
            this.itemCount.setText(itemCount);
        }

    }
}
