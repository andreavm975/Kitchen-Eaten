package com.itb.kitcheneaten.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itb.kitcheneaten.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReserveCompletedFragment extends Fragment {

    private ReserveCompletedViewModel mViewModel;
    private String name;
    private String nDinners;
    private String date;

    @BindView(R.id.reservationCompleted)
    TextView reservationCompleted;

    public static ReserveCompletedFragment newInstance() {
        return new ReserveCompletedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reserve_completed_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if(getArguments() !=null){
            name = ReserveCompletedFragmentArgs.fromBundle(getArguments()).getRestName();
            nDinners = ReserveCompletedFragmentArgs.fromBundle(getArguments()).getNDinners();
            date = ReserveCompletedFragmentArgs.fromBundle(getArguments()).getDate();
        }

        reservationCompleted.setText("¡Congratulations! You have made a reservation for "+nDinners+" dinners in " +name+ " for "+date+"." );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReserveCompletedViewModel.class);

    }

}
