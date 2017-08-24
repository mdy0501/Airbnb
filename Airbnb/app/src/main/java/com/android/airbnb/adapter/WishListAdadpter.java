package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.util.GlideApp;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 9..
 * wishlist의 지역별 리스트를 보여주는 어댑터
 */

public class WishListAdadpter extends RecyclerView.Adapter<WishListAdadpter.Holder> {

    // 백엔드 작업이 완료되면 houseList는 wishList 관련 객체로 교체해준다.
    private List<House> houselist;
    public Context mContext;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_saved_item, parent, false);
        this.mContext = parent.getContext();
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }
    
    @Override
    public int getItemCount() {
        return houselist.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView savedListImg;
        private TextView savedItemCount;
        private TextView savedItemTitle;

        public Holder(View itemView) {
            super(itemView);
            int position = -1;
            savedListImg = (ImageView) itemView.findViewById(R.id.saved_item_img);
            savedItemCount = (TextView) itemView.findViewById(R.id.saved_item_count);
            savedItemTitle = (TextView) itemView.findViewById(R.id.saved_item_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "item clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setSavedListImg(String imgUrl) {
            GlideApp
                    .with(mContext)
                    .load(imgUrl)
                    .centerCrop()
                    .into(savedListImg);
        }

        public void setSavedItemCount(String savedItemCount) {
            this.savedItemCount.setText(savedItemCount);
        }

        public void setSavedItemTitle(String savedItemTitle) {
            this.savedItemTitle.setText(savedItemTitle);
        }
    }
}
