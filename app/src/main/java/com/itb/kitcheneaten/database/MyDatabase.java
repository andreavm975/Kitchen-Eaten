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

/**
 * Classe que permet gestionar les dades de la base de dades (CRUD)
 */

public class MyDatabase {

    /**
     * Variables
     */

    FirebaseFirestore db;
    MutableLiveData<ArrayList<Restaurant>> restaurants = new MutableLiveData<>();
    ArrayList<Restaurant> aux = new ArrayList<>();
    MutableLiveData<Restaurant> restaurant = new MutableLiveData<>();
    

    /**
     * Constructor de la classe que obté la instancia de la base de dades de Firestore Database
     */

    public MyDatabase() {

        db = FirebaseFirestore.getInstance();

    }

    /**
     * Mètode que permet obtenir tots els restaurants de la base de dades, construits directament en objectes Restaurant
     */

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

        // Es neteja la llista perque sino es duplicarien els resultats

        aux.clear();

    }

    /**
     * Mètode que permet obtenir un restaurant pel seu nom, construit directament en un objecte Restaurant
     *
     * @param name Nom del restaurant
     */

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

    /**
     * Mètode que retorna la llista dels restaurants obtinguts
     *
     * @return Llista dels restaurants obtinguts
     */

    public LiveData<ArrayList<Restaurant>> getRestaurants() {
        return restaurants;
    }

    /**
     * Mètode que retorna el restaurant obtingut per nom
     *
     * @return Restaurant obtingut per nom
     */

    public LiveData<Restaurant> getRestaurant() {
        return restaurant;
    }

    /**
     * Mètode que permet pujar una reserva a la base de dades
     *
     * @param nameRestaurant Nom del restaurant al qual es vol fer la reserva
     * @param reservation    Objecte reserva
     * @return Retorna true o false si s'ha fet la reserva correctament
     */

    public boolean uploadReservation(String nameRestaurant, Reservation reservation) {
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

    /**
     * Mètode que permet comprobar la disponibilitat a l'hora de fer una reserva
     *
     * @param name        Nom del restaurant al qual es vol fer la reserva
     * @param capacity    Quantitat de comensals
     * @param reservation Objecte reserva
     * @return Retorna true o false segons si hi ha disponibilitat a la data seleccionada per a realitzar-se la reserva
     */

    public MutableLiveData<Boolean> isAvailable(String name, int capacity, Reservation reservation) {
        int[] totalDinners = {0};
        MutableLiveData<Boolean> available = new MutableLiveData<>();

        //S'obtenen les reserves del restaurant seleccionat a la data seleccionada i es calcula si la suma de tots els comensals de les reserves més la dels
        //comensals de la reserva a realitzar supera l'aforament màxim. Si no el supera, es podrà fer la reserva
        db.collection("restaurantes")
                .document(name.toLowerCase())
                .collection("reservations")
                .whereEqualTo("date", reservation.getDate())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Reservation reservation = document.toObject(Reservation.class);
                                totalDinners[0] += reservation.getnDinners();
                            }
                            if (totalDinners[0] + reservation.getnDinners() <= capacity) {
                                available.setValue(true);
                            } else {
                                available.setValue(false);
                            }
                        }

                    }
                });

        return available;

    }

}
