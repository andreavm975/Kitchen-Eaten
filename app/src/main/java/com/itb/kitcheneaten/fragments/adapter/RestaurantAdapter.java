package com.itb.kitcheneaten.fragments.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.itb.kitcheneaten.R;
import com.itb.kitcheneaten.model.Restaurant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {


    // VARIABLES
    private Context myContext;
    List<Restaurant> restaurants;
    OnRestaurantClickListener listener;

    public RestaurantAdapter(Context context) {
        this.myContext = context;
    }

    public RestaurantAdapter() {
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        Glide.with(myContext)
                .load(restaurant.getImage())
                .into(holder.imageRestaurant);
        holder.name.setText(restaurant.getName());
        holder.type.setText(restaurant.getType());
        holder.address.setText(restaurant.getAddress());
    }


    @Override
    public int getItemCount() {
        int size = 0;
        if (restaurants != null) {
            size = restaurants.size();
        }
        return size;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    public void setListener(OnRestaurantClickListener listener) {
        this.listener = listener;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgRestaurant)
        ImageView imageRestaurant;

        @BindView(R.id.tvName)
        TextView name;

        @BindView(R.id.tvAddress)
        TextView address;

        @BindView(R.id.tvType)
        TextView type;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_layout)
        public void onRestaurantClicked() {
            Restaurant restaurant = restaurants.get(getAdapterPosition());
            listener.onRestaurantClicked(restaurant);

        }
    }

    public interface OnRestaurantClickListener {
        void onRestaurantClicked(Restaurant restaurant);
    }

}
