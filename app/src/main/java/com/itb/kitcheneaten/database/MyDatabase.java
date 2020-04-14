package com.itb.kitcheneaten.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itb.kitcheneaten.model.Restaurant;

import java.util.ArrayList;

public class MyDatabase {

    FirebaseFirestore db;
    MutableLiveData<ArrayList<Restaurant>> restaurants = new MutableLiveData<>();
    ArrayList<Restaurant> aux = new ArrayList<>();
    MutableLiveData<Restaurant> restaurant = new MutableLiveData<>();


    public MyDatabase() {
        db = FirebaseFirestore.getInstance();
    }

    public void getAllRestaurantsFromBBDD() {

        db.collection("restaurantes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Restaurant restaurant = document.toObject(Restaurant.class);
                        aux.add(restaurant);
                    }
                    restaurants.postValue(aux);

                }
            }
        });
        aux.clear();
    }

    public void getRestaurantFromName(String name) {

        db.collection("restaurantes").document(name.toLowerCase()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        restaurant.postValue(document.toObject(Restaurant.class));
                    }
                }
            }
        });

    }


    public LiveData<ArrayList<Restaurant>> getRestaurants() {
        return restaurants;
    }

    public LiveData<Restaurant> getRestaurant() {
        return restaurant;
    }
}
