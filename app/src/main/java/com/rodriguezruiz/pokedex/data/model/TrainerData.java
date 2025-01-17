package com.rodriguezruiz.pokedex.data.model;

import com.google.firebase.firestore.PropertyName;

public class TrainerData {

    @PropertyName("trainer_id")
    private String trainerId;

    // Constructor vacio necesario para Firestore
    public TrainerData() {}

    public TrainerData(String trainerId) {
        this.trainerId = trainerId;
    }

    @PropertyName("trainer_id")
    public String getTrainerId() {
        return trainerId;
    }

    @PropertyName("trainer_id")
    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }
}