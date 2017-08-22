package com.android.airbnb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.domain.airbnb.House;

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
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView txtTitle;

        public Holder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        }
    }
}
