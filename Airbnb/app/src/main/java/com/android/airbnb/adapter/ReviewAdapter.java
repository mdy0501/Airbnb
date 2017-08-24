package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.util.DateHandler;
import com.android.airbnb.util.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder> {

    List<Host> hostList = new ArrayList<>();
    Context mContext;

    public ReviewAdapter(List<Host> hostList, Context context) {
        this.hostList = hostList;
        this.mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Host host = hostList.get(position);
        holder.setReviewGuestName(host.getUsername());
        String formattedDate = DateHandler.getDateYYYYdd(host.getLast_login());
        holder.setReviewGuestVisitDate(formattedDate);
        holder.setReviewGuestContent("내용내용내용내용 \n 내용내용내용!!!!!!!!!!!!!!!");
        holder.setReviewGuestImg(host.getImg_profile());
    }

    @Override
    public int getItemCount() {
        return hostList.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        private ImageView reviewGuestImg;
        private TextView reviewGuestName;

        public void setReviewGuestImg(String img) {
            if (img != null && !"".equals(img)) {
                GlideApp
                        .with(mContext)
                        .load(img)
                        .fallback(R.mipmap.ic_launcher)
                        .centerCrop()
                        .circleCrop()
                        .into(this.reviewGuestImg);
            } else {
                GlideApp
                        .with(mContext)
                        .load(R.mipmap.ic_launcher)
                        .centerCrop()
                        .circleCrop()
                        .into(this.reviewGuestImg);
            }
        }

        public void setReviewGuestName(String reviewGuestName) {
            this.reviewGuestName.setText(reviewGuestName);
        }

        public void setReviewGuestVisitDate(String reviewGuestVisitDate) {
            this.reviewGuestVisitDate.setText(reviewGuestVisitDate);
        }

        public void setReviewGuestContent(String reviewGuestContent) {
            this.reviewGuestContent.setText(reviewGuestContent);
        }

        private TextView reviewGuestVisitDate;
        private TextView reviewGuestContent;


        public Holder(View itemView) {
            super(itemView);
            reviewGuestImg = (ImageView) itemView.findViewById(R.id.review_guest_img);
            reviewGuestName = (TextView) itemView.findViewById(R.id.review_guest_name);
            reviewGuestVisitDate = (TextView) itemView.findViewById(R.id.review_guest_visit_date);
            reviewGuestContent = (TextView) itemView.findViewById(R.id.review_guest_content);
        }
    }
}
