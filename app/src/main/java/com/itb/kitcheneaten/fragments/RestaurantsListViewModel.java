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


  /*  public void subirRestaurantes() {
        ArrayList<String> names = new ArrayList<>();
        names.add("Dalai Lomo");
        names.add("El Celler de Can Roca");
        names.add("Can Prat");
        names.add("Restaurante Josefina");
        names.add("La fábrica");
        names.add("El loro charlie");
        names.add("Sal i Sucre");
        names.add("Casa Pepe");
        names.add("Ca la Poli");
        names.add("Restaurante chino Amistad");


        for (int i = 0; i < 10; i++) {
            Restaurant restaurant = new Restaurant(names.get(i), "Calle falsa " + i, "93584722" + i, 30 + i, "");
            db.uploadRestaurant(restaurant);
        }
    }*/

    public LiveData<ArrayList<Restaurant>> getAllRestaurants() {
        db.getAllRestaurantsFromBBDD();
        restaurants = db.getRestaurants();
        return restaurants;
    }


}
