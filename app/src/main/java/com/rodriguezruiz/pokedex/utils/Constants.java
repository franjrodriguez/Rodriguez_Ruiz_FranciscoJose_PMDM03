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
    public static final String LANGUAGE = "language_preference";
    public static final String LANGUAGE_DEFAULT = "es";
    public static final String SETTING_ABOUT = "about_preference";
    public static final String SETTING_LANGUAGE = "language_preference";
    public static final String SETTING_DELETE = "delete_pokemon_preferences";
    public static final String SETTING_LOGOUT = "logout_preference";
    // Global Firestore
    public static final String COLLECTION_USER = "pokemon_trainers";
    public static final String COLLECTION_NAME = "captured_pokemons";
}
