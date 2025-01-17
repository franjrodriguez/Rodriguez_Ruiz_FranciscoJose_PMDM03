package com.rodriguezruiz.pokedex.utils;

public class Constants {
    // Global TAG
    public static final String TAG = "TAGFRAN";

    // Global API
    public static final String URL_API = "https://pokeapi.co/api/v2/";
    public static final String URL_SPRITE = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    public static final String TYPE_SPRITE = ".png";
    public static final int OFFSET = 0;
    public static final int LIMIT = 150;

    // Global SharedPreferences
    public static final String SETTING_LANGUAGE = "language";
    public static final String SETTING_ABOUT = "about_preference";
    public static final String SETTING_DELETE = "delete";
    public static final String SETTING_LOGOUT = "logout";
    public static final String SETTING_LANGUAGE_DEFAULT = "es";

    // Global Firestore
    public static final String COLLECTION_TRAINERS = "pokemon_trainers";
    public static final String COLLECTION_CAPTURED = "captured_pokemons";
}
