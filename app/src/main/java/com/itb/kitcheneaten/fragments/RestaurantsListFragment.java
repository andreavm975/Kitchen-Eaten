package com.itb.kitcheneaten.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itb.kitcheneaten.R;
import com.itb.kitcheneaten.fragments.adapter.RestaurantAdapter;
import com.itb.kitcheneaten.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantsListFragment extends Fragment {

    //VARIABLES

    @BindView(R.id.recyclerview)
    RecyclerView myRecyclerView;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout loading;

    RestaurantAdapter myAdapter;

    private RestaurantsListViewModel mViewModel;

    public static RestaurantsListFragment newInstance() {
        return new RestaurantsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.restaurants_list_fragment, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RestaurantsListViewModel.class);

        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);

        myAdapter = new RestaurantAdapter();
        myRecyclerView.setAdapter(myAdapter);
        myAdapter.setListener(this::viewRestaurant);



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

    public void loadData() {
        loading.setRefreshing(true);
        mViewModel.getAllRestaurants();
        LiveData<ArrayList<Restaurant>> restaurants = mViewModel.getRestaurants();
        restaurants.observe(this, this::restaurantsChanged);
    }

    private void restaurantsChanged(ArrayList<Restaurant> restaurants) {
        myAdapter.setRestaurants(restaurants);
        loading.setRefreshing(false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    private void viewRestaurant(Restaurant restaurant) {

        String idName = restaurant.getName();
        NavDirections direction = RestaurantsListFragmentDirections.restaurantListToDetail(idName);
        Navigation.findNavController(getView()).navigate(direction);

    }


}
