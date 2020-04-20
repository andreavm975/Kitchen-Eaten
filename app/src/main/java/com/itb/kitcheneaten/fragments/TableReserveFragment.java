package com.itb.kitcheneaten.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.itb.kitcheneaten.R;
import com.itb.kitcheneaten.model.Reservation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TableReserveFragment extends Fragment {

    private TableReserveViewModel mViewModel;
    private String name;
    private int capacity;

    private Date reservationDate;

    @BindView(R.id.tvRestaurantName)
    TextView restName;

    @BindView(R.id.etDate)
    TextInputEditText etDate;

    @BindView(R.id.etDinners)
    TextInputEditText etDinners;

    @BindView(R.id.etName)
    TextInputEditText etName;

    @BindView(R.id.etTelf)
    TextInputEditText etTelf;
    private Reservation reservation;

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
            capacity = TableReserveFragmentArgs.fromBundle(getArguments()).getRestCapacity();

        }

        restName.setText(name);

    }

    @OnClick({R.id.tilDate,R.id.etDate})
    public void onDateClicked(){
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Reservation Date");
        MaterialDatePicker<Long> picker = builder.build();
        picker.addOnPositiveButtonClickListener(this::doOnDateSelected);
        picker.show(getFragmentManager(), picker.toString());
    }

    private void doOnDateSelected(Long aLong) {
        String pattern = "dd/MM/yyyy";
        DateFormat df= new SimpleDateFormat(pattern);
        reservationDate =new Date(aLong);
        String reservation=df.format(reservationDate);
        etDate.setText(reservation);
    }


    public void getDataFromUser(){
        reservation = new Reservation();
        reservation.setDate(reservationDate);
        reservation.setName(etName.getText().toString());
        reservation.setnDinners(Integer.parseInt(etDinners.getText().toString()));
        reservation.setTelf(etTelf.getText().toString());
    }


    @OnClick(R.id.btnCheck)
    public void onCheckClicked(){
        getDataFromUser();
        mViewModel.isAvailable(name, capacity,reservation).observe(this, this::onAvailableChanged);
    }

    private void onAvailableChanged(Boolean aBoolean) {
        if(aBoolean){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            mViewModel.uploadReservation(name, reservation);
                            Navigation.findNavController(getView()).navigate(R.id.action_tableReserveFragment_to_reserveCompletedFragment);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure?").setPositiveButton("Accept", dialogClickListener)
                    .setNegativeButton("Cancel", dialogClickListener).show();
        } else{
            Toast.makeText(getActivity(), "Not available date.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
