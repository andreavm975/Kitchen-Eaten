package com.itb.kitcheneaten.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.itb.kitcheneaten.database.MyDatabase;
import com.itb.kitcheneaten.model.Reservation;


public class TableReserveViewModel extends AndroidViewModel {

    MyDatabase db;
    MutableLiveData<Boolean> reserved = new MutableLiveData<>();

    public TableReserveViewModel(@NonNull Application application) {
        super(application);
        db= new MyDatabase();
    }

    public void uploadReservation(String nameRestaurant, Reservation reservation){
        reserved.postValue(db.uploadReservation(nameRestaurant,reservation));
    }

    public LiveData<Boolean> isAvailable(String name, int capacity, Reservation reservation){
        return db.isAvailable(name, capacity, reservation);
    }

}
