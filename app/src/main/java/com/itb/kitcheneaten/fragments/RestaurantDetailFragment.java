package com.itb.kitcheneaten.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.itb.kitcheneaten.R;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestaurantDetailFragment extends Fragment {

    private RestaurantDetailViewModel mViewModel;
    private String name;

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
        if(getArguments()!=null){
            name = RestaurantDetailFragmentArgs.fromBundle(getArguments()).getName();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RestaurantDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    @OnClick(R.id.btnReservation)
    public void onReservationClicked(){
        NavDirections navigation = RestaurantDetailFragmentDirections.actionRestaurantDetailFragmentToTableReserveFragment(name);
        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(navigation);
    }
}
