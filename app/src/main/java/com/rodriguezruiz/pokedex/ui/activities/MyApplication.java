package com.rodriguezruiz.pokedex.ui.activities;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {

    private static String userUID;

    public static void setUserUID(String uid) {
        userUID = uid;
    }

    public static String getUserUID() {
        return userUID;
    }
}
