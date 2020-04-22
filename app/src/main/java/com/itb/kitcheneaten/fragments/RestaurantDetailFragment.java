package com.itb.kitcheneaten.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.itb.kitcheneaten.R;
import com.itb.kitcheneaten.model.Restaurant;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestaurantDetailFragment extends Fragment {

    private RestaurantDetailViewModel mViewModel;
    private String name;
    private int capacity;
    private ArrayList<Integer> schedule;

    @BindView(R.id.ivRestaurantDetail)
    ImageView ivRestaurant;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvAddress)
    TextView tvAddress;

    @BindView(R.id.tvTelf)
    TextView tvTelf;

    @BindView(R.id.swipe_layout_detail)
    SwipeRefreshLayout loading;

    public static RestaurantDetailFragment newInstance() {
        return new RestaurantDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.restaurant_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            name = RestaurantDetailFragmentArgs.fromBundle(getArguments()).getName();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RestaurantDetailViewModel.class);

        loading.setRefreshing(true);
        loadData();
        loading.setColorSchemeColors(R.color.colorPrimaryDark);
        loading.setProgressBackgroundColorSchemeColor(R.color.colorAccent);

        loading.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData() {

        LiveData<Restaurant> restaurant = mViewModel.getRestaurant(name);
        restaurant.observe(this, this::onRestaurantChanged);
    }

    private void onRestaurantChanged(Restaurant restaurant) {
        Glide.with(getContext())
                .load(restaurant.getImage())
                .into(ivRestaurant);
        tvName.setText(restaurant.getName());
        tvAddress.setText(restaurant.getAddress());
        tvTelf.setText(restaurant.getTelf());
        capacity = restaurant.getCapacity();
        schedule = restaurant.getSchedule();
        loading.setRefreshing(false);
    }


    @OnClick(R.id.btnReservation)
    public void onReservationClicked() {
        NavDirections navigation = RestaurantDetailFragmentDirections.actionRestaurantDetailFragmentToTableReserveFragment(name, capacity, schedule);
        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(navigation);
    }
}
