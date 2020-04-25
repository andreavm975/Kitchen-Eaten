package com.itb.kitcheneaten.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.itb.kitcheneaten.R;
import com.itb.kitcheneaten.fragments.adapter.RestaurantAdapter;
import com.itb.kitcheneaten.model.Restaurant;

import java.util.ArrayList;

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

    private LiveData<ArrayList<Restaurant>> restaurants;

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

        //RECYCLERVIEW
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);

        //ADAPTER
        myAdapter = new RestaurantAdapter(getContext());
        myRecyclerView.setAdapter(myAdapter);
        myAdapter.setListener(this::viewRestaurant);

        //LOAD DATA
        loadData();
        loading.setColorSchemeColors(R.color.colorPrimaryDark);
        loading.setProgressBackgroundColorSchemeColor(R.color.colorAccent);
        loading.setOnRefreshListener(this::loadData);

    }

    /**
     * Mètode que carrega les dades que obté de la base de dades i les recarrega quan es llisca la part superior de la pantalla
     */
    public void loadData() {
        loading.setRefreshing(true);
        restaurants = mViewModel.getAllRestaurants();
        restaurants.observe(this, this::restaurantsChanged);
    }

    /**
     * Mètode que observa els restaurants i els posa al Recyclerview
     * @param restaurants ArrayList<Restaurants> restaurants que agafa de la base de dades
     */
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

    /**
     * Métode que navega cap a un Restaurant per a veure'l en detall
     * @param restaurant Restaurant que es vol veure
     */
    private void viewRestaurant(Restaurant restaurant) {

        String idName = restaurant.getName();
        NavDirections direction = RestaurantsListFragmentDirections.restaurantListToDetail(idName);
        Navigation.findNavController(getView()).navigate(direction);

    }


}
