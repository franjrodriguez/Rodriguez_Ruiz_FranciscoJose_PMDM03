package com.rodriguezruiz.pokedex.ui.activities;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {

    private static String userUID;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this); // Inicializa Firebase
    }

    public static void setUserUID(String uid) {
        userUID = uid;
    }

    public static String getUserUID() {
        return userUID;
    }
}