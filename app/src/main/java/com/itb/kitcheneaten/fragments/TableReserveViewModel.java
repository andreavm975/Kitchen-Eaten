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

    /**
     * Mètode que modifica la variable reserved segons la resposta que dóna la classe MyDatabase
     * @param nameRestaurant String nom del restaurant en el que es vol fer la reserva
     * @param reservation Reservation dades de la reserva que es vol fer
     */
    public void uploadReservation(String nameRestaurant, Reservation reservation){
        reserved.postValue(db.uploadReservation(nameRestaurant,reservation));
    }

    /**
     * Mètode que comprova si la data de la reserva i el restaurant estan disponibles
     * @param name String nom del restaurant
     * @param capacity Integer capacitat del restaurant
     * @param reservation Reservation dades de la reserva que es vol comprovar
     * @return LiveData<Boolean> true si està disponible, false si no
     */
    public LiveData<Boolean> isAvailable(String name, int capacity, Reservation reservation){
        return db.isAvailable(name, capacity, reservation);
    }

}
