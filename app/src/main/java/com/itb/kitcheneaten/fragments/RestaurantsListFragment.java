package com.itb.kitcheneaten.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itb.kitcheneaten.R;
import com.itb.kitcheneaten.fragments.adapter.RestaurantAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantsListFragment extends Fragment {

    //VARIABLES

    @BindView(R.id.recyclerview)
    RecyclerView myRecyclerView;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RestaurantsListViewModel.class);

        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);

        myAdapter = new RestaurantAdapter();
        myRecyclerView.setAdapter(myAdapter);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }
}
