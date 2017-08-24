package com.android.airbnb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;

/**
 * Created by MDY on 2017-08-22.
 */

public class RegisterRoomsAdapter extends RecyclerView.Adapter<RegisterRoomsAdapter.Holder>{

    private List<House> houses;
    private LayoutInflater inflater;
    private Context context;


    public RegisterRoomsAdapter(Context context, List<House> data){
        this.houses = data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void clearData(){
        houses.clear();
    }

    public void refreshData(List<House> houses) {
        this.houses = houses;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.register_rooms_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        House house = houses.get(position);
        holder.txtTitle.setText(house.getTitle());
        holder.txtAddress.setText(house.getAddress());
        holder.setPosition(position);
        House_images[] images = house.getHouse_images();
        GlideApp
                .with(inflater.getContext())
                .load(images.length > 0 ? images[0].getImage() : null)
                .centerCrop()
                .fallback(R.drawable.question_mark)
                .into(holder.imgFirst);
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        int position = -1;
        TextView txtTitle, txtAddress;
        ImageView imgFirst;

        public Holder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            imgFirst = (ImageView) itemView.findViewById(R.id.imgFirst);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailHouseActivity.class);
                    House registerHouse = houses.get(position);
                    Bundle extra = new Bundle();
                    extra.putString("key", "registerHouse");
                    extra.putParcelable("registerHouse", registerHouse);
                    intent.putExtras(extra);
                    v.getContext().startActivity(intent);
                }
            });
        }

        private void setPosition(int position) {
            this.position = position;
        }
    }
}
