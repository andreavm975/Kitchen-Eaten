package com.itb.kitcheneaten.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.itb.kitcheneaten.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableReserveFragment extends Fragment {

    private TableReserveViewModel mViewModel;
    private String name;

    @BindView(R.id.tvRestaurantName)
    TextView restName;

    public static TableReserveFragment newInstance() {
        return new TableReserveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.table_reserve_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TableReserveViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            name = TableReserveFragmentArgs.fromBundle(getArguments()).getRestName();
        }

        restName.setText(name);

    }
}
