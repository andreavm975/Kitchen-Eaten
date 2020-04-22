package com.itb.kitcheneaten.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itb.kitcheneaten.model.Reservation;
import com.itb.kitcheneaten.model.Restaurant;

import java.util.ArrayList;

public class MyDatabase {
    FirebaseFirestore db;
    MutableLiveData<ArrayList<Restaurant>> restaurants = new MutableLiveData<>();
    ArrayList<Restaurant> aux = new ArrayList<>();
    MutableLiveData<Restaurant> restaurant = new MutableLiveData<>();
    MutableLiveData<Reservation> reservation= new MutableLiveData<>();
    MutableLiveData<ArrayList<Reservation>> reservations = new MutableLiveData<>();
    MutableLiveData<Boolean> available = new MutableLiveData<>();

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

  /*  public void uploadRestaurant(Restaurant restaurant){
        final boolean[] reserved = {false};
        db.collection("restaurantes").document(restaurant.getName().toLowerCase())
                .set(restaurant)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("OK");
                    }

                });

    }*/

    public boolean uploadReservation(String nameRestaurant, Reservation reservation){
        final boolean[] reserved = {false};
        db.collection("restaurantes")
                .document(nameRestaurant.toLowerCase())
                .collection("reservations")
                .add(reservation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                reserved[0] = true;
            }
        });

        return reserved[0];
    }

    public MutableLiveData<Boolean> isAvailable(String name, int capacity, Reservation reservation){
        int[] totalDinners = {0};
        db.collection("restaurantes")
                .document(name.toLowerCase())
                .collection("reservations")
                .whereEqualTo("date",reservation.getDate())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Reservation reservation= document.toObject(Reservation.class);
                                totalDinners[0] +=reservation.getnDinners();
                            }
                            if(totalDinners[0]+reservation.getnDinners()<=capacity){
                                available.setValue(true);
                            } else{
                                available.setValue(false);
                            }
                        }
                    }
                });

        return available;
    }

}
