package com.itb.kitcheneaten.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.itb.kitcheneaten.database.MyDatabase;
import com.itb.kitcheneaten.model.Restaurant;

public class RestaurantDetailViewModel extends AndroidViewModel {

    MyDatabase db;
    LiveData<Restaurant> restaurant;

    public RestaurantDetailViewModel(@NonNull Application application) {
        super(application);
        db= new MyDatabase();
    }

    /**
     * Mètode que recull un restaurant a partir del seu nom de la base de dades
     * @param name
     * @return
     */
    public LiveData<Restaurant> getRestaurant(String name){
        db.getRestaurantFromName(name);
        restaurant= db.getRestaurant();

        return restaurant;
    }

}
