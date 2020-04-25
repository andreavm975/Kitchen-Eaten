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

    /**
     * Variables
     */

    private RestaurantDetailViewModel mViewModel;
    private String name;
    private int capacity;
    private ArrayList<Integer> schedule;

    @BindView(R.id.ivRestaurantDetail)
    ImageView ivRestaurant;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvType)
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

        //S'obtè el nom per arguments del fragment RestaurantListFragment

        if (getArguments() != null) {
            name = RestaurantDetailFragmentArgs.fromBundle(getArguments()).getName();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RestaurantDetailViewModel.class);

        //Es posa el swiper recarregant les dades del fragment
        loading.setRefreshing(true);

        //Es carreguen les dades
        loadData();
        loading.setColorSchemeColors(R.color.colorPrimaryDark);
        loading.setProgressBackgroundColorSchemeColor(R.color.colorAccent);

        //Amb aquest listener, si es fa scroll cap abaix a dalt de tot del fragment, es tornan a carregar les dades, actualitzant-se
        //posibles canvis a la base de dades
        loading.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    /**
     * Mètode que permet obtenir el restaurant
     */
    private void loadData() {

        LiveData<Restaurant> restaurant = mViewModel.getRestaurant(name);
        restaurant.observe(this, this::onRestaurantChanged);
    }

    /**
     * Mètode que s'executa quan arriba el restaurant i es posa la informació a la layout
     *
     * @param restaurant Restaurant obtingut
     */
    private void onRestaurantChanged(Restaurant restaurant) {

        //Amb Glide es pot descarregar la imatge de la direcció obtinguda de l'objecte restaurant. La imatge es guarda al Firebase Storage
        //i a la base de dades es guarda al camp image la direcció pùblica de la imatge.//
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


    /**
     * Botò que navega al fragment per a formalitzar la reserva al restaurant
     */

    @OnClick(R.id.btnReservation)
    public void onReservationClicked() {
        NavDirections navigation = RestaurantDetailFragmentDirections.actionRestaurantDetailFragmentToTableReserveFragment(name, capacity, schedule);
        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(navigation);
    }
}
