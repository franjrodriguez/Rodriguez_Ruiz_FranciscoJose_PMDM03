package com.rodriguezruiz.pokedex.ui.activities;

import android.app.Application;
import com.google.firebase.FirebaseApp;

/**
 * Clase personalizada que extiende {@link Application} para inicializar Firebase
 * y gestionar el UID del usuario actual.
 * Esta clase se utiliza como punto de entrada para configuraciones globales de la aplicación.
 */
public class MyApplication extends Application {

    /**
     * Método llamado cuando se crea la aplicación.
     * Aquí se inicializa Firebase y se realizan otras configuraciones globales.
     */
    private static String userUID;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this); // Inicializa Firebase
    }

    /**
     * Establece el UID del usuario actual.
     * Este método permite almacenar el identificador único del usuario (UID) para su uso global en la aplicación.
     *
     * @param uid El identificador único del usuario (UID) a almacenar.
     */
    public static void setUserUID(String uid) {
        userUID = uid;
    }

    /**
     * Obtiene el UID del usuario actual.
     * Este método devuelve el identificador único del usuario (UID) previamente almacenado.
     *
     * @return El identificador único del usuario (UID) o null si no se ha establecido.
     */
    public static String getUserUID() {
        return userUID;
    }
}