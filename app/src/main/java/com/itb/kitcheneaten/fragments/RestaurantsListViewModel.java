package com.itb.kitcheneaten.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.itb.kitcheneaten.database.MyDatabase;
import com.itb.kitcheneaten.model.Restaurant;

import java.util.ArrayList;

public class RestaurantsListViewModel extends AndroidViewModel {
    MyDatabase db;

    private LiveData<ArrayList<Restaurant>> restaurants = new MutableLiveData<>();

    public RestaurantsListViewModel(@NonNull Application application) {
        super(application);
        db = new MyDatabase();
    }

    /**
     * Mètode que recull tots els restaurants que ha rebut de la base de dades
     * @return LiveData<ArrayList<Restaurant>> Array de restaurants que rep.
     */
    public LiveData<ArrayList<Restaurant>> getAllRestaurants() {
        db.getAllRestaurantsFromBBDD();
        restaurants = db.getRestaurants();
        return restaurants;
    }


}
