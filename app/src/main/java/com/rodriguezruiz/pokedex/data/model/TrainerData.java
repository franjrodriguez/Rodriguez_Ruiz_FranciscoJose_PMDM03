package com.rodriguezruiz.pokedex.data.model;

import com.google.firebase.firestore.PropertyName;

/**
 * Representa un modelo de datos de entrenador para su uso con Firestore.
 * Esta clase proporciona mapeo para el campo "trainer_id" utilizando anotaciones de Firestore.
 */
public class TrainerData {

    @PropertyName("trainer_id")
    private String trainerId;

    /**
     * Constructor sin argumentos necesario para la deserialización de Firestore.
     */    public TrainerData() {}

    /**
     * Construye una nueva instancia de TrainerData con el ID de entrenador especificado.
     *
     * @param trainerId el identificador único para el entrenador.
     */
    public TrainerData(String trainerId) {
        this.trainerId = trainerId;
    }

    /**
     * Obtiene el ID del entrenador.
     *
     * @return el ID del entrenador.
     */
    @PropertyName("trainer_id")
    public String getTrainerId() {
        return trainerId;
    }

    /**
     * Establece el ID del entrenador.
     *
     * @param trainerId el identificador único a establecer para el entrenador.
     */
    @PropertyName("trainer_id")
    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }
}